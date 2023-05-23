package vn.poly.edu.bookmanagement.model;

public class NguoiDung {
    private String userName;
    private String passWord;
    private String phone;
    private String hoVaTen;

    public NguoiDung(String userName, String passWord, String phone, String hoVaTen) {
        this.userName = userName;
        this.passWord = passWord;
        this.phone = phone;
        this.hoVaTen = hoVaTen;
    }

    public NguoiDung(String userName, String phone, String hoVaTen) {
        this.userName = userName;
        this.phone = phone;
        this.hoVaTen = hoVaTen;
    }

    public NguoiDung(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    public NguoiDung() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHoVaTen() {
        return hoVaTen;
    }

    public void setHoVaTen(String hoVaTen) {
        this.hoVaTen = hoVaTen;
    }
}
