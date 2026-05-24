package DAO;

import Connection.DBConnection;
import Model.Kendaraan;
import DAO.Utils.WaktuHelper;

import java.sql.Timestamp;
import java.sql.*;
import java.util.ArrayList;

public class RiwayatDAO {

    Connection conn;

    public RiwayatDAO() {

        conn = DBConnection.Connect();

        System.out.println(conn);
    }


    // =========================================
    // SEARCH
    // =========================================
    public ArrayList<Kendaraan> searchKendaraan(
            String keyword
    ) {

        ArrayList<Kendaraan> list
                = new ArrayList<>();

        String query
                = "SELECT k.id_kendaraan, "
                + "k.plat_nomor, "
                + "k.jenis_kendaraan, "
                + "s.nomor_slot, "
                + "p.waktu_masuk "
                + "FROM parkir p "
                + "JOIN kendaraan k "
                + "ON p.id_kendaraan = k.id_kendaraan "
                + "JOIN slot_parkir s "
                + "ON p.id_slot = s.id_slot "
                + "WHERE k.plat_nomor LIKE ? OR s.nomor_slot LIKE ?";

        try {

            PreparedStatement ps
                    = conn.prepareStatement(query);

            ps.setString(
                    1,
                    "%" + keyword + "%"
            );
            ps.setString(
                    2,
                    "%" + keyword + "%"
            );

            ResultSet rs
                    = ps.executeQuery();

            while (rs.next()) {

                Kendaraan kendaraan
                        = new Kendaraan();

                kendaraan.setIdKendaraan(
                        rs.getInt("id_kendaraan")
                );

                kendaraan.setPlatNomor(
                        rs.getString("plat_nomor")
                );

                kendaraan.setJenisKendaraan(
                        rs.getString("jenis_kendaraan")
                );

                kendaraan.setNomorSlot(
                        rs.getString("nomor_slot")
                );

                Timestamp waktuMasuk
                        = rs.getTimestamp("waktu_masuk");

                kendaraan.setWaktuMasuk(
                        waktuMasuk
                );

                kendaraan.setWaktuMasukFormat(
                        WaktuHelper.formatWaktu(waktuMasuk)
                );

                kendaraan.setLamaParkir(
                        WaktuHelper.hitungLamaParkir(waktuMasuk)
                );

                list.add(kendaraan);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<Kendaraan> getKendaraanParkir() {

        ArrayList<Kendaraan> list
                = new ArrayList<>();

        String query
                = "SELECT k.id_kendaraan, "
                + "k.plat_nomor, "
                + "k.jenis_kendaraan, "
                + "s.nomor_slot, "
                + "p.waktu_masuk, "
                + "p.waktu_keluar, "
                + "p.durasi_jam, "
                + "p.biaya "
                + "FROM parkir p "
                + "JOIN kendaraan k "
                + "ON p.id_kendaraan = k.id_kendaraan "
                + "JOIN slot_parkir s "
                + "ON p.id_slot = s.id_slot "
                + "WHERE p.waktu_keluar IS NOT NULL";

        try {

            Statement st
                    = conn.createStatement();

            ResultSet rs
                    = st.executeQuery(query);

            while (rs.next()) {

                Kendaraan kendaraan = new Kendaraan();

                kendaraan.setIdKendaraan(rs.getInt("id_kendaraan"));
                kendaraan.setPlatNomor(rs.getString("plat_nomor"));
                kendaraan.setJenisKendaraan(rs.getString("jenis_kendaraan"));

                Timestamp waktuMasuk = rs.getTimestamp("waktu_masuk");
                Timestamp waktuKeluar = rs.getTimestamp("waktu_keluar");

                // kalau tidak parkir (null safety)
                int jam = 0;
                if (waktuMasuk != null) {
                    jam = WaktuHelper.hitungJam(waktuMasuk);
                    if (jam < 1) {
                        jam = 1; // minimal 1 jam
                    }
                }

                int tarif = 0;

                if (rs.getString("jenis_kendaraan").equalsIgnoreCase("Motor")) {

                    tarif = 2000;
                    if (jam > 1) {
                        tarif += (jam - 1) * 1000;
                    }

                } else if (rs.getString("jenis_kendaraan").equalsIgnoreCase("Mobil")) {

                    tarif = 3000;
                    if (jam > 1) {
                        tarif += (jam - 1) * 2000;
                    }
                }

                kendaraan.setNomorSlot(rs.getString("nomor_slot"));
                kendaraan.setWaktuMasuk(waktuMasuk);
                kendaraan.setWaktuMasukFormat(WaktuHelper.formatWaktu(waktuMasuk));
                kendaraan.setWaktuKeluar(waktuKeluar);
                kendaraan.setWaktuKeluarFormat(WaktuHelper.formatWaktu(waktuKeluar));
//                kendaraan.setLamaParkir(
//                        WaktuHelper.hitungLamaParkir(waktuMasuk)
//                );
                kendaraan.setLamaParkir(rs.getString("durasi_jam"));

                kendaraan.setTarifParkir(rs.getInt("biaya"));

                list.add(kendaraan);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return list;
    }
}
