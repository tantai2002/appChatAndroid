package com.example.chatapp_btl.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

import com.example.chatapp_btl.DTO.NguoiDungDTO;
import com.example.chatapp_btl.Database.CreateDataBase;

import java.security.AccessControlContext;

public class NguoiDungDAO {
    SQLiteDatabase database;

//    public NguoiDungDAO(AccessControlContext context){
//        CreateDataBase createDataBase = new CreateDataBase(context);
//        database = createDataBase.open();
//    }

    public NguoiDungDAO(Context context) {
        CreateDataBase creadb = new CreateDataBase(context);
        database = creadb.getWritableDatabase();
    }


    public long ThemNguoiDung(NguoiDungDTO nguoiDungDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDataBase.TB_MANGUOIDUNG,nguoiDungDTO.getMaND());
        contentValues.put(CreateDataBase.TB_TENNGUOIDUNG,nguoiDungDTO.getTenND());
        contentValues.put(CreateDataBase.TB_TAIKHOAN, nguoiDungDTO.getTaiKhoan());
        contentValues.put(CreateDataBase.TB_MATKHAU, nguoiDungDTO.getMatKhau());
        contentValues.put(CreateDataBase.TB_SDIENTHOAI, nguoiDungDTO.getSoDienThoai());
        long kiemTra = database.insert(CreateDataBase.TB_NGUOIDUNG, null,contentValues);
        return kiemTra;
    }

    public boolean KiemTraNguoiDung(){
        String truyVan = "SELECT * FROM " + CreateDataBase.TB_NGUOIDUNG;
        Cursor cursor = database.rawQuery(truyVan, null);
        if(cursor.getCount() != 0){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean kiemTraDangNhap(String taiKhoan, String matKhau){
        String truyVan = "SELECT * FROM " + CreateDataBase.TB_NGUOIDUNG + " WHERE " + CreateDataBase.TB_TAIKHOAN
                + " = " + taiKhoan + "' AND " +  CreateDataBase.TB_MATKHAU + " = " + matKhau + " '";
        Cursor cursor = database.rawQuery(truyVan, null);
        if(cursor.getCount() != 0) {
            return true;
        }
        else{
            return false;
        }
    }
}
