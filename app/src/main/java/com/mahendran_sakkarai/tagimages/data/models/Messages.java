package com.mahendran_sakkarai.tagimages.data.models;

import android.text.Editable;

/**
 * Created by Mahendran Sakkarai on 10/28/2016.
 */

public class Messages {
    private int id;
    private String type;
    private String message;
    private int imageId;
    private boolean active;
    private String by;
    private long sentTime;

    public Messages(int id, String message, int imageId, boolean active, String messageType, String by, long sentTime) {
        this.id = id;
        this.message = message;
        this.imageId = imageId;
        this.active = active;
        this.type = messageType;
        this.by = by;
        this.sentTime = sentTime;
    }

    public Messages(String message, String messageType, String by, long timeInMillis) {
        this.message = message;
        this.type = messageType;
        this.by = by;
        this.sentTime = timeInMillis;
    }

    public Messages(int imageId, boolean active, String messageType, String by, long sentTime) {
        this.imageId = imageId;
        this.active = active;
        this.type = messageType;
        this.by = by;
        this.sentTime = sentTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public long getSentTime() {
        return sentTime;
    }

    public void setSentTime(long sentTime) {
        this.sentTime = sentTime;
    }
}
