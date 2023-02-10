package nndb.com.parkinglotbctt;

public class License {
    public String MaX;
    public String BSXe;
    public String LoaiXe;
    public String GioVao;

    public License(String maX, String BSXe, String loaiXe, String gioVao) {
        MaX = maX;
        this.BSXe = BSXe;
        LoaiXe = loaiXe;
        GioVao = gioVao;
    }

    public String getMaX() {
        return MaX;
    }

    public void setMaX(String maX) {
        MaX = maX;
    }

    public String getBSXe() {
        return BSXe;
    }

    public void setBSXe(String BSXe) {
        this.BSXe = BSXe;
    }

    public String getLoaiXe() {
        return LoaiXe;
    }

    public void setLoaiXe(String loaiXe) {
        LoaiXe = loaiXe;
    }

    public String getGioVao() {
        return GioVao;
    }

    public void setGioVao(String gioVao) {
        GioVao = gioVao;
    }
}
