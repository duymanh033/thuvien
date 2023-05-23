package vn.poly.edu.bookmanagement.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import vn.poly.edu.bookmanagement.database.DbHelper;
import vn.poly.edu.bookmanagement.model.NguoiDung;

public class NguoiDungDAO {
    SQLiteDatabase sqLiteDatabase;
    DbHelper dbHelper;

    public static String TABLE_NAME = "nguoiDung";

    public NguoiDungDAO(Context context){
        dbHelper = new DbHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    public ArrayList<NguoiDung> getAllNguoiDung(){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        ArrayList<NguoiDung> list = new ArrayList<>();
        String sql = "select * from " + TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            NguoiDung nguoiDung = new NguoiDung();
            nguoiDung.setUserName(cursor.getString(0));
            nguoiDung.setPassWord(cursor.getString(1));
            nguoiDung.setPhone(cursor.getString(2));
            nguoiDung.setHoVaTen(cursor.getString(3));
            list.add(nguoiDung);
            cursor.moveToNext();
        }
        cursor.close();
        sqLiteDatabase.close();
        return list;
    }

    public int insertNguoiDung(NguoiDung nguoiDung){
        sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userName", nguoiDung.getUserName());
        contentValues.put("passWord", nguoiDung.getPassWord());
        contentValues.put("phone", nguoiDung.getPhone());
        contentValues.put("hoVaTen", nguoiDung.getHoVaTen());

        try {
            if (sqLiteDatabase.insert(TABLE_NAME, null, contentValues) == -1) {
                return -1;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 1;
    }

    public int deleteNguoiDung(String userName) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        try {
            if (sqLiteDatabase.delete(TABLE_NAME, "userName=?", new String[]{userName}) == -1) {
                return -1;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 1;
    }

    public int updateThongTinNguoiDung(NguoiDung nguoiDung) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("phone", nguoiDung.getPhone());
        values.put("hoVaTen", nguoiDung.getHoVaTen());

        try {
            if (sqLiteDatabase.update(TABLE_NAME, values, "userName=?", new String[]{nguoiDung.getUserName()}) == -1) {
                return -1;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 1;
    }

    public int updateMatKhauNguoiDung(NguoiDung nguoiDung) {
        sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("passWord", nguoiDung.getPassWord());
        int result = sqLiteDatabase.update(TABLE_NAME, values, "userName=?", new String[]{nguoiDung.getUserName()});

        try {
            if (result == -1) {
                return -1;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 1;
    }
}
