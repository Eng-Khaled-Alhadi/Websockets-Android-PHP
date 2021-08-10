package com.terminus.chatroom;

import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class SocketListener extends WebSocketListener {
    private OnMessage onMessage;
    private OnResponse onResponse;
    private OnNotification onNotification;
    private OnConfirmMessage onConfirmMessage;
    private OnIsConnect isConnect;

    public void setOnMessage(OnMessage onMessage) {
        this.onMessage = onMessage;
    }

    public void setOnNotification(OnNotification onNotification) {
        this.onNotification = onNotification;
    }

    public void setOnResponse(OnResponse onResponse) {
        this.onResponse = onResponse;
    }

    public void setOnConfirmMessage(OnConfirmMessage onConfirmMessage) {
        this.onConfirmMessage = onConfirmMessage;
    }

    public void setIsConnect(OnIsConnect isConnect) {
        this.isConnect = isConnect;
    }


    interface OnMessage{
        void onMessage(Message message);
    }

    interface OnResponse{
        void onResponse(String response);
    }

    interface OnNotification{
        void onNotification(MyNotification notification);
    }

    interface OnConfirmMessage{
        void onConfirmMessage(String date);
    }

    interface OnIsConnect{
        void  isConnect(int status);
    }

    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        Log.d("test_socket_closed",reason);
    }

    @Override
    public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        Log.d("test_socket_closing",reason);
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        try {
            if(onResponse!=null){
                onResponse.onResponse(text);
            }

            JSONObject object = new JSONObject(text);
            int requestType =  object.getInt("requestType");
            Log.d("test_request_type", String.valueOf(requestType));
            Log.d("test_response", text);
            switch (requestType){
                case RequestType.NEW_MESSAGE:
                    Message message = CastResponse.cast(text,Message.class);
                    if(onMessage!= null)onMessage.onMessage(message);
                    break;
                case RequestType.CONFIRM_MESSAGE:
                    if(onConfirmMessage!= null)onConfirmMessage.onConfirmMessage(object.getString("date"));
                    break;
                case RequestType.IS_CONNECTED:
                    if(isConnect!= null)isConnect.isConnect(object.getInt("status"));
                    break;
                case RequestType.NEW_PUBLIC_NOTIFICATION : case RequestType.NEW_PUBLIC_NOTIFICATION_FOR_STUDENT:
                    MyNotification notification = CastResponse.cast(text,MyNotification.class);
                    if(onNotification!= null)onNotification.onNotification(notification);
                    break;

                default:

                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        webSocket.send(new Gson().toJson(new ConnectID(MainActivity.userId,1)));
    }
}
