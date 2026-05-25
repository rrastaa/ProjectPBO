package Controller;

import DAO.KendaraanDAO;
import Model.Kendaraan;
import Model.Mobil;
import Model.Motor;
import View.FormKendaraanView;

import javax.swing.*;

import java.util.ArrayList;

public class KendaraanController {

    private KendaraanDAO dao;
    private FormKendaraanView view;

    public KendaraanController() {

        dao = new KendaraanDAO();
    }

    public KendaraanController(FormKendaraanView view) {

        this.view = view;
        dao = new KendaraanDAO();

        if (view.mode.equalsIgnoreCase("Tambah")) {
            bindInsert();
        } else if (view.mode.equalsIgnoreCase("Edit")) {
            bindUpdate();
        }
    }

    public ArrayList<Kendaraan> getAllKendaraan() {

        return dao.getAllKendaraan();
    }

    public void insertKendaraan(
            Kendaraan kendaraan
    ) {

        dao.insertKendaraan(kendaraan);
    }

    public void updateKendaraan(
            Kendaraan kendaraan
    ) {

        dao.updateKendaraan(kendaraan);
    }

    public void deleteKendaraan(int id) {

        dao.deleteKendaraan(id);
    }

    public void selesaiParkir(int id) {

        dao.selesaiParkir(id);
    }

    public ArrayList<Kendaraan> searchKendaraan(
            String keyword
    ) {

        return dao.searchKendaraan(keyword);
    }

    public ArrayList<Kendaraan> getKendaraanParkir() {

        return dao.getKendaraanParkir();
    }

    public void bindInsert() {

        view.btnSimpan.addActionListener(e -> {
            String jenis
                    = view.cbJenis
                            .getSelectedItem()
                            .toString();

            Kendaraan kendaraan;

            if (jenis.equalsIgnoreCase("Motor")) {

                kendaraan = new Motor();

            } else {

                kendaraan = new Mobil();
            }

            kendaraan.setPlatNomor(view.tfPlat.getText());
            kendaraan.setJenisKendaraan(view.cbJenis.getSelectedItem().toString());
            kendaraan.setNomorSlot(view.tfSlot.getText());

            boolean berhasil = dao.insertKendaraan(kendaraan);

            if (berhasil) {
                JOptionPane.showMessageDialog(null, "Data berhasil ditambah!");
                view.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Data gagal ditambah!");
            }
            
        });
    }

    public void bindUpdate() {

        view.btnSimpan.addActionListener(e -> {

            String jenis
                    = view.cbJenis
                            .getSelectedItem()
                            .toString();

            Kendaraan kendaraan;

            if (jenis.equalsIgnoreCase("Motor")) {

                kendaraan = new Motor();

            } else {

                kendaraan = new Mobil();
            }

            kendaraan.setIdKendaraan(view.id);
            kendaraan.setPlatNomor(view.tfPlat.getText());
            kendaraan.setJenisKendaraan(view.cbJenis.getSelectedItem().toString());
            kendaraan.setNomorSlot(view.tfSlot.getText());

            boolean berhasil = dao.updateKendaraan(kendaraan);

            if (berhasil) {
                JOptionPane.showMessageDialog(null, "Data berhasil diupdate!");
                view.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Data gagal diupdate!");
            }
        });
    }
}
