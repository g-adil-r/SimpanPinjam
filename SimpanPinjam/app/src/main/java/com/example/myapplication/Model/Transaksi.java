package com.example.myapplication.Model;

public class Transaksi {
    String transaksiId;
    long tanggal;
    int setoran;

    public Transaksi() {}

    public Transaksi(String transaksiId, long tanggal, int setoran) {
        this.transaksiId = transaksiId;
        this.tanggal = tanggal;
        this.setoran = setoran;
    }

    public String getTransaksiId() {
        return transaksiId;
    }

    public void setTransaksiId(String transaksiId) {
        this.transaksiId = transaksiId;
    }

    public long getTanggal() {
        return tanggal;
    }

    public void setTanggal(long tanggal) {
        this.tanggal = tanggal;
    }

    public int getSetoran() {
        return setoran;
    }

    public void setSetoran(int setoran) {
        this.setoran = setoran;
    }
}
