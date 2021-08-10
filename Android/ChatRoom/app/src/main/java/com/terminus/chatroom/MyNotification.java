package com.terminus.chatroom;

public class MyNotification {


    private final String title;
    private final String subTitle;
    private int requestType;

    public MyNotification(String title, String subTitle) {
        this.title = title;
        this.subTitle = subTitle;
        this.requestType = RequestType.NEW_PUBLIC_NOTIFICATION;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    public int getRequestType() {
        return requestType;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }
}
