package DAO.Utils;

public class TarifService {

    public static int hitungTarif(
            String jenisKendaraan,
            int jam
    ) {

        if (jam < 1) {
            jam = 1;
        }

        int tarif = 0;

        if (jenisKendaraan.equalsIgnoreCase("Motor")) {

            tarif = 2000;

            if (jam > 1) {

                tarif += (jam - 1) * 1000;
            }
        }

        else if (jenisKendaraan.equalsIgnoreCase("Mobil")) {

            tarif = 3000;

            if (jam > 1) {

                tarif += (jam - 1) * 2000;
            }
        }

        return tarif;
    }
}