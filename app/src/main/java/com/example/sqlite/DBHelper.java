package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "myDB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // autoincrement - the recID will increase even the data is deleted
        db.execSQL("CREATE TABLE table1(recID integer primary key autoincrement, recContent text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS table1");
        onCreate(db);
    }

    public Boolean insertData(String content){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues(1);
        values.put("recContent",content);
        db.insert("table1",null,values);
        return true;
    }

    public Boolean updateData(int recID, String content){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues(1);
        values.put("recContent",content);
        db.update("table1",values,"recID=?",new String[]{String.valueOf(recID)});
        return true;
    }

    public Boolean deleteData(int recID){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("table1","recID=?",new String[]{String.valueOf(recID)});
        return true;
    }

    public Cursor readData(){
        //String query="SELECT * FROM table1"; // without row.xml
        String query="SELECT recID AS _id, recID, recContent FROM table1";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=null;
        if(db!=null){
            cursor=db.rawQuery(query, null);
        }
        return cursor;
    }
}
