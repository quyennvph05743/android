package edu.fpt.lenovo.shoponline.model;

public class Loaisp {
    public int Id;
    public String Tenloaisp;
    public String Hinhloaisanpham;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTenloaisp() {
        return Tenloaisp;
    }

    public void setTenloaisp(String tenloaisp) {
        Tenloaisp = tenloaisp;
    }

    public String getHinhanhloaisp() {
        return Hinhloaisanpham;
    }

    public void setHinhanhloaisp(String hinhloaisanpham) {
        Hinhloaisanpham = hinhloaisanpham;
    }

    public Loaisp(int id, String tenloaisp, String hinhloaisanpham) {

        Id = id;
        Tenloaisp = tenloaisp;
        Hinhloaisanpham = hinhloaisanpham;
    }
}
