package com.terminus.chatroom;

import android.app.DownloadManager;

public class ConnectID {

    private final int userId;
    private final int userType;
    private final int requestType;

    public ConnectID(int userId, int userType) {
        this.userId = userId;
        this.userType = userType;
        this.requestType = RequestType.CONNECT_ID;
    }

    public int getRequestType() {
        return requestType;
    }

    public int getUserId() {
        return userId;
    }

    public int getUserType() {
        return userType;
    }
}
