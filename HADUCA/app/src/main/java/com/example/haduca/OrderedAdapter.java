package com.example.haduca;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class OrderedAdapter extends ArrayAdapter<Cake> {
    Context context;
    int resource;
    List<Cake> cakes;
    public OrderedAdapter(@NonNull Context context, int resource, @NonNull List<Cake> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.cakes=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null)
        {
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.cake_ordered,parent,false);
            viewHolder.img_order=(ImageView) convertView.findViewById(R.id.img_ordered);
            viewHolder.id_order=(TextView) convertView.findViewById(R.id.id_ordered);
            viewHolder.price_order=(TextView) convertView.findViewById(R.id.price_ordered);
            viewHolder.mess_order=(TextView) convertView.findViewById(R.id.mess_ordered);
            viewHolder.prepaid_order=(TextView) convertView.findViewById(R.id.prepaid_ordered);

            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }
        Cake cake=cakes.get(position);
        viewHolder.img_order.setImageBitmap(cake.getPicture());
        viewHolder.id_order.setText(cake.getId());
        viewHolder.price_order.setText(String.valueOf(cake.getPrice()));
        viewHolder.mess_order.setText(cake.getMessage());
        viewHolder.prepaid_order.setText(String.valueOf(cake.getPrepaid()));

        return convertView;
    }

    public class ViewHolder
    {
        ImageView img_order;
        TextView id_order;
        TextView price_order;
        TextView mess_order;
        TextView prepaid_order;
    }
}
