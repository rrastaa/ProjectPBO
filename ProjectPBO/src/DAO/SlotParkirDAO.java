package DAO;

import Connection.DBConnection;
import Model.SlotParkir;

import java.sql.*;
import java.util.ArrayList;

public class SlotParkirDAO {

    public ArrayList<SlotParkir> getAllSlot() {

        ArrayList<SlotParkir> list
                = new ArrayList<>();

        try {

            Connection conn
                    = DBConnection.Connect();

            String query
                    = "SELECT * FROM slot_parkir";

            Statement st
                    = conn.createStatement();

            ResultSet rs
                    = st.executeQuery(query);

            while (rs.next()) {

                SlotParkir slot
                        = new SlotParkir(
                                rs.getInt("id_slot"),
                                rs.getString("nomor_slot"),
                                rs.getString("status_slot")
                        );

                list.add(slot);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void updateStatusSlot(
            int idSlot,
            String status
    ) {

        try {

            Connection conn
                    = DBConnection.Connect();

            String query
                    = "UPDATE slot_parkir "
                    + "SET status_slot=? "
                    + "WHERE id_slot=?";

            PreparedStatement ps
                    = conn.prepareStatement(query);

            ps.setString(1, status);
            ps.setInt(2, idSlot);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getTotalSlot() {

        int total = 0;

        try {

            Connection conn
                    = DBConnection.Connect();

            String query
                    = "SELECT COUNT(*) AS total "
                    + "FROM slot_parkir";

            Statement st
                    = conn.createStatement();

            ResultSet rs
                    = st.executeQuery(query);

            if (rs.next()) {

                total = rs.getInt("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }

    public int getSlotKosong() {

        int total = 0;

        try {

            Connection conn
                    = DBConnection.Connect();

            String query
                    = "SELECT COUNT(*) AS total "
                    + "FROM slot_parkir "
                    + "WHERE status_slot='Kosong'";

            Statement st
                    = conn.createStatement();

            ResultSet rs
                    = st.executeQuery(query);

            if (rs.next()) {

                total = rs.getInt("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }

    public int getSlotTerisi() {

        int total = 0;

        try {

            Connection conn
                    = DBConnection.Connect();

            String query
                    = "SELECT COUNT(*) AS total "
                    + "FROM slot_parkir "
                    + "WHERE status_slot='Terisi'";

            Statement st
                    = conn.createStatement();

            ResultSet rs
                    = st.executeQuery(query);

            if (rs.next()) {

                total = rs.getInt("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }
}
