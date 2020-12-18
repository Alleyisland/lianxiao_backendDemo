package com.lianxiao.demo.simpleserver.dao;

import com.lianxiao.demo.simpleserver.model.Post;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PostRepository extends ElasticsearchRepository<Post, Integer> {


}