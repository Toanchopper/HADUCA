package com.example.haduca;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class UpdateDialog extends DialogFragment {

    static final String POS="POS";
    static final String PICTURE="PICTURE";
    static final String ID="ID";
    static final String PRICE="PRICE";
    static final String MESS="MESS";
    static final String PRE="PRE";
    EditText edt1,edt2,edt3;
    Button btnaccept,btncancel;
    DBManager db;
    public static UpdateDialog newInstance(int pos, Bitmap picture,String id,int price,String mess,int pre) {

        Bundle args = new Bundle();
        args.putInt(POS,pos);
        args.putParcelable(PICTURE,picture);
        args.putString(ID,id);
        args.putInt(PRICE,price);
        args.putString(MESS,mess);
        args.putInt(PRE,pre);
        UpdateDialog fragment = new UpdateDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_cake,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edt1=(EditText) view.findViewById(R.id.edt_reprice);
        edt2=(EditText) view.findViewById(R.id.edt_remess);
        edt3=(EditText) view.findViewById(R.id.edt_repaid);
        btnaccept=(Button) view.findViewById(R.id.btn_accept);
        btncancel=(Button) view.findViewById(R.id.btn_cancel);
        final int pos= getArguments().getInt(POS);
        final Bitmap pic=getArguments().getParcelable(PICTURE);
        final String id=getArguments().getString(ID);
        final String mess=getArguments().getString(MESS);
        final int price=getArguments().getInt(PRICE);
        final int pre=getArguments().getInt(PRE);

        edt1.setText(String.valueOf(price));
        edt2.setText(mess);
        edt3.setText(String.valueOf(pre));
        db=new DBManager(getContext());
        btnaccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cake cake =new Cake(pos,pic,id,Integer.parseInt(edt1.getText().toString()),edt2.getText().toString(),Integer.parseInt(edt3.getText().toString()));
                 update(cake);
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
    }

    public void update(Cake cake)
    {
        if(!edt1.getText().equals("")&&!edt2.getText().equals("")&&!edt3.getText().equals(""))
        {
            db.update(cake);
            db.close();
            Toast.makeText(getContext(),"Đã cập nhật thành công",Toast.LENGTH_SHORT).show();
            getDialog().dismiss();
        }
        else{
            Toast.makeText(getContext(),"Bạn không được để trống",Toast.LENGTH_SHORT).show();
        }
    }
}
