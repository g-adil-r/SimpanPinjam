package com.example.myapplication.Model;

import java.util.List;

public class Anggota {
    String anggotaId;
    String nama;
    String alamatBidak;
    List<Transaksi> transaksiList;
    public Anggota() {}

    public Anggota(String anggotaId, String nama, String alamatBidak) {
        this.anggotaId = anggotaId;
        this.nama = nama;
        this.alamatBidak = alamatBidak;
    }

    public String getAnggotaId() {
        return anggotaId;
    }

    public void setAnggotaId(String anggotaId) {
        this.anggotaId = anggotaId;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamatBidak() {
        return alamatBidak;
    }

    public void setAlamatBidak(String alamatBidak) {
        this.alamatBidak = alamatBidak;
    }

    public List<Transaksi> getTransaksiList() {
        return transaksiList;
    }

    public void setTransaksiList(List<Transaksi> transaksiList) {
        this.transaksiList = transaksiList;
    }
}
