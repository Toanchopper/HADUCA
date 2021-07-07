package com.example.haduca;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class ManageFragment extends Fragment{ //implements CakeDialog.sendData{

    ListView listcake;
    Button revenue,delall,update;
    List<Cake> cakes;
    static final String CAKE="CAKE";
    OrderedAdapter adapter;
    DBManager db;
    public ManageFragment() {
        // Required empty public constructor
    }

    public static ManageFragment newInstance(Cake cake) {

        Bundle args = new Bundle();

        ManageFragment fragment = new ManageFragment();
        args.putParcelable(CAKE, (Parcelable) cake);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listcake=(ListView) view.findViewById(R.id.listcake);
        revenue=(Button) view.findViewById(R.id.revenue);
        delall=(Button) view.findViewById(R.id.delall);
        update=(Button) view.findViewById(R.id.update);
        cakes=new ArrayList<>();
        db=new DBManager(getContext());
        cakes=db.getCake();
        setAdapter();

        revenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRevenue();
            }
        });
        delall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delAll();
                cakes.clear();
                cakes.addAll(db.getCake());
                setAdapter();
            }
        });
        listcake.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                edit(cakes.get(position));
                return true;
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cakes.clear();
                cakes.addAll(db.getCake());
                setAdapter();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

   public void setAdapter()
   {
       if(adapter==null)
       {
           adapter=new OrderedAdapter(getContext(),R.layout.cake_ordered,cakes);
           listcake.setAdapter(adapter);
           listcake.setSelection(adapter.getCount()-1);
       }else{
           adapter.notifyDataSetChanged();
           listcake.setSelection(adapter.getCount()-1);
       }
   }
     public void showRevenue()
     {
         int sum=0;
         int n =listcake.getCount();
         for(int i=0;i<n;i++)
         {
             sum+=cakes.get(i).getPrice();
         }
         AlertDialog.Builder dialog=new AlertDialog.Builder(getContext()).setTitle("Thống kê doanh thu").setMessage("Tổng doanh thu: "+String.valueOf(sum)).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                 dialog.dismiss();
             }
         });
         dialog.show();
     }

     public void delAll()
     {
         db.deleteAll();
     }
     public void edit(Cake cake)
     {
         FragmentManager fm=getFragmentManager();
         UpdateDialog updateDialog=UpdateDialog.newInstance(cake.getPos(),cake.getPicture(),cake.getId(),cake.getPrice(),cake.getMessage(),cake.getPrepaid());
         updateDialog.show(fm,null);
     }
}
