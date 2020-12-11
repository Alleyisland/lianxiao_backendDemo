package com.lianxiao.demo.simpleserver.service;

import com.lianxiao.demo.simpleserver.model.Post;
import org.springframework.data.domain.Page;

import java.util.Iterator;
import java.util.List;

public interface ElasticService<T> {

    void createIndex();

    void deleteIndex(String index);

    void save(T t);

    void saveAll(List<T> list);

    Iterator<Post> findAll();

    //Page<Post> findByTitle(String title);

    Page<Post> query(String keyword);

    /*Page<Post> query(String keyword);*/
}
