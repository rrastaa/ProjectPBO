package DAO;

import Connection.DBConnection;
import Model.Kendaraan;
import DAO.Utils.WaktuHelper;

import java.sql.Timestamp;
import java.sql.*;
import java.util.ArrayList;

public class KendaraanDAO {

    Connection conn;

    public KendaraanDAO() {

        conn = DBConnection.Connect();

        System.out.println(conn);
    }

    // =========================================
    // GET ALL DATA
    // =========================================
    public ArrayList<Kendaraan> getAllKendaraan() {

        ArrayList<Kendaraan> list
                = new ArrayList<>();

        String query
                = "SELECT * FROM kendaraan";

        try {

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

                kendaraan.setPemilikKendaraan(
                        rs.getString("pemilik")
                );

                list.add(kendaraan);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return list;
    }

    // =========================================
    // INSERT
    // =========================================
    public boolean insertKendaraan(Kendaraan kendaraan) {

        try {

            // =========================================
            // 1. CEK / INSERT KENDARAAN
            // =========================================
            String checkQuery
                    = "SELECT id_kendaraan FROM kendaraan WHERE plat_nomor=?";

            PreparedStatement psCheck
                    = conn.prepareStatement(checkQuery);

            psCheck.setString(1, kendaraan.getPlatNomor());

            ResultSet rsCheck = psCheck.executeQuery();

            int idKendaraan;

            if (rsCheck.next()) {

                idKendaraan = rsCheck.getInt("id_kendaraan");

            } else {

                String insertKendaraan
                        = "INSERT INTO kendaraan (plat_nomor, jenis_kendaraan) VALUES (?, ?)";

                PreparedStatement psInsert
                        = conn.prepareStatement(insertKendaraan, Statement.RETURN_GENERATED_KEYS);

                psInsert.setString(1, kendaraan.getPlatNomor());
                psInsert.setString(2, kendaraan.getJenisKendaraan());

                psInsert.executeUpdate();

                ResultSet rsGen = psInsert.getGeneratedKeys();

                if (!rsGen.next()) {
                    return false;
                }

                idKendaraan = rsGen.getInt(1);
            }

            // =========================================
            // 2. CEK SLOT
            // =========================================
            String checkSlot
                    = "SELECT id_slot, status_slot FROM slot_parkir WHERE nomor_slot=?";

            PreparedStatement psSlot
                    = conn.prepareStatement(checkSlot);

            psSlot.setString(1, kendaraan.getNomorSlot());

            ResultSet rsSlot = psSlot.executeQuery();

            if (!rsSlot.next()) {
                System.out.println("Slot tidak ditemukan!");
                return false;
            }

            int idSlot = rsSlot.getInt("id_slot");
            String status = rsSlot.getString("status_slot");

            if ("Terisi".equalsIgnoreCase(status)) {
                System.out.println("Slot sudah terisi!");
                return false;
            }

            // =========================================
            // 3. CEK KENDARAAN SUDAH PARKIR AKTIF
            // =========================================
            String checkActive
                    = "SELECT p.id_parkir "
                    + "FROM parkir p "
                    + "JOIN slot_parkir s ON p.id_slot = s.id_slot "
                    + "WHERE p.id_kendaraan=? AND s.status_slot='Terisi'";

            PreparedStatement psActive
                    = conn.prepareStatement(checkActive);

            psActive.setInt(1, idKendaraan);

            ResultSet rsActive = psActive.executeQuery();

            if (rsActive.next()) {
                System.out.println("Kendaraan masih parkir!");
                return false;
            }

            // =========================================
            // 4. INSERT PARKIR
            // =========================================
            String insertParkir
                    = "INSERT INTO parkir (id_kendaraan, id_slot, waktu_masuk) VALUES (?, ?, ?)";

            PreparedStatement psParkir
                    = conn.prepareStatement(insertParkir);

            psParkir.setInt(1, idKendaraan);
            psParkir.setInt(2, idSlot);
            psParkir.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            psParkir.executeUpdate();

            // =========================================
            // 5. UPDATE SLOT
            // =========================================
            String updateSlot
                    = "UPDATE slot_parkir SET status_slot='Terisi' WHERE id_slot=?";

            PreparedStatement psUpdate
                    = conn.prepareStatement(updateSlot);

            psUpdate.setInt(1, idSlot);

            psUpdate.executeUpdate();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // =========================================
    // UPDATE
    // =========================================
    public boolean updateKendaraan(Kendaraan kendaraan) {

        try {

            // =========================================
            // 1. AMBIL SLOT LAMA
            // =========================================
            String getOldSlot
                    = "SELECT id_slot FROM parkir WHERE id_kendaraan=?";

            PreparedStatement psOld
                    = conn.prepareStatement(getOldSlot);

            psOld.setInt(1, kendaraan.getIdKendaraan());

            ResultSet rsOld = psOld.executeQuery();

            Integer oldSlot = null;

            System.out.println("Masih Aman");

            if (rsOld.next()) {
                oldSlot = rsOld.getInt("id_slot");
            }

            // =========================================
            // 2. UPDATE KENDARAAN
            // =========================================
            String updateKendaraan
                    = "UPDATE kendaraan SET plat_nomor=?, jenis_kendaraan=? WHERE id_kendaraan=?";

            PreparedStatement ps
                    = conn.prepareStatement(updateKendaraan);

            ps.setString(1, kendaraan.getPlatNomor());
            ps.setString(2, kendaraan.getJenisKendaraan());
            ps.setInt(3, kendaraan.getIdKendaraan());

            System.out.println("Masuk Slot Aman");

            ps.executeUpdate();

            // =========================================
            // 3. UPDATE PARKIR (slot baru)
            // =========================================
            String getNewSlot
                    = "SELECT id_slot FROM slot_parkir WHERE nomor_slot=?";

            PreparedStatement psNew
                    = conn.prepareStatement(getNewSlot);

            psNew.setString(1, kendaraan.getNomorSlot());

            ResultSet rsNew = psNew.executeQuery();

            if (!rsNew.next()) {
                System.out.println("Slot baru tidak ditemukan!");
                return false;
            }

            int newSlot = rsNew.getInt("id_slot");

            // =========================================
            // 4. UPDATE PARKIR TABLE
            // =========================================
            String updateParkir
                    = "UPDATE parkir SET id_slot=? WHERE id_kendaraan=?";

            PreparedStatement psParkir
                    = conn.prepareStatement(updateParkir);

            psParkir.setInt(1, newSlot);
            psParkir.setInt(2, kendaraan.getIdKendaraan());

            System.out.println("Update Table Aman");

            psParkir.executeUpdate();

            // =========================================
            // 5. UPDATE STATUS SLOT (OLD -> KOSONG)
            // =========================================
            if (oldSlot != null) {

                String kosongkanOld
                        = "UPDATE slot_parkir SET status_slot='Kosong' WHERE id_slot=?";

                PreparedStatement psKosong
                        = conn.prepareStatement(kosongkanOld);

                psKosong.setInt(1, oldSlot);

                System.out.println("Slot Kosong aman");

                psKosong.executeUpdate();
            }

            // =========================================
            // 6. UPDATE STATUS SLOT (NEW -> TERISI)
            // =========================================
            String isiNew
                    = "UPDATE slot_parkir SET status_slot='Terisi' WHERE id_slot=?";

            PreparedStatement psIsi
                    = conn.prepareStatement(isiNew);

            psIsi.setInt(1, newSlot);

            psIsi.executeUpdate();

            System.out.println("Update Aman");

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // =========================================
    // DELETE
    // =========================================
    public boolean deleteKendaraan(int id) {

        try {

            // =========================================
            // 1. AMBIL SLOT YANG DIPAKAI KENDARAAN
            // =========================================
            String getSlot
                    = "SELECT p.id_slot "
                    + "FROM parkir p "
                    + "JOIN slot_parkir s ON p.id_slot = s.id_slot "
                    + "WHERE p.id_kendaraan=? AND s.status_slot='Terisi'";

            PreparedStatement psGetSlot
                    = conn.prepareStatement(getSlot);

            psGetSlot.setInt(1, id);

            ResultSet rs = psGetSlot.executeQuery();

            Integer idSlot = null;

            if (rs.next()) {
                idSlot = rs.getInt("id_slot");
            }

            // =========================================
            // 2. UPDATE SLOT JADI KOSONG (kalau ada)
            // =========================================
            if (idSlot != null) {

                String updateSlot
                        = "UPDATE slot_parkir SET status_slot='Kosong' WHERE id_slot=?";

                PreparedStatement psUpdate
                        = conn.prepareStatement(updateSlot);

                psUpdate.setInt(1, idSlot);

                psUpdate.executeUpdate();
            }

            // =========================================
            // 3. HAPUS DATA PARKIR
            // =========================================
            String deleteParkir
                    = "DELETE FROM parkir WHERE id_kendaraan=?";

            PreparedStatement psDeleteParkir
                    = conn.prepareStatement(deleteParkir);

            psDeleteParkir.setInt(1, id);

            psDeleteParkir.executeUpdate();

            // =========================================
            // 4. HAPUS KENDARAAN
            // =========================================
            String deleteKendaraan
                    = "DELETE FROM kendaraan WHERE id_kendaraan=?";

            PreparedStatement psDeleteKendaraan
                    = conn.prepareStatement(deleteKendaraan);

            psDeleteKendaraan.setInt(1, id);

            psDeleteKendaraan.executeUpdate();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
                + "JOIN kendaraan k ON p.id_kendaraan = k.id_kendaraan "
                + "JOIN slot_parkir s ON p.id_slot = s.id_slot "
                + "WHERE (k.plat_nomor LIKE ? OR s.nomor_slot LIKE ?) "
                + "AND s.status_slot = 'Terisi'";

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

                Kendaraan kendaraan = new Kendaraan();

                kendaraan.setIdKendaraan(rs.getInt("id_kendaraan"));
                kendaraan.setPlatNomor(rs.getString("plat_nomor"));
                kendaraan.setJenisKendaraan(rs.getString("jenis_kendaraan"));

                Timestamp waktuMasuk = rs.getTimestamp("waktu_masuk");

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
                kendaraan.setLamaParkir(
                        WaktuHelper.hitungLamaParkir(waktuMasuk)
                );

                kendaraan.setTarifParkir(tarif);

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
                + "p.waktu_masuk "
                + "FROM parkir p "
                + "JOIN kendaraan k "
                + "ON p.id_kendaraan = k.id_kendaraan "
                + "JOIN slot_parkir s "
                + "ON p.id_slot = s.id_slot "
                + "WHERE s.status_slot = 'Terisi'";

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
                kendaraan.setLamaParkir(
                        WaktuHelper.hitungLamaParkir(waktuMasuk)
                );

                kendaraan.setTarifParkir(tarif);

                list.add(kendaraan);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return list;
    }

    public boolean selesaiParkir(int idKendaraan) {

        try {

            // =========================================
            // 1. AMBIL DATA PARKIR AKTIF
            // =========================================
            String getData
                    = "SELECT id_parkir, id_slot, waktu_masuk "
                    + "FROM parkir "
                    + "WHERE id_kendaraan=? AND status_parkir='Parkir'";

            PreparedStatement psGet
                    = conn.prepareStatement(getData);

            psGet.setInt(1, idKendaraan);

            ResultSet rs = psGet.executeQuery();

            if (!rs.next()) {
                System.out.println("Tidak ada parkir aktif!");
                return false;
            }

            int idParkir = rs.getInt("id_parkir");
            int idSlot = rs.getInt("id_slot");
            Timestamp waktuMasuk = rs.getTimestamp("waktu_masuk");

            Timestamp waktuKeluar
                    = new Timestamp(System.currentTimeMillis());

            // =========================================
            // 2. HITUNG DURASI (JAM)
            // =========================================
            long diffMs
                    = waktuKeluar.getTime() - waktuMasuk.getTime();

            int jam
                    = (int) Math.ceil(diffMs / (1000.0 * 60 * 60));

            if (jam < 1) {
                jam = 1;
            }

            // =========================================
            // 3. HITUNG BIAYA
            // =========================================
            String getJenis
                    = "SELECT jenis_kendaraan FROM kendaraan WHERE id_kendaraan=?";

            PreparedStatement psJenis
                    = conn.prepareStatement(getJenis);

            psJenis.setInt(1, idKendaraan);

            ResultSet rsJenis = psJenis.executeQuery();

            String jenis = "Motor";

            if (rsJenis.next()) {
                jenis = rsJenis.getString("jenis_kendaraan");
            }

            double biaya = 0;

            if (jenis.equalsIgnoreCase("Motor")) {

                biaya = 2000;

                if (jam > 1) {
                    biaya += (jam - 1) * 1000;
                }

            } else if (jenis.equalsIgnoreCase("Mobil")) {

                biaya = 3000;

                if (jam > 1) {
                    biaya += (jam - 1) * 2000;
                }
            }

            // =========================================
            // 4. UPDATE PARKIR (SELESAI)
            // =========================================
            String update
                    = "UPDATE parkir SET "
                    + "waktu_keluar=?, "
                    + "durasi_jam=?, "
                    + "biaya=?, "
                    + "status_parkir='Keluar' "
                    + "WHERE id_parkir=?";

            PreparedStatement psUpdate
                    = conn.prepareStatement(update);

            psUpdate.setTimestamp(1, waktuKeluar);
            psUpdate.setInt(2, jam);
            psUpdate.setDouble(3, biaya);
            psUpdate.setInt(4, idParkir);

            psUpdate.executeUpdate();

            // =========================================
            // 5. KOSONGKAN SLOT
            // =========================================
            String updateSlot
                    = "UPDATE slot_parkir SET status_slot='Kosong' WHERE id_slot=?";

            PreparedStatement psSlot
                    = conn.prepareStatement(updateSlot);

            psSlot.setInt(1, idSlot);

            psSlot.executeUpdate();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
