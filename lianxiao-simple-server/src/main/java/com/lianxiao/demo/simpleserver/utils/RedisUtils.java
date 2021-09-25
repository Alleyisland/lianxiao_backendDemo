package com.lianxiao.demo.simpleserver.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.lianxiao.demo.simpleserver.exception.AppException;
import com.lianxiao.demo.simpleserver.model.Post;
import org.apache.lucene.util.fst.FST;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import com.google.common.hash.Hashing;
import org.springframework.data.redis.connection.RedisZSetCommands.Tuple;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;

@Component
public class RedisUtils {
    private static final long BITSET_SHIFT = 48;
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;
    @Autowired
    private RedissonClient redissonClient;

    //  数字类型自增+1
    public void incr(Object key){
        redisTemplate.opsForValue().increment(key);
    }

    public void set(Object key,Object value){
        String strkey=FastJsonUtils.toString(key);
        String strobj=FastJsonUtils.toString(value);
        redisTemplate.opsForValue().set(strkey,strobj);
    }

    //  hash put
    public void hSet(Object key,Object hashKey,Object value){
        if(isNum(value))
            value=num2Str(value);
        redisTemplate.opsForHash().put(key,hashKey,value);
    }

    public void zSetAdd(Object key, Object zSetKey, double value) {
        if(isNum(value)) {
            redisTemplate.opsForZSet().add(key, zSetKey, value);
        }
        else throw new AppException("-1","赞数必须是数字");
    }

    //  get 返回int
    public int getNum(Object key){
        Object value=redisTemplate.opsForValue().get(key);
        if(value instanceof String){
            return Integer.parseInt((String)value);
        }
        return value==null?0:(int)value;
    }

    //  hash get 返回int
    public int hGetNum(Object key, Object hashKey) {
        Object value=redisTemplate.opsForHash().get(key,hashKey);
        if (value instanceof String)
            return Integer.parseInt((String) value);
        return value==null?0:(int)value;
    }


    public Set<Object> zSetRange(Object key,int low,int high) {
        Set<Object> rank=redisTemplate.opsForZSet().reverseRange(key,low,high);
        System.out.println("获取到的排行列表:" + JSON.toJSONString(rank));
        return rank;
    }

    public boolean isNum(Object value){
        return value instanceof Integer||value instanceof Long||value instanceof Double;
    }

    public String num2Str(Object num){
        return num+"";
    }

    public void flushSet(Object key, Set<String> tags) {
        RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
        redisTemplate.executePipelined(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                for (String tag : tags)
                    connection.sAdd(serializer.serialize((String) key), serializer.serialize(tag));
                return null;
            }
        }, serializer);
    }

    public Set<String> interSet(Object key1, Object key2) {
        Set<Object> value=redisTemplate.opsForSet().intersect(key1,key2);
        Set<String> res=new HashSet<>();
        if(value!=null)
            for(Object o:value)
                res.add((String)o);
        return res;
    }

    public Set<String> fetchSet(Object key) {
        Set<Object> value=redisTemplate.opsForSet().members(key);
        Set<String> res=new HashSet<>();
        if(value!=null)
            for(Object o:value)
                res.add((String)o);
        return res;
    }

    public void bitSetPut(Object key,long uid) {
        HashFunction hf = Hashing.sha256();
        HashCode hc = hf.newHasher()
                .putLong(uid)
                .hash();
        long offset=hc.asLong()>>>BITSET_SHIFT;
        redisTemplate.opsForValue().setBit(key,offset,true);
    }

    public void bitSetDel(Object key,long uid) {
        HashFunction hf = Hashing.sha256();
        HashCode hc = hf.newHasher()
                .putLong(uid)
                .hash();
        long offset=hc.asLong()>>>BITSET_SHIFT;
        redisTemplate.opsForValue().setBit(key,offset,false);
    }

    public Map<Long, Boolean> bitSetScan(Object key,Set<Long> uids) {
        Map<Long,Boolean> res=new HashMap<>();
        HashFunction hf = Hashing.sha256();
        for(long uid:uids) {
            HashCode hc = hf.newHasher()
                    .putLong(uid)
                    .hash();
            long offset = hc.asLong()>>>BITSET_SHIFT;
            res.put(uid,redisTemplate.opsForValue().getBit(key, offset));
        }
        return res;
    }

    public boolean execIPLimitScript(DefaultRedisScript script, Object key) {
        List<Object> keys=new ArrayList<>();
        keys.add(key);
        String limitWindow=num2Str(30);
        String limitCnt=num2Str(100);
        return (long)redisTemplate.execute(script,keys,limitWindow,limitCnt)==1L;
    }

    public List<Long> getList(Object key) {
        List<Long>res=new ArrayList<>();
        List<Object> ids=redisTemplate.opsForList().range(key,0,-1);
        if(ids!=null)
            for(Object o: ids)
                res.add(Long.parseLong((String) o));
        return res;
    }

    public List<Post> getPostList(Object key) {
        List<Post>res=new ArrayList<>();
        List<Object> posts=redisTemplate.opsForList().range(key,0,-1);
        if(posts!=null) {
            for (Object o : posts) {
                System.out.println(o.toString());
                res.add(JSON.parseObject(o.toString(), Post.class));
            }
        }
        return res;
    }

    public void listAddOne(Object key, Object value) {
        redisTemplate.opsForList().leftPush(key,value);
    }

    public List<Post> listReplace(Object key, List<Post> posts) {
        List<Post> res=new ArrayList<>();

        List<Object> oldValues = redisTemplate.opsForList().range(key, 0, -1);
        Long oldSize=redisTemplate.opsForList().size(key);
        if(oldSize!=null) {
            for(int i=0;i<oldSize;i++) {
                redisTemplate.opsForList().rightPop(key);
            }
        }

        return res;
    }

    public void delKey(Object key){
        redisTemplate.delete(key);
    }

    public void expire(Object key, Date date) {
        redisTemplate.expireAt(key,date);
    }

    public void listAddAll(Object key, List<Post> posts) {
        for(Post p:posts){
            redisTemplate.opsForList().leftPush(key,JSON.toJSONString(p));
        }
    }

    public List<Post> fetchOldList(Object key) {
        List<Post> res=new ArrayList<>();
        List<Object> oldValues = redisTemplate.opsForList().range(key, 0, -1);
        if(oldValues!=null&&oldValues.size()!=0){
            for (Object o : oldValues) {
                res.add(JSON.parseObject((String) o, Post.class));
            }
        }
        return res;
    }
}
