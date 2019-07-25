package com.example.apululu.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.apululu.R;
import com.example.apululu.utils.Cards;

import java.util.List;



public class DeckAdapter extends ArrayAdapter<Cards> {

    private final int mResource;

    public DeckAdapter(@NonNull Context context, @NonNull List<Cards> objects) {
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

        Cards item = getItem(position);
        if (item != null) {
            ((TextView) layout.findViewById(R.id.item_text)).setText(item.getName());
          //  ((TextView) layout.findViewById(R.id.item_text2)).setText(item.getName());
            Drawable d = new BitmapDrawable(getContext().getResources(),item.getDrawable());
            ((ImageView) layout.findViewById(R.id.image_play)).setImageDrawable(d);
        }
        return layout;
    }

}
