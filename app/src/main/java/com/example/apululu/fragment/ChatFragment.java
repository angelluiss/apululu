package com.example.apululu.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.apululu.R;
import com.example.apululu.activity.ChatActivity;
import com.example.apululu.adapter.ChatListAdapter;
import com.example.apululu.adapter.CustomListAdapter;
import com.example.apululu.helper.HTTPHelper;
import com.example.apululu.model.ChatList;
import com.example.apululu.model.Notification;
import com.example.apululu.utils.URLS;
import com.example.apululu.utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {


    HTTPHelper getChats;
    public ArrayList<ChatList> chatListsArrayList;
    ListView listView;

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Share Preference Token
        final  SharedPreferences token = getActivity().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        chatListsArrayList = new ArrayList<>();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        listView = (ListView) view.findViewById(R.id.lvChatList);
        getChats = new HTTPHelper(getActivity());

        getChats.getDataArray(URLS.CHAT_LIST, Util.getTokenPrefs(token), "GET", null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        parseJSONArray(response, Util.getTokenPrefs(token));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });


        return view;
    }


    private void parseJSONArray(JSONArray array, String token) {
        try {
            for (int count = 0; count < array.length(); count++) {
                String item = array.getString(count);
                Log.d("Itemddd", item);
                getChats.petitionData(URLS.CHAT_LIST + item + "/from",token, "GET", null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                for (int count = 0; count < (response.length() - 2); count++){

                                    try {
                                     //   object = response.getJSONObject(count);
                                        JSONObject data = response.getJSONObject("profile");
                                        Log.d("Falta235", data.toString());
                                        String nameChat = data.getString("firstName");
                                        String lastNameChat = data.getString("lastName");
                                        String image = data.getString("image");

                                        ChatList chatListModel = new ChatList(R.drawable.heart_1_like, nameChat,lastNameChat);

                                        chatListsArrayList.add(chatListModel);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                                Log.d("Activitiess",chatListsArrayList.toString());
                                ChatListAdapter whatever =  new ChatListAdapter(getActivity(),chatListsArrayList);

                                listView.setAdapter(whatever);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position,
                                                            long id) {
                                        Intent chat = new Intent(getContext(), ChatActivity.class);
                                        startActivity(chat);
                                    }
                                });
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Errrorrr",error.toString());
                            }
                        });


            }
        } catch (JSONException exception) {
            Log.d("arrayerror", exception.toString());
        }
    }


}
