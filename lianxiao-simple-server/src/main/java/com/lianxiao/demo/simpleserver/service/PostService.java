package com.lianxiao.demo.simpleserver.service;

import com.lianxiao.demo.simpleserver.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service("elasticService")
public interface PostService extends ElasticService<Post> {

    @Override
    void createIndex();

    @Override
    void deleteIndex(String index);

    @Override
    void save(Post post);

    @Override
    void saveAll(List<Post> list);

    @Override
    List<Post> findAll();

    @Override
    Page<Post> query(String keyword);

    void thumbUp(long pid);

    int getThumbUp(long pid);
    Set<Object> getTopKPost();
}


