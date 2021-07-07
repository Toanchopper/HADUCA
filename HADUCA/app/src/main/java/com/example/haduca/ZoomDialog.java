package com.example.haduca;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

public class ZoomDialog extends DialogFragment {

    static final String IMAGE="IMAGE";
    static final String ID="ID";
    static final String POS="POS";
    ImageView imgzoom;
    Button btnchoose;
    public static ZoomDialog newInstance(int pos,Bitmap bitmap,String id) {

        Bundle args = new Bundle();
        ZoomDialog fragment = new ZoomDialog();
        args.putParcelable(IMAGE,bitmap);
        args.putString(ID,id);
        args.putInt(POS,pos);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.image_zoom,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgzoom=(ImageView) view.findViewById(R.id.imgzoom);
        btnchoose=(Button) view.findViewById(R.id.btnchoose);
        final Bitmap b =getArguments().getParcelable(IMAGE);
        final String id=getArguments().getString(ID);
        final int pos=getArguments().getInt(POS);
        imgzoom.setImageBitmap(b);
        btnchoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm=getFragmentManager();
                CakeDialog cakeDialog=CakeDialog.newInstance(pos,b,id);
                cakeDialog.show(fm,null);
                getDialog().dismiss();
            }
        });
    }
}
