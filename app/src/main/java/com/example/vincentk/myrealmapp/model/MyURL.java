package com.example.vincentk.myrealmapp.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * Created by vincentk on 26/06/2017.
 */

@RealmClass
public class MyURL extends RealmObject {

    @PrimaryKey
    private long id;

    @Required
    @Index
    private String name;

    @Required
    @Index
    private String url;

    @Index
    private Date updateDate;

    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public MyURL(long id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public  MyURL() {
    }
}
