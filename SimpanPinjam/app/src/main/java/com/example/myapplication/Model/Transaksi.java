package com.example.myapplication.Model;

public class Transaksi {
    String transaksiId;
    String anggotaId;
    String tanggal;
    int setoran;

    public Transaksi() {}

    public Transaksi(String transaksiId, String anggotaId, String tanggal, int setoran) {
        this.transaksiId = transaksiId;
        this.anggotaId = anggotaId;
        this.tanggal = tanggal;
        this.setoran = setoran;
    }

    public String getTransaksiId() {
        return transaksiId;
    }

    public void setTransaksiId(String transaksiId) {
        this.transaksiId = transaksiId;
    }

    public String getAnggotaId() {
        return anggotaId;
    }

    public void setAnggotaId(String anggotaId) {
        this.anggotaId = anggotaId;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public int getSetoran() {
        return setoran;
    }

    public void setSetoran(int setoran) {
        this.setoran = setoran;
    }
}
