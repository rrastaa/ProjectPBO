package Controller;

import DAO.RiwayatDAO;
import Model.Kendaraan;
import View.FormKendaraanView;

import java.util.ArrayList;

public class RiwayatKendaraanController {

    private RiwayatDAO dao;
    private FormKendaraanView view;

    public RiwayatKendaraanController() {

        dao = new RiwayatDAO();
    }

    public ArrayList<Kendaraan> searchKendaraan(
            String keyword
    ) {

        return dao.searchKendaraan(keyword);
    }

    public ArrayList<Kendaraan> getKendaraanParkir() {

        return dao.getKendaraanParkir();
    }

    
}
