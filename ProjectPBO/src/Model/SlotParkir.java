package Model;

public class SlotParkir {

    private int idSlot;
    private String nomorSlot;
    private String statusSlot;

    public SlotParkir(
            int idSlot,
            String nomorSlot,
            String statusSlot
    ) {
        this.idSlot = idSlot;
        this.nomorSlot = nomorSlot;
        this.statusSlot = statusSlot;
    }

    public int getIdSlot() {
        return idSlot;
    }

    public String getNomorSlot() {
        return nomorSlot;
    }

    public String getStatusSlot() {
        return statusSlot;
    }

    public void setStatusSlot(String statusSlot) {
        this.statusSlot = statusSlot;
    }
}