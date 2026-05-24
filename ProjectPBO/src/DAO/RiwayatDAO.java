package DAO;

import Connection.DBConnection;
import Model.Kendaraan;
import DAO.Utils.WaktuHelper;
import Model.Mobil;
import Model.Motor;

import java.sql.Timestamp;
import java.sql.*;
import java.util.ArrayList;

public class RiwayatDAO {

    Connection conn;

    public RiwayatDAO() {

        conn = DBConnection.Connect();

        System.out.println(conn);
    }

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
                + "p.waktu_masuk, "
                + "p.waktu_keluar, "
                + "p.durasi_jam, "
                + "p.biaya "
                + "FROM parkir p "
                + "JOIN kendaraan k "
                + "ON p.id_kendaraan = k.id_kendaraan "
                + "JOIN slot_parkir s "
                + "ON p.id_slot = s.id_slot "
                + "WHERE (k.plat_nomor LIKE ? OR s.nomor_slot LIKE ?)"
                + "AND p.waktu_keluar IS NOT NULL";

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
                String jenis
                        = rs.getString("jenis_kendaraan");

                Kendaraan kendaraan;

                if (jenis.equalsIgnoreCase("Motor")) {

                    kendaraan = new Motor();

                } else {

                    kendaraan = new Mobil();
                }

                kendaraan.setIdKendaraan(rs.getInt("id_kendaraan"));
                kendaraan.setPlatNomor(rs.getString("plat_nomor"));
                kendaraan.setJenisKendaraan(rs.getString("jenis_kendaraan"));

                Timestamp waktuMasuk = rs.getTimestamp("waktu_masuk");
                Timestamp waktuKeluar = rs.getTimestamp("waktu_keluar");

                int jam = 0;
                if (waktuMasuk != null) {
                    jam = WaktuHelper.hitungJam(waktuMasuk);
                    if (jam < 1) {
                        jam = 1;
                    }
                }

                kendaraan.setNomorSlot(rs.getString("nomor_slot"));
                kendaraan.setWaktuMasuk(waktuMasuk);
                kendaraan.setWaktuMasukFormat(WaktuHelper.formatWaktu(waktuMasuk));
                kendaraan.setWaktuKeluar(waktuKeluar);
                kendaraan.setWaktuKeluarFormat(WaktuHelper.formatWaktu(waktuKeluar));

                kendaraan.setLamaParkir(rs.getString("durasi_jam"));

                kendaraan.setTarifParkir(rs.getInt("biaya"));

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
                String jenis
                        = rs.getString("jenis_kendaraan");

                Kendaraan kendaraan;

                if (jenis.equalsIgnoreCase("Motor")) {

                    kendaraan = new Motor();

                } else {

                    kendaraan = new Mobil();
                }

                kendaraan.setIdKendaraan(rs.getInt("id_kendaraan"));
                kendaraan.setPlatNomor(rs.getString("plat_nomor"));
                kendaraan.setJenisKendaraan(rs.getString("jenis_kendaraan"));

                Timestamp waktuMasuk = rs.getTimestamp("waktu_masuk");
                Timestamp waktuKeluar = rs.getTimestamp("waktu_keluar");

                int jam = 0;
                if (waktuMasuk != null) {
                    jam = WaktuHelper.hitungJam(waktuMasuk);
                    if (jam < 1) {
                        jam = 1;
                    }
                }

                kendaraan.setNomorSlot(rs.getString("nomor_slot"));
                kendaraan.setWaktuMasuk(waktuMasuk);
                kendaraan.setWaktuMasukFormat(WaktuHelper.formatWaktu(waktuMasuk));
                kendaraan.setWaktuKeluar(waktuKeluar);
                kendaraan.setWaktuKeluarFormat(WaktuHelper.formatWaktu(waktuKeluar));

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
