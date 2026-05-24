package Controller;

import DAO.SlotParkirDAO;
import Model.SlotParkir;

import java.util.ArrayList;

public class ParkirController {

    private SlotParkirDAO slotDAO;

    public ParkirController() {

        slotDAO = new SlotParkirDAO();
    }

    public ArrayList<SlotParkir> getAllSlot() {

        return slotDAO.getAllSlot();
    }

    public void isiSlot(int idSlot) {

        slotDAO.updateStatusSlot(
                idSlot,
                "Terisi"
        );
    }

    public void kosongkanSlot(int idSlot) {

        slotDAO.updateStatusSlot(
                idSlot,
                "Kosong"
        );
    }

    public int getTotalSlot() {

        return slotDAO.getTotalSlot();
    }

    public int getSlotKosong() {

        return slotDAO.getSlotKosong();
    }

    public int getSlotTerisi() {

        return slotDAO.getSlotTerisi();
    }
}
