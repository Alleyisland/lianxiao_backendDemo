package com.lianxiao.demo.simpleserver.model;

import com.alibaba.fastjson.annotation.JSONType;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Document(indexName = "post", replicas = 0)
@JSONType(orders = {"id", "type", "owner_id" ,"owner_name","title", "content"})
public class Post {
    @Id
    private Long id;

    @Field(type = FieldType.Integer)
    private Integer type;

    @Field(type = FieldType.Long)
    private String owner_id;
    @Field(type = FieldType.Long)
    private String owner_name;

    @Field(type = FieldType.Date,format = DateFormat.basic_date_time_no_millis)
    private Date date;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String content;

    public Post(Long id, Integer type, String owner_id, String owner_name,Date date, String title, String content) {
        this.id = id;
        this.type = type;
        this.owner_id = owner_id;
        this.owner_name = owner_name;
        this.date = date;
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

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }
    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", type=" + type +
                ", owner_id='" + owner_id + '\'' +
                ", owner_name='" + owner_name + '\'' +
                ", date=" + date +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
