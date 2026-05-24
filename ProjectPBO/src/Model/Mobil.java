package Model;

public class Mobil extends Kendaraan {

    @Override
    public int hitungTarif(int jam) {

        if (jam < 1) {
            jam = 1;
        }

        return 3000 + ((jam - 1) * 2000);
    }
}