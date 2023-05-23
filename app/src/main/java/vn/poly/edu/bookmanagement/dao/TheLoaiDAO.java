package vn.poly.edu.bookmanagement.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import vn.poly.edu.bookmanagement.database.DbHelper;
import vn.poly.edu.bookmanagement.model.TheLoai;

public class TheLoaiDAO {
    DbHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;

    public TheLoaiDAO(Context context) {
        dbHelper = new DbHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    public ArrayList<TheLoai> getAllTheLoai() {
        sqLiteDatabase = dbHelper.getReadableDatabase();
        ArrayList<TheLoai> listTheLoai = new ArrayList<>();
        String sql = "select * from theLoai";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String maTheLoai = cursor.getString(0);
            String tenTheLoai = cursor.getString(1);
            String moTa = cursor.getString(2);
            int viTri = cursor.getInt(3);
            TheLoai theLoai = new TheLoai(maTheLoai, tenTheLoai, moTa, viTri);
            listTheLoai.add(theLoai);
            cursor.moveToNext();
        }
        cursor.close();
        sqLiteDatabase.close();
        return listTheLoai;
    }

    public void insertTheLoai(TheLoai theLoai) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maTheLoai", theLoai.getMaTheLoai());
        contentValues.put("tenTheLoai", theLoai.getTenTheLoai());
        contentValues.put("moTa", theLoai.getMoTa());
        contentValues.put("viTri", theLoai.getViTri());

        sqLiteDatabase.insert("theLoai", null, contentValues);
    }

    public void deleteTheLoai(String maTheLoai) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        sqLiteDatabase.delete("theLoai", "maTheLoai=?", new String[]{maTheLoai});
    }

    public void updateTheLoai(TheLoai theLoai) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maTheLoai", theLoai.getMaTheLoai());
        contentValues.put("tenTheLoai", theLoai.getTenTheLoai());
        contentValues.put("moTa", theLoai.getMoTa());
        contentValues.put("viTri", theLoai.getViTri());

        sqLiteDatabase.update("theLoai", contentValues, "maTheLoai=?", new String[]{theLoai.getMaTheLoai()});
    }
}
