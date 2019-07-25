package com.example.apululu.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.apululu.R;
import com.example.apululu.helper.HTTPHelper;
import com.example.apululu.utils.URLS;
import com.example.apululu.utils.Util;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class ChatActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    String chatRoom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        Bundle parametros = this.getIntent().getExtras();
        if(parametros !=null){
            chatRoom = parametros.getString("chatRoom");
            Log.d("Itemmm ", chatRoom);
        }

        Socket socket = this.getSocket();
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

                } catch (Exception e) {
                    String error = e.toString();
                    Log.d("SocketMessage",error);
                    return;
                }

            }
        });


        ImageButton sendMessage = (ImageButton)findViewById(R.id.btnSendMessage);
        final EditText editMessage = (EditText)findViewById(R.id.editTxtMessage);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject messagePetition = new JSONObject();
                try {
                    messagePetition.put("chat",chatRoom);
                    messagePetition.put("message", editMessage.getText().toString());
                    if (!editMessage.getText().toString().isEmpty()){
                        editMessage.setText("");
                    }
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
                                Toast.makeText(ChatActivity.this, error.toString() + " Disliked", Toast.LENGTH_SHORT).show();
                            }
                        } );
            }
        });


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


}
