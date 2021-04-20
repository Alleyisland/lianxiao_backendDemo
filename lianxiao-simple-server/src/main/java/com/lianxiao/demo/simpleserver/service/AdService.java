package com.lianxiao.demo.simpleserver.service;

import com.lianxiao.demo.simpleserver.model.Ad;

import java.util.List;

public interface AdService {

    List<Ad> showAllAd();

    void deleteAd(long aid);
}
