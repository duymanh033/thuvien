package vn.poly.edu.bookmanagement.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "DbBookManager";
    public static final int DB_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String nguoiDung = "create table nguoiDung" +
                "( userName text primary key, " +
                "passWord text, " +
                "phone text, " +
                "hoVaTen text" +
                ")";
        sqLiteDatabase.execSQL(nguoiDung);

        String TheLoai = "create table theLoai" +
                "( " +
                "maTheLoai text primary key, " +
                "tenTheLoai text, " +
                "moTa text, " +
                "viTri integer" +
                ")";
        sqLiteDatabase.execSQL(TheLoai);

        String Sach = "create table Sach" +
                "( " +
                "maSach text primary key, " +
                "maTheLoai text, " +
                "tenSach text, " +
                "tacGia text, " +
                "NXB text, " +
                "giaBia float, " +
                "soLuong integer" +
                ")";
        sqLiteDatabase.execSQL(Sach);

        String hoaDon = "create table hoaDon" +
                "( " +
                "maHoaDon text primary key, " +
                "ngayMua text" +
                ")";
        sqLiteDatabase.execSQL(hoaDon);

        String hoaDonChiTiet = "create table hoaDonChiTiet" +
                "( " +
                "maHoaDonChiTiet integer primary key autoincrement, " +
                "maHoaDon text, " +
                "maSach text, " +
                "soLuongMua integer," +
                "thanhTien text" +
                ")";
        sqLiteDatabase.execSQL(hoaDonChiTiet);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists nguoiDung");
        sqLiteDatabase.execSQL("drop table if exists theLoai");
        sqLiteDatabase.execSQL("drop table if exists Sach");
        sqLiteDatabase.execSQL("drop table if exists hoaDon");
        sqLiteDatabase.execSQL("drop table if exists hoaDonChiTiet");
        onCreate(sqLiteDatabase);
    }
}
