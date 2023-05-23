package vn.poly.edu.bookmanagement.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import vn.poly.edu.bookmanagement.database.DbHelper;
import vn.poly.edu.bookmanagement.model.HoaDon;

public class HoaDonDAO {
    DbHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;

    public HoaDonDAO (Context context){
        dbHelper = new DbHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    public ArrayList<HoaDon> getAllHoaDon() {
        sqLiteDatabase = dbHelper.getReadableDatabase();
        ArrayList<HoaDon> listHoaDon = new ArrayList<>();
        String sql = "select * from hoaDon";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String maHoaDon = cursor.getString(0);
            String ngayMua = cursor.getString(1);
            HoaDon hoaDon = new HoaDon(maHoaDon, ngayMua);
            listHoaDon.add(hoaDon);
            cursor.moveToNext();
        }
        cursor.close();
        sqLiteDatabase.close();
        return listHoaDon;
    }

    public void insertHoaDon(HoaDon hoaDon) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("maHoadon", hoaDon.getMaHoadon());
        values.put("ngayMua", hoaDon.getNgayMua());
        sqLiteDatabase.insert("hoaDon", null, values);
    }

    public void deleteHoaDon(String maHoaDon) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        sqLiteDatabase.delete("hoaDon", "maHoaDon=?", new String[]{maHoaDon});
    }

}
