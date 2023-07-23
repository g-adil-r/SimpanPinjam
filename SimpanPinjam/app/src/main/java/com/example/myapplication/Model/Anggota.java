package com.example.myapplication.Model;

public class Anggota {
    String id;
    String nama;
    String alamatBidak;

    public Anggota(String id, String nama, String alamatBidak) {
        this.id = id;
        this.nama = nama;
        this.alamatBidak = alamatBidak;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
