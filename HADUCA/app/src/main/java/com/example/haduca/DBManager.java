package com.example.haduca;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {
    public static String DATABASE="MYDATA";
    public static int VERSION=1;
    public static String ID="ID";
    public static String POS="POS";
    public static String PRICE="PRICE";
    public static String IMAGE="IMAGE";
    public static String MESSAGE="MESSAGE";
    public static String PREPAID="PREPAID";

    String create="CREATE TABLE "+DATABASE+" ("
            +POS+" TEXT, "
            +IMAGE+" BLOB, "
            +ID+" TEXT, "
            +PRICE+" INTEGER,"
            +MESSAGE+" TEXT, "
            +PREPAID+" INTEGER)";

    public DBManager(@Nullable Context context) {
        super(context, DATABASE,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void doquery(String sql)
    {
        SQLiteDatabase database=getWritableDatabase();
        database.execSQL(sql);
    }

    public void addCake(Cake cake)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put(POS,String.valueOf(cake.getPos()));
        content.put(IMAGE,encodeImage(cake.getPicture()));
        content.put(ID,cake.getId());
        content.put(PRICE,cake.getPrice());
        content.put(MESSAGE,cake.getMessage());
        content.put(PREPAID,cake.getPrepaid());

        db.insert(DATABASE,null,content);
        db.close();
    }

    public List<Cake> getCake()
    {
        SQLiteDatabase db=getWritableDatabase();
        String getall="SELECT * FROM "+DATABASE;
        List<Cake> list=new ArrayList<>();
        Cursor cursor=db.rawQuery(getall,null);
        if(cursor.moveToFirst())
        {
            do{
                int pos=Integer.parseInt(cursor.getString(0));
                byte[] image=cursor.getBlob(1);
                String id=cursor.getString(2);
                int price=Integer.parseInt(cursor.getString(3));
                String mess=cursor.getString(4);
                int prepaid=Integer.parseInt(cursor.getString(5));
                Cake cake=new Cake(pos,decodeImage(image),id,price,mess,prepaid);
                list.add(cake);
            }while(cursor.moveToNext());
        }

        return list;
    }

    public void update(Cake cake)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put(IMAGE,encodeImage(cake.getPicture()));
        content.put(ID,cake.getId());
        content.put(PRICE,cake.getPrice());
        content.put(MESSAGE,cake.getMessage());
        content.put(PREPAID,cake.getPrepaid());

        db.update(DATABASE,content,POS+"="+cake.getPos(),null);
        db.close();
    }
    public void deleteAll()
    {
        SQLiteDatabase db=getWritableDatabase();
        String delete="delete from "+DATABASE;
        db.execSQL(delete);
        db.close();
    }

    public Bitmap decodeImage(byte[] image)
    {
        Bitmap pic= BitmapFactory.decodeByteArray(image,0,image.length);
        return pic;
    }
    public byte[] encodeImage(Bitmap image)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
         image.compress(Bitmap.CompressFormat.PNG,0, stream);
        byte[] pic= stream.toByteArray();

        return pic;
    }

}
