package vn.poly.edu.bookmanagement.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import vn.poly.edu.bookmanagement.database.DbHelper;
import vn.poly.edu.bookmanagement.model.Sach;

public class SachDAO {
    DbHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;

    public SachDAO(Context context) {
        dbHelper = new DbHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    public ArrayList<Sach> getAllSach() {
        sqLiteDatabase = dbHelper.getReadableDatabase();
        ArrayList<Sach> listSach = new ArrayList<>();
        String sql = "select * from Sach";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String maSach = cursor.getString(0);
            String maTheLoai = cursor.getString(1);
            String tenSach = cursor.getString(2);
            String tacGia = cursor.getString(3);
            String NXB = cursor.getString(4);
            double giaBia = cursor.getDouble(5);
            int soLuong = cursor.getInt(6);
            Sach sach = new Sach(maSach, maTheLoai, tenSach, tacGia, NXB, giaBia, soLuong);
            listSach.add(sach);
            cursor.moveToNext();
        }
        cursor.close();
        sqLiteDatabase.close();
        return listSach;
    }

    public void insertSach(Sach sach) {
        sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("maSach", sach.getMaSach());
        values.put("maTheLoai", sach.getMaTheLoai());
        values.put("tenSach", sach.getTenSach());
        values.put("tacGia", sach.getTacGia());
        values.put("NXB", sach.getNXB());
        values.put("giaBia", sach.getGiaBia());
        values.put("soLuong", sach.getSoLuong());
        sqLiteDatabase.insert("Sach", null, values);
    }

    public void deleteSach(String maSach) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        sqLiteDatabase.delete("Sach", "maSach=?", new String[]{maSach});
    }

    public void updateSach(Sach sach) {
        sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("maSach", sach.getMaSach());
        values.put("maTheLoai", sach.getMaTheLoai());
        values.put("tenSach", sach.getTenSach());
        values.put("tacGia", sach.getTacGia());
        values.put("NXB", sach.getNXB());
        values.put("giaBia", sach.getGiaBia());
        values.put("soLuong", sach.getSoLuong());
        sqLiteDatabase.update("Sach", values, "maSach=?", new String[]{sach.getMaSach()});
    }
}
