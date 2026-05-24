package DAO;

import Connection.DBConnection;
import Model.Kendaraan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.ArrayList;

public class ParkirDAO {

    Connection conn;

    public ParkirDAO() {

        conn = DBConnection.Connect();

        System.out.println(conn);
    }

    // =========================================
    // INSERT PARKIR
    // =========================================
    public boolean insertParkir(
            int idKendaraan,
            int idSlot
    ) {

        try {

            String query
                    = "INSERT INTO parkir "
                    + "(id_kendaraan, id_slot, waktu_masuk, status_parkir) "
                    + "VALUES (?, ?, ?, 'Parkir')";

            PreparedStatement ps
                    = conn.prepareStatement(query);

            ps.setInt(1, idKendaraan);
            ps.setInt(2, idSlot);

            ps.setTimestamp(
                    3,
                    new Timestamp(System.currentTimeMillis())
            );

            ps.executeUpdate();

            return true;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // =========================================
    // CEK PARKIR AKTIF
    // =========================================
    public boolean isParkirAktif(
            int idKendaraan
    ) {

        try {

            String query
                    = "SELECT * "
                    + "FROM parkir "
                    + "WHERE id_kendaraan=? "
                    + "AND status_parkir='Parkir'";

            PreparedStatement ps
                    = conn.prepareStatement(query);

            ps.setInt(1, idKendaraan);

            ResultSet rs
                    = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // =========================================
    // AMBIL SLOT AKTIF
    // =========================================
    public int getIdSlotAktif(
            int idKendaraan
    ) {

        try {

            String query
                    = "SELECT id_slot "
                    + "FROM parkir "
                    + "WHERE id_kendaraan=? "
                    + "AND status_parkir='Parkir'";

            PreparedStatement ps
                    = conn.prepareStatement(query);

            ps.setInt(1, idKendaraan);

            ResultSet rs
                    = ps.executeQuery();

            if (rs.next()) {

                return rs.getInt("id_slot");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return -1;
    }

    public Timestamp getWaktuMasuk(
            int idKendaraan
    ) {

        try {

            String query
                    = "SELECT waktu_masuk "
                    + "FROM parkir "
                    + "WHERE id_kendaraan=? "
                    + "AND status_parkir='Parkir'";

            PreparedStatement ps
                    = conn.prepareStatement(query);

            ps.setInt(1, idKendaraan);

            ResultSet rs
                    = ps.executeQuery();

            if (rs.next()) {

                return rs.getTimestamp("waktu_masuk");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    // =========================================
    // SELESAI PARKIR
    // =========================================
    public boolean selesaiParkir(
            int idKendaraan,
            int durasi,
            double biaya
    ) {

        try {

            String query
                    = "UPDATE parkir SET "
                    + "waktu_keluar=?, "
                    + "durasi_jam=?, "
                    + "biaya=?, "
                    + "status_parkir='Keluar' "
                    + "WHERE id_kendaraan=? "
                    + "AND status_parkir='Parkir'";

            PreparedStatement ps
                    = conn.prepareStatement(query);

            ps.setTimestamp(
                    1,
                    new Timestamp(System.currentTimeMillis())
            );

            ps.setInt(2, durasi);
            ps.setDouble(3, biaya);
            ps.setInt(4, idKendaraan);

            ps.executeUpdate();

            return true;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // =========================================
    // GET KENDARAAN PARKIR
    // =========================================
    public ArrayList<Kendaraan> getKendaraanParkir() {

        ArrayList<Kendaraan> list
                = new ArrayList<>();

        try {

            String query
                    = "SELECT "
                    + "k.id_kendaraan, "
                    + "k.plat_nomor, "
                    + "k.jenis_kendaraan, "
                    + "s.nomor_slot, "
                    + "p.waktu_masuk "
                    + "FROM parkir p "
                    + "JOIN kendaraan k "
                    + "ON p.id_kendaraan = k.id_kendaraan "
                    + "JOIN slot_parkir s "
                    + "ON p.id_slot = s.id_slot "
                    + "WHERE p.status_parkir='Parkir'";

            Statement st
                    = conn.createStatement();

            ResultSet rs
                    = st.executeQuery(query);

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

                kendaraan.setWaktuMasuk(
                        rs.getTimestamp("waktu_masuk")
                );

                list.add(kendaraan);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return list;
    }
}
