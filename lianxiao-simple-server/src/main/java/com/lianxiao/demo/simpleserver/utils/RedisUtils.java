package com.lianxiao.demo.simpleserver.utils;

import com.alibaba.fastjson.JSON;
import com.lianxiao.demo.simpleserver.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RedisUtils {
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

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
}
