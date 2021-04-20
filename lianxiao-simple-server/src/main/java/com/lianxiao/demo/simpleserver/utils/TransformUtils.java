package com.lianxiao.demo.simpleserver.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TransformUtils {
    public static <T> List<T> Iter2List(Iterator<T> iterator){
        List<T> res= new ArrayList<>();
        while(iterator.hasNext()) {
            res.add(iterator.next());
        }
        return res;
    }
}
