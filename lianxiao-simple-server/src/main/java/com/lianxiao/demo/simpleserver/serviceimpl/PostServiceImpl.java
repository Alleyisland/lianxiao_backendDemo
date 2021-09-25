package com.lianxiao.demo.simpleserver.serviceimpl;

import com.lianxiao.demo.simpleserver.dao.PostRepository;
import com.lianxiao.demo.simpleserver.model.Post;
import com.lianxiao.demo.simpleserver.service.PostService;
import com.lianxiao.demo.simpleserver.utils.RedisUtils;
import com.lianxiao.demo.simpleserver.utils.TransformUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@Service("elasticService")
public class PostServiceImpl implements PostService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private RedisUtils redisUtils;

    public static final String THUMB_UP_KEY = "post_thumb_up";

    public static final String THUMB_UP_RANK = "post_thumb_up_rank";

    public static final int TOP_K=10;

    private final Pageable pageable = PageRequest.of(0, 10);

    private static final String WEIBO_HOT_KEY_PREFIX = "weibo_hot_";

    @Override
    public void createIndex() {
        elasticsearchTemplate.createIndex(Post.class);
    }

    @Override
    public void deleteIndex(String index) {
        elasticsearchTemplate.deleteIndex(index);
    }

    @Override
    public void save(Post post) {
        postRepository.save(post);
    }

    @Override
    public void saveAll(List<Post> list) {
        postRepository.saveAll(list);
    }

    @Override
    public List<Post> findAll() {
        return TransformUtils.Iter2List(postRepository.findAll().iterator());
    }

    @Override
    public Post findById(long postId) {
        try {
            return postRepository.findById((int) postId).get();
        }catch (NoSuchElementException e){
            return null;
        }
    }

    /*@Override
    public Page<Post> findByTitle(String title) {
        return elasticRepository.findByTitle(title,pageable);
    }*/

    @Override
    public Page<Post> query(String keyword) {
        if (StringUtils.isEmpty(keyword)) {
            return postRepository.findAll(pageable);
        }
        NativeSearchQuery builder = new NativeSearchQueryBuilder()
                .withQuery(matchQuery("title", keyword))
                .withQuery(matchQuery("content", keyword))
                .withPageable(pageable)
                .build();
        return postRepository.search(builder);
    }

    /*
    public void thumbUp(long pid) {
        String key= "post_thumb_up_" + pid;
        redisOp.incr(key);
    }

    public int getThumbUp(long pid) {
        String key= "post_thumb_up_" + pid;
        return redisOp.getNum(key);
    }
     */

    public void thumbUp(long pid) {
        String strPid= redisUtils.num2Str(pid);
        int oldValue= redisUtils.hGetNum(THUMB_UP_KEY,strPid);
        redisUtils.hSet(THUMB_UP_KEY,strPid, oldValue + 1);
        redisUtils.zSetAdd(THUMB_UP_RANK,strPid,oldValue + 1);
    }

    public int getThumbUp(long pid) {
        String strPid= redisUtils.num2Str(pid);
        return redisUtils.hGetNum(THUMB_UP_KEY,strPid);
    }

    public Set<Object> getTopKPost() {
        return redisUtils.zSetRange(THUMB_UP_RANK,0,TOP_K);
    }

    public List<Post> fetchWeiboHot(Date date) {
        int month=date.getMonth()+1;
        int day=date.getDate();
        if(date.getHours()<12)
            day=day-1;
        String key=WEIBO_HOT_KEY_PREFIX+month+"_"+day;
        return redisUtils.getPostList(key);
    }

    /*@Override
    public Page<Post> query(String keyword) {
        return elasticRepository.findByContent(keyword,pageable);
    }*/
}


