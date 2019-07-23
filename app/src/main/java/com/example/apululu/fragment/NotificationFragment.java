package com.example.apululu.fragment;


import android.content.Context;
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
import com.example.apululu.activity.ChataAndNotificationActivity;
import com.example.apululu.adapter.CustomListAdapter;
import com.example.apululu.helper.HTTPHelper;
import com.example.apululu.model.Notification;
import com.example.apululu.utils.URLS;
import android.content.SharedPreferences;
import com.example.apululu.utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {
    public ArrayList<Notification> notificationArrayList;
    ListView listView;
    public NotificationFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Share Preference Token
        SharedPreferences token = getActivity().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        notificationArrayList = new ArrayList<>();
        /// Get Notifications
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        listView = (ListView) view.findViewById(R.id.lvNotification);

        HTTPHelper getNotification = new HTTPHelper(getActivity());

        getNotification.getDataArray(URLS.NOTIFICATION_USER, Util.getTokenPrefs(token), "GET", null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        parseJSONArray(response);
                        Log.d("arrayError", notificationArrayList.toString());

                        Log.d("ArrayList", notificationArrayList.toString());
                        CustomListAdapter whatever =  new CustomListAdapter(getActivity(),notificationArrayList);

                        listView.setAdapter(whatever);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position,
                                                    long id) {

                            }
                        });
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        return view;
    }

    private void parseJSONArray(JSONArray array) {


        try {
            for (int count = 0; count < array.length(); count++) {
                JSONObject object = array.getJSONObject(count);


                JSONObject data = object.getJSONObject("data");
                String notificationType = data.getString("type");

                JSONObject from = data.getJSONObject("from");
                String fromUserId = from.getString("userId");

                String firstName = from.getString("firstName");
                String lastName = from.getString("lastName");
                String image = from.getString("image");

                int numberNotifications = array.length();

                Notification notificationModel = new Notification(R.drawable.heart_1_like, firstName,lastName,notificationType,array, numberNotifications);

                notificationArrayList.add(notificationModel);
                Log.d("Ocurre?", String.valueOf(count));

                //      putDBDates(sex, firstName, lastName, image, distance,birthdate);
                //    DownloadImageFromPath(urls.MAIN_URL_IMAGES + image);

            }
        } catch (JSONException exception) {
            Log.d("arrayerror", exception.toString());
        }
    }

}
