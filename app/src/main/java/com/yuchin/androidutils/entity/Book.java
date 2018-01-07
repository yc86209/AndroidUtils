package com.yuchin.androidutils.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Creator：YOOOOO on 2017/12/25 02:14
 * Mail：youchin_li@newsoft.com.tw
 **/


@Entity
public class Book {
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private Long userId;
    @NotNull
    private String price;

    @Generated(hash = 1922075286)
    public Book(Long id, @NotNull Long userId, @NotNull String price) {
        this.id = id;
        this.userId = userId;
        this.price = price;
    }

    @Generated(hash = 1839243756)
    public Book() {
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", userId=" + userId +
                ", price='" + price + '\'' +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


}