package DAO.Utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class WaktuHelper {

    public static String formatWaktu(
            Timestamp waktu
    ) {

        SimpleDateFormat sdf
                = new SimpleDateFormat(
                        "dd MMM yyyy HH:mm",
                        new Locale("id", "ID")
                );

        return sdf.format(waktu);
    }

    public static String hitungLamaParkir(
            Timestamp waktuMasuk
    ) {

        Timestamp sekarang
                = new Timestamp(System.currentTimeMillis());

        long selisihMs
                = sekarang.getTime()
                - waktuMasuk.getTime();

        long menit
                = selisihMs / (1000 * 60);

        long jam
                = menit / 60;

        long sisaMenit
                = menit % 60;

        return jam
                + " Jam "
                + sisaMenit
                + " Menit";
    }

    public static int hitungJam(Timestamp waktuMasuk) {

        Timestamp sekarang
                = new Timestamp(System.currentTimeMillis());

        long selisihMs
                = sekarang.getTime() - waktuMasuk.getTime();

        long menit
                = selisihMs / (1000 * 60);

        return (int) (menit / 60);
    }
}
