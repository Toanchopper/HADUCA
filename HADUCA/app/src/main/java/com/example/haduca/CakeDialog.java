package com.example.haduca;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class CakeDialog extends DialogFragment {

    ImageView imvcake;
    TextView tvid;
    EditText edtprice,edtmessage,edtprepaid;
    Button btnok,btncancel;
    static final String IMAGE="IMAGE";
    static final String ID="ID";
    static final String POS="POS";
    static final String PRICE="PRICE";
    static final String MESSAGE="MESSAGE";
    static final String PREPAID="PREPAID";
    static final String CAKE="CAKE";
    static final int REQUEST_CODE=1;
    Context context;
    DBManager db;
    public static CakeDialog newInstance(int pos,Bitmap image,String id) {
        Bundle args = new Bundle();
        args.putString(ID,id);
        args.putParcelable(IMAGE,image);
        args.putInt(POS,pos);
        CakeDialog fragment = new CakeDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.order_dialog,container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imvcake=(ImageView) view.findViewById(R.id.imvcake);
        tvid=(TextView) view.findViewById(R.id.tvid);
        edtprice=(EditText) view.findViewById(R.id.edtprice);
        edtmessage=(EditText) view.findViewById(R.id.edtmesage);
        edtprepaid=(EditText) view.findViewById(R.id.edtprepaid);
        btnok=(Button) view.findViewById(R.id.btnok);
        btncancel=(Button) view.findViewById(R.id.btncancel);

        final Bitmap image=getArguments().getParcelable(IMAGE);
        final String id=getArguments().getString(ID);
        final int pos=getArguments().getInt(POS);
        db=new DBManager(getContext());
        imvcake.setImageBitmap(image);
        tvid.setText(id);

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtprice.getText().toString().equals("")&&!edtmessage.getText().toString().equals("")&&!edtprepaid.getText().toString().equals(""))
                {
                    Cake cake=new Cake(pos,image,id,Integer.parseInt(edtprice.getText().toString()),edtmessage.getText().toString(),Integer.parseInt(edtprepaid.getText().toString()));
                    getDialog().dismiss();
                    db.addCake(cake);
                    openmanager();
                    Toast.makeText(getContext(),"Đặt bánh thành công",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(),"Không được để rỗng",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
    public void openmanager()
    {
        ManageFragment manageFragment=new ManageFragment();
        FragmentManager fm=getFragmentManager();
        FragmentTransaction transaction=fm.beginTransaction();
        transaction.replace(R.id.frame,manageFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
