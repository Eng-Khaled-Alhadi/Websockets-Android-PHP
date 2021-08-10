package com.terminus.chatroom;

import android.os.Parcel;

public class Message {
    private int id;
    private final int type,uid;
    private int status = 0;
    private final int toUid;
    private String data;
    private final int isToGroup;
    private final int requestType;
    private final String time;



    public Message(int uid, int toUid, String data, int type, String time, int isToGroup) {
        this.type = type;
        this.data = data;
        this.uid = uid;
        this.toUid = toUid;
        this.isToGroup = isToGroup;
        this.time = time;
        this.requestType = RequestType.NEW_MESSAGE;

    }

    public int getRequestType() {
        return requestType;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(int status) {
        this.status = status;
    }





    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public int getStatus() {
        return status;
    }

    public String getData() {
        return data;
    }

    public int getUid() {
        return uid;
    }

    public int getToUid() {
        return toUid;
    }

    public int getIsToGroup() {
        return isToGroup;
    }

    public String getTime() {
        return time;
    }



}
