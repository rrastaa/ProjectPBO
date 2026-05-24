package Model;

import java.sql.Timestamp;

public abstract class Kendaraan {

    private int idKendaraan;

    private String platNomor;

    private String jenisKendaraan;

    private String pemilikKendaraan;

    private String nomorSlot;

    private String statusKendaraan;

    private Timestamp waktuMasuk;
    private Timestamp waktuKeluar;
    private String waktuMasukFormat;
    private String waktuKeluarFormat;
    private String lamaParkir;

    private int tarifParkir;

    public int getIdKendaraan() {
        return idKendaraan;
    }

    public void setIdKendaraan(int idKendaraan) {
        this.idKendaraan = idKendaraan;
    }

    public String getPlatNomor() {
        return platNomor;
    }

    public void setPlatNomor(String platNomor) {
        this.platNomor = platNomor;
    }

    public String getJenisKendaraan() {
        return jenisKendaraan;
    }

    public void setJenisKendaraan(String jenisKendaraan) {
        this.jenisKendaraan = jenisKendaraan;
    }

    public String getPemilikKendaraan() {
        return pemilikKendaraan;
    }

    public void setPemilikKendaraan(String pemilikKendaraan) {
        this.pemilikKendaraan = pemilikKendaraan;
    }

    public String getNomorSlot() {
        return nomorSlot;
    }

    public void setNomorSlot(String nomorSlot) {
        this.nomorSlot = nomorSlot;
    }

    public String getStatusKendaraan() {
        return statusKendaraan;
    }

    public void setStatusKendaraan(String statusKendaraan) {
        this.statusKendaraan = statusKendaraan;
    }

    public Timestamp getWaktuMasuk() {
        return waktuMasuk;
    }

    public void setWaktuMasuk(Timestamp waktuMasuk) {
        this.waktuMasuk = waktuMasuk;
    }

    public String getWaktuMasukFormat() {
        return waktuMasukFormat;
    }

    public void setWaktuMasukFormat(String waktuMasukFormat) {
        this.waktuMasukFormat = waktuMasukFormat;
    }
    public Timestamp getWaktuKeluar() {
        return waktuKeluar;
    }

    public void setWaktuKeluar(Timestamp waktuKeluar) {
        this.waktuKeluar = waktuKeluar;
    }
    public String getWaktuKeluarFormat() {
        return waktuKeluarFormat;
    }

    public void setWaktuKeluarFormat(String waktuKeluarFormat) {
        this.waktuKeluarFormat = waktuKeluarFormat;
    }

    public String getLamaParkir() {
        return lamaParkir;
    }

    public void setLamaParkir(String lamaParkir) {
        this.lamaParkir = lamaParkir;
    }
    
    public abstract int hitungTarif(int jam);

    public int getTarifParkir() {
        return tarifParkir;
    }

    public void setTarifParkir(int tarifParkir) {
        this.tarifParkir = tarifParkir;
    }
}
