package Service;

public class TarifService {

    // =========================================
    // HITUNG TARIF PARKIR
    // =========================================
    public static double hitungTarif(
            String jenisKendaraan,
            int jam
    ) {

        // minimal 1 jam
        if (jam < 1) {
            jam = 1;
        }

        double tarif = 0;

        // =========================================
        // MOTOR
        // =========================================
        if (jenisKendaraan.equalsIgnoreCase("Motor")) {

            tarif = 2000;

            if (jam > 1) {

                tarif += (jam - 1) * 1000;
            }
        }

        // =========================================
        // MOBIL
        // =========================================
        else if (jenisKendaraan.equalsIgnoreCase("Mobil")) {

            tarif = 3000;

            if (jam > 1) {

                tarif += (jam - 1) * 2000;
            }
        }

        return tarif;
    }
}