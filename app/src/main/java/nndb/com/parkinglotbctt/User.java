package nndb.com.parkinglotbctt;

public class User {
    public String HoTen;
    public String Email;
    public String DiaChi;
    public String NgaySinh;
    public String SoDT;
    public String GioiTinh;

    public User(String hoTen, String email, String diaChi, String ngaySinh, String soDT, String gioiTinh) {
        HoTen = hoTen;
        Email = email;
        DiaChi = diaChi;
        NgaySinh = ngaySinh;
        SoDT = soDT;
        GioiTinh = gioiTinh;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        NgaySinh = ngaySinh;
    }

    public String getSoDT() {
        return SoDT;
    }

    public void setSoDT(String soDT) {
        SoDT = soDT;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        GioiTinh = gioiTinh;
    }
}
