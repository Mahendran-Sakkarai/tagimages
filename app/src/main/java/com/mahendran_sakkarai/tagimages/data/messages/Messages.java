package com.mahendran_sakkarai.tagimages.data.messages;

/**
 * Created by Mahendran Sakkarai on 10/28/2016.
 */

public class Messages {
    private int id;
    private String type;
    private String message;
    private String by;
    private int sentTime;

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

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public int getSentTime() {
        return sentTime;
    }

    public void setSentTime(int sentTime) {
        this.sentTime = sentTime;
    }
}
