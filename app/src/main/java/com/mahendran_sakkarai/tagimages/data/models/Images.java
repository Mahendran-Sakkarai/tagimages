package com.mahendran_sakkarai.tagimages.data.models;

/**
 * Created by Mahendran Sakkarai on 10/28/2016.
 */

public class Images {
    private int id;
    private String imageUrl;

    public Images(int id, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
