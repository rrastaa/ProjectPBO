package Service;

import DAO.KendaraanDAO;
import DAO.ParkirDAO;
import DAO.SlotDAO;
import DAO.Utils.WaktuHelper;

import java.sql.Timestamp;
import Model.Kendaraan;

import java.util.ArrayList;

public class ParkirService {

    KendaraanDAO kendaraanDAO;
    ParkirDAO parkirDAO;
    SlotDAO slotDAO;

    public ParkirService() {

        kendaraanDAO = new KendaraanDAO();
        parkirDAO = new ParkirDAO();
        slotDAO = new SlotDAO();
    }

    // =========================================
    // PARKIR MASUK
    // =========================================
    public boolean parkirMasuk(
            Kendaraan kendaraan
    ) {

        try {

            // =========================================
            // 1. CEK SLOT
            // =========================================
            boolean slotTerisi
                    = slotDAO.isSlotTerisi(
                            kendaraan.getNomorSlot()
                    );

            if (slotTerisi) {

                System.out.println("Slot sudah terisi!");

                return false;
            }

            // =========================================
            // 2. INSERT / CEK KENDARAAN
            // =========================================
            int idKendaraan
                    = kendaraanDAO.insertKendaraan(
                            kendaraan
                    );

            if (idKendaraan == -1) {

                System.out.println("Gagal insert kendaraan!");

                return false;
            }

            // =========================================
            // 3. CEK PARKIR AKTIF
            // =========================================
            boolean parkirAktif
                    = parkirDAO.isParkirAktif(
                            idKendaraan
                    );

            if (parkirAktif) {

                System.out.println("Kendaraan masih parkir!");

                return false;
            }

            // =========================================
            // 4. AMBIL SLOT
            // =========================================
            int idSlot
                    = slotDAO.getIdSlot(
                            kendaraan.getNomorSlot()
                    );

            if (idSlot == -1) {

                System.out.println("Slot tidak ditemukan!");

                return false;
            }

            // =========================================
            // 5. INSERT PARKIR
            // =========================================
            boolean insertParkir
                    = parkirDAO.insertParkir(
                            idKendaraan,
                            idSlot
                    );

            if (!insertParkir) {

                System.out.println("Gagal insert parkir!");

                return false;
            }

            // =========================================
            // 6. UPDATE SLOT
            // =========================================
            slotDAO.updateStatusSlot(
                    idSlot,
                    "Terisi"
            );

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    // =========================================
    // PARKIR KELUAR
    // =========================================
    public boolean parkirKeluar(
            int idKendaraan
    ) {

        try {

            // ambil waktu masuk
            Timestamp waktuMasuk
                    = parkirDAO.getWaktuMasuk(
                            idKendaraan
                    );

            if (waktuMasuk == null) {

                return false;
            }

            // hitung jam
            int jam
                    = WaktuHelper.hitungJam(
                            waktuMasuk
                    );

            if (jam < 1) {
                jam = 1;
            }

            // ambil kendaraan
            Kendaraan kendaraan
                    = kendaraanDAO.getKendaraanById(
                            idKendaraan
                    );

            // hitung biaya
            double biaya
                    = TarifService.hitungTarif(
                            kendaraan.getJenisKendaraan(),
                            jam
                    );

            // update parkir
            boolean selesai
                    = parkirDAO.selesaiParkir(
                            idKendaraan,
                            jam,
                            biaya
                    );

            if (!selesai) {

                return false;
            }

            // ambil slot aktif
            int idSlot
                    = parkirDAO.getIdSlotAktif(
                            idKendaraan
                    );

            // kosongkan slot
            slotDAO.updateStatusSlot(
                    idSlot,
                    "Kosong"
            );

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
    public ArrayList<Kendaraan> getAllKendaraan() {

        return kendaraanDAO.getAllKendaraan();
    }

    // =========================================
    // SEARCH
    // =========================================
    public ArrayList<Kendaraan> searchKendaraan(
            String keyword
    ) {

        return kendaraanDAO.searchKendaraan(keyword);
    }

    // =========================================
    // GET KENDARAAN PARKIR
    // =========================================
//    public ArrayList<Kendaraan> getKendaraanParkir() {
//
//        return kendaraanDAO.getKendaraanParkir();
//    }

    // =========================================
    // TAMBAH PARKIR
    // =========================================
    public boolean tambahParkir(
            Kendaraan kendaraan
    ) {

        // logic parkir masuk disini

        return true;
    }

    // =========================================
    // UPDATE PARKIR
    // =========================================
    public boolean updateParkir(
            Kendaraan kendaraan
    ) {

        return kendaraanDAO.updateKendaraan(kendaraan);
    }

    // =========================================
    // DELETE
    // =========================================
    public boolean deleteKendaraan(
            int id
    ) {

        return kendaraanDAO.deleteKendaraan(id);
    }

}
