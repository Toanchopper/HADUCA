package com.example.haduca;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class GridAdapter extends ArrayAdapter<Bitmap> {
    Context context;
    int resource;
    List<Bitmap> list;
    public GridAdapter(@NonNull Context context, int resource, @NonNull List<Bitmap> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.list=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null)
        {
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_cake,parent,false);
            viewHolder.imgcake=(ImageView) convertView.findViewById(R.id.imgcake);

            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }

        Bitmap bm=list.get(position);
        viewHolder.imgcake.setImageBitmap(bm);

        return convertView;
    }

    public class ViewHolder
    {
        ImageView imgcake;
    }
}
