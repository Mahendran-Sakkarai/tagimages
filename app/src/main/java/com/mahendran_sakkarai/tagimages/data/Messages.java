package com.mahendran_sakkarai.tagimages.data;

/**
 * Created by Mahendran Sakkarai on 10/28/2016.
 */

public class Messages<T> {
    private int id;
    private T message;
    private String by;
    private int sentTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
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
