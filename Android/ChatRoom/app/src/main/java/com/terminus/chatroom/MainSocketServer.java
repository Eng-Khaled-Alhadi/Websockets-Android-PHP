package com.terminus.chatroom;

import android.util.Log;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class MainSocketServer  {
    private WebSocket webSocket;
    private static final String host = "172.16.16.12";
    private static final String IP = "ws://" + host + ":8080";
    private static final SocketListener listener = new SocketListener();
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder().url(IP).build();
    public MainSocketServer() {
        webSocket = client.newWebSocket(request, listener);
    }

    private boolean _send(String s){
        boolean result = webSocket.send(s);
        if(!result) {
            webSocket = client.newWebSocket(request, listener);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result = webSocket.send(s);
        }
        return result ;
    }

    public boolean send(Object object)  {
        Gson gson = new Gson();
        String json = gson.toJson(object);

        if(json.contains("requestType")){
            boolean result = _send(json);
            Log.d("test_socket_send", String.valueOf(result));
            return result;
        }else {
            new Exception("not found a variable requestType").printStackTrace();
            return false;
        }
    }

    public boolean send(String json) {

        if(json.contains("requestType")){
            boolean result = _send(json);
            Log.d("test_socket_send", String.valueOf(result));
            return result;
        }else {
            new Exception("not found a variable requestType").printStackTrace();
            return false;

        }


    }

    public SocketListener getListener() {
        return listener;
    }

    protected void onDestroy() {
        webSocket.cancel();
        MainActivity.socketServer = null;
    }


}
