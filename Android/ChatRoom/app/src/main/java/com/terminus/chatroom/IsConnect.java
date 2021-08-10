package com.terminus.chatroom;

public class IsConnect {

    private final int id;
    private final int status;
    private final int requestType;

    public IsConnect(int id, int status) {
        this.id = id;
        this.status = status;
        this.requestType = RequestType.IS_CONNECTED;
    }

    public int getRequestType() {
        return requestType;
    }

    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }
}
