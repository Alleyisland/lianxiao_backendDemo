package com.lianxiao.demo.simpleserver.model;

import com.alibaba.fastjson.annotation.JSONType;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "post", replicas = 0)
@JSONType(orders = {"id", "type", "title", "content"})
public class Post {
    @Id
    private Long id;

    @Field(type = FieldType.Integer)
    private Integer type;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String content;

    public Post(Long id, Integer type, String title, String content) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.content = content;
    }

    public Post() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
