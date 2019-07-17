package com.example.apululu.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.apululu.R;
import com.example.apululu.interfaces.Card;
import com.example.apululu.utils.cards;

import java.util.List;


public class DeckAdapter extends ArrayAdapter<Pair<String, Integer>> {

    private final int mResource;

    public DeckAdapter(@NonNull Context context, @NonNull List<Pair<String, Integer>> objects) {
        super(context, R.layout.item, objects);
        mResource = R.layout.item;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        final RelativeLayout layout;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (RelativeLayout) inflater.inflate(mResource, parent, false);
        } else
            layout = (RelativeLayout) convertView;

        Pair item = getItem(position);
        if (item != null) {
            layout.setBackgroundColor((Integer) item.second);
            ((TextView) layout.findViewById(R.id.item_text)).setText((String) item.first);
            ((TextView) layout.findViewById(R.id.item_text_name)).setText((String) item.first);
        }
        return layout;
    }

}
