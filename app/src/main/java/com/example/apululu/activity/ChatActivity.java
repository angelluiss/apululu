package com.example.apululu.activity;

import android.content.Context;

import android.content.SharedPreferences;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.apululu.R;
import com.example.apululu.adapter.ChatAdapter;
import com.example.apululu.helper.HTTPHelper;

import com.example.apululu.model.ChatMessage;

import com.example.apululu.model.MessageUsage;
import com.example.apululu.utils.URLS;
import com.example.apululu.utils.Util;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ChatActivity extends AppCompatActivity {

    private EditText messageET;
    private ListView messagesContainer;
    private Button sendBtn;
    private ChatAdapter adapter;
    private ArrayList<ChatMessage> chatHistory;
    private String messageRev;

    private SharedPreferences prefs;
    String chatRoom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);


        final Socket socket = this.getSocket();
        socket.connect();

        socket.emit("chat", chatRoom);

        socket.on("new message", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {

                JSONObject hello;
                try {
                    JSONObject data =  (JSONObject) args[0];
                    //   hello = data.getJSONObject("from");
                    //  String dato = hello.toString();
                    Log.d("SocketMessage",data.toString());
                    messageRev = data.getString("data");
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.setId(122);//dummy
                    chatMessage.setMessage(messageRev);
                    chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                    chatMessage.setMe(true);
                    displayMessage(chatMessage);
                } catch (Exception e) {
                    String error = e.toString();
                    Log.d("SocketMessage",error);
                    return;
                }

            }
        });



        initControls();


      /*  sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                HTTPHelper sendMessage = new HTTPHelper(ChatActivity.this);

                sendMessage.petitionData(URLS.MAIN_URL + "/matches/chat-rooms/send", Util.getTokenPrefs(prefs),"POST",messagePetition, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("RespuestaMessage", response.toString());
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(ChatActivity.this, error.toString() + " Disliked", Toast.LENGTH_SHORT).show();
                            }
                        } );
            }
        });*/


    }

    private Socket getSocket()
    {
        Socket socket = null;
        try {
            IO.Options opts = new IO.Options();
            opts.query = "token=" + Util.getTokenPrefs(prefs);
            socket = IO.socket("http://192.168.2.117:3000", opts);
            Log.d("NotificationsSocket", socket.toString());
        } catch (URISyntaxException e) {}
        return socket;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("file_path", messageRev);
    }



    private void initControls() {
        messagesContainer = (ListView) findViewById(R.id.messagesContainer);
        messageET = (EditText) findViewById(R.id.messageEdit);
        sendBtn = (Button) findViewById(R.id.chatSendButton);

        TextView meLabel = (TextView) findViewById(R.id.meLbl);
        TextView companionLabel = (TextView) findViewById(R.id.friendLabel);
        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
        companionLabel.setText("My Buddy");// Hard Coded
        loadDummyHistory();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }

                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setId(122);//dummy
                chatMessage.setMessage(messageText);
                chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                chatMessage.setMe(true);


                Bundle parametros = getIntent().getExtras();
                if(parametros !=null){
                    chatRoom = parametros.getString("chatRoom");
                    Log.d("Itemmm ", chatRoom);
                }
               JSONObject messagePetition = new JSONObject();
                try {
                    messagePetition.put("chat",chatRoom);
                    Log.d("petition",messagePetition.getString("chat"));
                    messagePetition.put("message", messageET.getText().toString());
                    Log.d("petition3452135",messagePetition.getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                HTTPHelper sendMessage = new HTTPHelper(ChatActivity.this);

                sendMessage.petitionData(URLS.MAIN_URL + "/matches/chat-rooms/send", Util.getTokenPrefs(prefs),"POST",messagePetition, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("RespuestaMessage", response.toString());
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(ChatActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        } );
                messageET.setText("");
                displayMessage(chatMessage);
            }
        });
    }

    public void displayMessage(ChatMessage message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        messageRev = savedInstanceState.getString("file_path");
    }

    private void loadDummyHistory() {

        chatHistory = new ArrayList<ChatMessage>();

        ChatMessage msg = new ChatMessage();
        msg.setId(1);
        msg.setMe(false);
        msg.setMessage("Hi");
        msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg);
        ChatMessage msg1 = new ChatMessage();
        msg1.setId(2);
        msg1.setMe(false);
        msg1.setMessage("How r u doing???");
        msg1.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg1);

        adapter = new ChatAdapter(ChatActivity.this, new ArrayList<ChatMessage>());
        messagesContainer.setAdapter(adapter);

        for (int i = 0; i < chatHistory.size(); i++) {
            ChatMessage message = chatHistory.get(i);
            displayMessage(message);
        }
    }


}
