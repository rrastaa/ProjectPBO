package Controller;

import DAO.KendaraanDAO;
import Model.Kendaraan;
import View.FormKendaraanView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

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

    // =========================================
    // GET ALL DATA
    // =========================================
    public ArrayList<Kendaraan> getAllKendaraan() {

        return dao.getAllKendaraan();
    }

    // =========================================
    // INSERT
    // =========================================
    public void insertKendaraan(
            Kendaraan kendaraan
    ) {

        dao.insertKendaraan(kendaraan);
    }

    // =========================================
    // UPDATE
    // =========================================
    public void updateKendaraan(
            Kendaraan kendaraan
    ) {

        dao.updateKendaraan(kendaraan);
    }

    // =========================================
    // DELETE
    // =========================================
    public void deleteKendaraan(int id) {

        dao.deleteKendaraan(id);
    }
    
    // =========================================
    // SELESAI
    // =========================================
    public void selesaiParkir(int id) {

        dao.selesaiParkir(id);
    }

    // =========================================
    // SEARCH
    // =========================================
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

            Kendaraan kendaraan = new Kendaraan();

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

            Kendaraan kendaraan = new Kendaraan();

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
