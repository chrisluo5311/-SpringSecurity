package com.kucw.security.service;

import com.kucw.security.dao.MyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyServiceImpl implements MyService {

    @Autowired
    private MyDao myDao;

    @Override
    public String getMovie() {
        System.out.println("執行 MyService 的 getMovie 方法");

        myDao.getMovie();

        return "成功取得電影";
    }

    @Override
    public String deleteMovie() {
        System.out.println("執行 MyService 的 deleteMovie 方法");

        myDao.deleteMovie();

        return "成功刪除電影";
    }
}
