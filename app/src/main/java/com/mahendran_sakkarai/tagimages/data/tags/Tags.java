package com.mahendran_sakkarai.tagimages.data.tags;

import com.mahendran_sakkarai.tagimages.data.images.Images;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahendran Sakkarai on 10/28/2016.
 */

public class Tags {
    private int id;

    private String tag;

    private List<Images> imagesList = new ArrayList<>();

    public Tags(int id, String tagName) {
        this.id = id;
        this.tag = tagName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<Images> getImagesList() {
        return imagesList;
    }

    public void setImagesList(List<Images> imagesList) {
        this.imagesList = imagesList;
    }
}
