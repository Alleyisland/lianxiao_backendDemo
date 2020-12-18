package com.lianxiao.demo.simpleserver.model;

import com.lianxiao.demo.simpleserver.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "`film`")
public class Film extends BaseEntity {

    /**
     * 电影名
     */
    @Column(name = "`filmName`")
    private String filmName;

    /**
     * 年份
     */
    @Column(name = "`year`")
    private Integer year;

    /**
     * 导演
     */
    @Column(name = "`director`")
    private String director;

    /**
     * 男主演
     */
    @Column(name = "`actor`")
    private String actor;

    /**
     * 女主演
     */
    @Column(name = "`actress`")
    private String actress;


    /**
     * 地区
     */
    @Column(name = "`district`")
    private String district;


    /**
     * 封面图
     */
    @Column(name = "`pic_url`")
    private String pic_url;

    /**
     * 链接
     */
    @Column(name = "`link`")
    private String link;

    public Film(String filmName, Integer year, String director, String actor, String actress, String district, String pic_url, String link) {
        this.filmName = filmName;
        this.year = year;
        this.director = director;
        this.actor = actor;
        this.actress = actress;
        this.district = district;
        this.pic_url = pic_url;
        this.link = link;
    }


    public Film() {

    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getActress() {
        return actress;
    }

    public void setActress(String actress) {
        this.actress = actress;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}