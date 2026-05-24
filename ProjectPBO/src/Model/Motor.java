package Model;

public class Motor extends Kendaraan {

    @Override
    public int hitungTarif(int jam) {

        if (jam < 1) {
            jam = 1;
        }

        return 2000 + ((jam - 1) * 1000);
    }
}