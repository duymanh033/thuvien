package vn.poly.edu.bookmanagement.model;

public class HoaDon {
    private String maHoadon;
    private String ngayMua;

    public HoaDon(String maHoadon, String ngayMua) {
        this.maHoadon = maHoadon;
        this.ngayMua = ngayMua;
    }

    public HoaDon() {
    }

    public String getMaHoadon() {
        return maHoadon;
    }

    public void setMaHoadon(String maHoadon) {
        this.maHoadon = maHoadon;
    }

    public String getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(String ngayMua) {
        this.ngayMua = ngayMua;
    }
}
