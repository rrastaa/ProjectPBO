package DAO;

import Connection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SlotDAO {

    Connection conn;

    public SlotDAO() {

        conn = DBConnection.Connect();

        System.out.println(conn);
    }

    // =========================================
    // CEK SLOT TERISI
    // =========================================
    public boolean isSlotTerisi(
            String nomorSlot
    ) {

        try {

            String query =
                    "SELECT status_slot "
                    + "FROM slot_parkir "
                    + "WHERE nomor_slot=?";

            PreparedStatement ps =
                    conn.prepareStatement(query);

            ps.setString(1, nomorSlot);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                String status =
                        rs.getString("status_slot");

                return status.equalsIgnoreCase("Terisi");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // =========================================
    // AMBIL ID SLOT
    // =========================================
    public int getIdSlot(
            String nomorSlot
    ) {

        try {

            String query =
                    "SELECT id_slot "
                    + "FROM slot_parkir "
                    + "WHERE nomor_slot=?";

            PreparedStatement ps =
                    conn.prepareStatement(query);

            ps.setString(1, nomorSlot);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                return rs.getInt("id_slot");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return -1;
    }

    // =========================================
    // UPDATE STATUS SLOT
    // =========================================
    public boolean updateStatusSlot(
            int idSlot,
            String status
    ) {

        try {

            String query =
                    "UPDATE slot_parkir "
                    + "SET status_slot=? "
                    + "WHERE id_slot=?";

            PreparedStatement ps =
                    conn.prepareStatement(query);

            ps.setString(1, status);
            ps.setInt(2, idSlot);

            ps.executeUpdate();

            return true;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // =========================================
    // GET SLOT BY NOMOR
    // =========================================
    public String getStatusSlot(
            String nomorSlot
    ) {

        try {

            String query =
                    "SELECT status_slot "
                    + "FROM slot_parkir "
                    + "WHERE nomor_slot=?";

            PreparedStatement ps =
                    conn.prepareStatement(query);

            ps.setString(1, nomorSlot);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                return rs.getString("status_slot");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }
}