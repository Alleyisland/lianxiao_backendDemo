package com.lianxiao.demo.simpleserver.service;

import com.lianxiao.demo.simpleserver.dao.PostRepository;
import com.lianxiao.demo.simpleserver.model.Post;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@Service("elasticService")
public class PostService implements ElasticService<Post> {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;
    @Autowired
    private PostRepository postRepository;

    private Pageable pageable = PageRequest.of(0,10);

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
    public Iterator<Post> findAll() {
        return postRepository.findAll().iterator();
    }

    /*@Override
    public Page<Post> findByTitle(String title) {
        return elasticRepository.findByTitle(title,pageable);
    }*/

    @Override
    public Page<Post> query(String keyword) {
        if(StringUtils.isEmpty(keyword)){
            return postRepository.findAll(pageable);
        }
        NativeSearchQuery builder = new NativeSearchQueryBuilder()
                .withQuery(matchQuery("title", keyword))
                .withQuery(matchQuery("content", keyword))
                .withPageable(pageable)
                .build();
        Page<Post> posts = postRepository.search(builder);
        return posts;
    }

    /*@Override
    public Page<Post> query(String keyword) {
        return elasticRepository.findByContent(keyword,pageable);
    }*/
}


