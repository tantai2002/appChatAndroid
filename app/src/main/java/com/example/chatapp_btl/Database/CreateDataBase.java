package com.example.chatapp_btl.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class CreateDataBase extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ChatApp";
    public static String TB_NGUOIDUNG = "NGUOIDUNG";
    public  static String TB_MANGUOIDUNG = "MANGUOIDUNG";
    public static String TB_TENNGUOIDUNG = "TENNGUOIDUNG";
    public static String TB_TAIKHOAN = "TAIKHOAN";
    public static String TB_MATKHAU = "MATKHAU";
    public static String TB_SDIENTHOAI = "SODIENTHOAI";

    public CreateDataBase(Context context){
        super(context,DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tbNguoiDung = "CREATE TABLE " + TB_NGUOIDUNG + " ( " + TB_MANGUOIDUNG + " TEXT PRIMARY KEY, "
                + TB_TENNGUOIDUNG + " TEXT, " + TB_TAIKHOAN + " TEXT, " + TB_MATKHAU + " TEXT, " + TB_SDIENTHOAI + " TEXT)";

        db.execSQL(tbNguoiDung);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public SQLiteDatabase open(){
        return this.getWritableDatabase();
    }
}
