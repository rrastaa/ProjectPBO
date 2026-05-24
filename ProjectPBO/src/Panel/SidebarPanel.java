package Panel;

import View.LoginView;

import javax.swing.*;
import java.awt.*;

public class SidebarPanel extends JPanel {

    JButton dashboardBtn;
    JButton masukBtn;
    JButton riwayatBtn;
    JButton logoutBtn;

    public SidebarPanel() {

        setLayout(new GridLayout(10, 1, 10, 10));

        setPreferredSize(new Dimension(200, 700));

        setBackground(Color.LIGHT_GRAY);

        // PADDING PANEL
        setBorder(
                BorderFactory.createEmptyBorder(
                        20, // atas
                        15, // kiri
                        20, // bawah
                        15 // kanan
                )
        );

        dashboardBtn = new JButton("Dashboard");
        masukBtn = new JButton("Kendaraan Masuk");
        riwayatBtn = new JButton("Riwayat");
        logoutBtn = new JButton("Logout");

        styleButton(dashboardBtn);
        styleButton(masukBtn);
        styleButton(riwayatBtn);
        styleButton(logoutBtn);

        add(new JLabel(""));
        add(dashboardBtn);
        add(masukBtn);
        add(riwayatBtn);
        add(logoutBtn);
    }

    private void styleButton(JButton btn) {

        btn.setFocusPainted(false);

        btn.setBackground(new Color(70, 70, 70));

        btn.setForeground(Color.BLACK);

        btn.setFont(
                new Font("Arial", Font.PLAIN, 12)
        );
    }

    // OPTIONAL GETTER
    public JButton getDashboardBtn() {
        
        return dashboardBtn;
    }

    public JButton getMasukBtn() {
        return masukBtn;
    }

    public JButton getRiwayatBtn() {
        return riwayatBtn;
    }

    public JButton getLogoutBtn() {
        return logoutBtn;
    }
}
