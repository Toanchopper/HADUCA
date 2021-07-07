package com.example.haduca;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment{

    GridView gridcake;
    Spinner options;
    Button openmanager;
    List<String> option;
    List<Bitmap> listimg;
    List<String> listid;
    File[] files;
    static final File path=new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)));
    static final String[] ext={"png","jpg","jpeg"};
    FilenameFilter filterAll=new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            for(String x : ext){
                if(name.endsWith("."+x))
                    return true;
            }
            return false;
        }
    };
    FilenameFilter filterMT=new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            for(String x : ext){
                if(name.endsWith("."+x)&&name.charAt(0)=='M'&&name.charAt(1)=='T')
                    return true;
            }
            return false;
        }
    };
    FilenameFilter filterMA=new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            for(String x : ext){
                if(name.endsWith("."+x)&&name.charAt(0)=='M'&&name.charAt(1)=='A')
                    return true;
            }
            return false;
        }
    };
    FilenameFilter filterFA=new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            for(String x : ext){
                if(name.endsWith("."+x)&&name.charAt(0)=='F'&&name.charAt(1)=='A')
                    return true;
            }
            return false;
        }
    };
    FilenameFilter filterBO=new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            for(String x : ext){
                if(name.endsWith("."+x)&&name.charAt(0)=='B'&&name.charAt(1)=='O')
                    return true;
            }
            return false;
        }
    };
    FilenameFilter filterGL=new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            for(String x : ext){
                if(name.endsWith("."+x)&&name.charAt(0)=='G'&&name.charAt(1)=='L')
                    return true;
            }
            return false;
        }
    };
    FilenameFilter filterIA=new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
           for(String x : ext)
           {
               if(name.endsWith("."+x)&&name.charAt(0)=='I'&&name.charAt(1)=='A')
               {
                   return true;
               }
           }
            return false;
        }
    };
    FilenameFilter filterBC=new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            for(String x : ext){
                if(name.endsWith("."+x)&&name.charAt(0)=='B'&&name.charAt(1)=='C')
                    return true;
            }
            return false;
        }
    };
    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_order, container, false);
        gridcake=(GridView) view.findViewById(R.id.cakegrid);
        options=(Spinner) view.findViewById(R.id.optionspinner);
        openmanager=(Button) view.findViewById(R.id.openmanager);

        option=new ArrayList<>();
        option.add("Người lớn - Nam");
        option.add("Người lớn - Nữ");
        option.add("Trẻ con - Nam");
        option.add("Trẻ con - Nữ");
        option.add("Mừng thọ");
        option.add("Bánh cưới");
        option.add("In ảnh");
        ArrayAdapter adapter=new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,option);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        options.setAdapter(adapter);
        options.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FilenameFilter filter=changeItem();
                files=path.listFiles(filter);
                try {
                    setGrid();
                }catch (NullPointerException e)
                {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        openmanager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openmanager();
            }
        });

     return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    private void checkAndRequestPermissions() {
        String[] permissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions((Activity) getContext(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
        }
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
    public FilenameFilter changeItem()
    {
        FilenameFilter filter=null;
        if(options.getSelectedItem().equals("Mừng thọ"))
        {
            filter=filterMT;
        }
         else if(options.getSelectedItem().equals("Người lớn - Nam"))
        {
            filter=filterMA;
        }
        else if(options.getSelectedItem().equals("Người lớn - Nữ"))
        {
            filter=filterFA;
        }
        else if(options.getSelectedItem().equals("Trẻ con - Nam"))
        {
            filter=filterBO;
        }
        else if(options.getSelectedItem().equals("Trẻ con - Nữ"))
        {
            filter=filterGL;
        }
        else if(options.getSelectedItem().equals("Bánh cưới"))
        {
            filter=filterBC;
        }
        else if(options.getSelectedItem().equals("In ảnh"))
        {
            filter=filterIA;
        }
        else
            filter=filterAll;
        return filter;
    }
    public void setGrid()
    {
        listimg=new ArrayList<>();
        listid=new ArrayList<>();
        for(File f : files)
        {
            listimg.add(BitmapFactory.decodeFile(String.valueOf(f)));
        }
        for(File f : files)
        {
            if(f.getName().endsWith(".png")||f.getName().endsWith(".jpg"))
            {
                listid.add(f.getName().substring(0,f.getName().length()-4));
            }
            else{
                listid.add(f.getName().substring(0,f.getName().length()-5));
            }
        }
        GridAdapter gridAdapter=new GridAdapter(getContext(),R.layout.item_cake,listimg);
        gridcake.setAdapter(gridAdapter);
        gridcake.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fm=getFragmentManager();
//                CakeDialog cakeDialog=CakeDialog.newInstance(listimg.get(position),listid.get(position));
//                cakeDialog.show(fm,null);
                Random rd=new Random();
                int pos=rd.nextInt();
                ZoomDialog zoomDialog=ZoomDialog.newInstance(pos,listimg.get(position),listid.get(position));
                zoomDialog.show(fm,null);
            }
        });
    }

}
