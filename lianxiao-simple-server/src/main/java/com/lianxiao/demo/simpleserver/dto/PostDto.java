package com.lianxiao.demo.simpleserver.dto;

import com.lianxiao.demo.simpleserver.model.Post;
import com.lianxiao.demo.simpleserver.model.Reply;

import java.util.List;

public class PostDto {

    private Post post;
    private List<Reply> replies;
    private int like_cnt;

    public PostDto() {

    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public int getLike_cnt() {
        return like_cnt;
    }

    public void setLike_cnt(int like_cnt) {
        this.like_cnt = like_cnt;
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }
}
