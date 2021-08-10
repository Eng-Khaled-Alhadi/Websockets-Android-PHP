package com.terminus.chatroom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    //    private WebSocket webSocket;
    public static MainSocketServer socketServer;
    private MessageAdapter adapter;
    public static int userId = 0;
    public static int toUid = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        socketServer = new MainSocketServer();
        ListView messageList = findViewById(R.id.messageList);
        final EditText messageBox = findViewById(R.id.messageBox);
        TextView send = findViewById(R.id.send);

        adapter = new MessageAdapter();
        messageList.setAdapter(adapter);

        MainActivity.socketServer.getListener().setOnMessage(new com.terminus.chatroom.SocketListener.OnMessage() {
            @Override
            public void onMessage(final Message message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        JSONObject jsonObject = new JSONObject();

                        try {


                            jsonObject.put("message", message.getData());
                            jsonObject.put("byServer", true);

                            adapter.addItem(jsonObject);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if (MainActivity.socketServer.send(new IsConnect(toUid, 0))) {
//                    Log.d("test_send","success");
//
//                }else {
//
//                    Log.d("test_send","filed");
//                }

                String message = messageBox.getText().toString();


                if (!message.isEmpty()) {

                    Message message1 = new Message(
                            userId, toUid, message, 1, String.valueOf(Calendar.getInstance().getTimeInMillis()), 0
                    );
                    if (MainActivity.socketServer.send(message1)) {
                        messageBox.setText("");

                    } else {
                        Toast.makeText(MainActivity.this, "cant'n send", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    JSONObject jsonObject = new JSONObject();

                    try {


                        jsonObject.put("message", message1.getData());
                        jsonObject.put("byServer", false);

                        adapter.addItem(jsonObject);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        MainActivity.socketServer.getListener().setOnConfirmMessage(new SocketListener.OnConfirmMessage() {
            @Override
            public void onConfirmMessage(final String date) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, date, Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        MainActivity.socketServer.getListener().setIsConnect(new SocketListener.OnIsConnect() {
            @Override
            public void isConnect(final int status) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getSupportActionBar().setTitle(status > 0 ? "online" : "offline");
                    }
                });
            }
        });


        MainActivity.socketServer.getListener().setOnNotification(new SocketListener.OnNotification() {
            @Override
            public void onNotification(final MyNotification notification) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, notification.getTitle(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainActivity.socketServer.onDestroy();
    }


    public class MessageAdapter extends BaseAdapter {


        List<JSONObject> messagesList = new ArrayList<>();


        @Override
        public int getCount() {
            return messagesList.size();
        }

        @Override
        public Object getItem(int i) {
            return messagesList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            if (view == null)
                view = getLayoutInflater().inflate(R.layout.message_list_item, viewGroup, false);


            TextView sentMessage = view.findViewById(R.id.sentMessage);
            TextView receivedMessage = view.findViewById(R.id.receivedMessage);


            JSONObject item = messagesList.get(i);


            try {

                if (item.getBoolean("byServer")) {


                    receivedMessage.setVisibility(View.VISIBLE);
                    receivedMessage.setText(item.getString("message"));


                    sentMessage.setVisibility(View.INVISIBLE);


                } else {


                    sentMessage.setVisibility(View.VISIBLE);
                    sentMessage.setText(item.getString("message"));


                    receivedMessage.setVisibility(View.INVISIBLE);


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


            return view;
        }


        void addItem(JSONObject item) {


            messagesList.add(item);
            notifyDataSetChanged();


        }


    }

}
