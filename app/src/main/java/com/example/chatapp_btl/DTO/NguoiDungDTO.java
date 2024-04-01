package com.example.chatapp_btl.DTO;

public class NguoiDungDTO {
    private String maND; //
    private String tenND; //
    private String taiKhoan; // Tài khoản
    private String matKhau; // Mật khẩu
    private String soDienThoai; // Số điện thoại

    // Constructor mặc định
    public NguoiDungDTO() {
    }

    // Constructor với tham số
    public NguoiDungDTO(String maND, String tenND, String taiKhoan, String matKhau, String soDienThoai) {
        this.maND = maND;
        this.tenND = tenND;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.soDienThoai = soDienThoai;
    }

    // Các phương thức getter và setter để truy cập và thiết lập giá trị cho các trường dữ liệu
    public String getMaND() {
        return maND;
    }

    public void setMaND(String maND) {
        this.maND = maND;
    }

    public String getTenND() {
        return tenND;
    }

    public void setTenND(String tenND) {
        this.tenND = tenND;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    // Phương thức toString để trả về thông tin của đối tượng dưới dạng chuỗi
    @Override
    public String toString() {
        return "NguoiDungDTO{" +
                "maND='" + maND + '\'' +
                ", tenND='" + tenND + '\'' +
                ", taiKhoan='" + taiKhoan + '\'' +
                ", matKhau='" + matKhau + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                '}';
    }
}
