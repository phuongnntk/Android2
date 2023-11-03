package com.shop.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context){
        super(context,"Food",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Tạo bảng Nguoi dung
        String qNguoiDung = "CREATE TABLE NGUOIDUNG(tendangnhap TEXT PRIMARY KEY, matkhau TEXT, hoten TEXT )";
        db.execSQL(qNguoiDung);
        // Tạo bảng San pham
        String qSanPham = "CREATE TABLE SANPHAM(masp INTEGER PRIMARY KEY AUTOINCREMENT, tensp TEXT,giaban INTEGER, soluong INTEGER)";
        db.execSQL(qSanPham);

        String dNguoiDung = "INSERT INTO NGUOIDUNG VALUES('phuongntk2392@gmail.com', 123, 'Phượng Nguyễn'),('TrucPT', 123, 'Trúc Phạm')";
        db.execSQL(dNguoiDung);

        String dSanPham = "INSERT INTO SANPHAM VALUES(1, 'Bánh Quy', 5000, 30)," +
                " (2, 'Socola', 7000, 40), " +
                "(3, 'Kẹo', 2000, 80), " +
                "(4, 'Bánh dẻo', 7000, 30)";
        db.execSQL(dSanPham);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS NGUOIDUNG");
            db.execSQL("DROP TABLE IF EXISTS SANPHAM");
            onCreate(db);
        }
    }
}
