package Panel;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class SidebarPanel extends JPanel {

    JButton dashboardBtn;
    JButton masukBtn;
    JButton riwayatBtn;
    JButton logoutBtn;

    JLabel jamLabel;

    public SidebarPanel() {

        setLayout(new BorderLayout());

        setPreferredSize(
                new Dimension(200, 700)
        );

        setBackground(
                new Color(245, 245, 245)
        );

        setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        15,
                        20,
                        15
                )
        );

        JPanel menuPanel = new JPanel();

        menuPanel.setLayout(
                new GridLayout(4, 1, 10, 10)
        );

        menuPanel.setOpaque(false);

        dashboardBtn = new JButton("Dashboard");

        masukBtn = new JButton("Kendaraan Masuk");

        riwayatBtn = new JButton("Riwayat");

        logoutBtn = new JButton("Logout");

        styleButton(dashboardBtn);
        styleButton(masukBtn);
        styleButton(riwayatBtn);
        styleButton(logoutBtn);

        menuPanel.add(dashboardBtn);

        menuPanel.add(masukBtn);

        menuPanel.add(riwayatBtn);

        menuPanel.add(logoutBtn);

        jamLabel = new JLabel(
                "",
                SwingConstants.CENTER
        );

        jamLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        jamLabel.setForeground(Color.BLACK);

        startClock();

        add(menuPanel, BorderLayout.NORTH);

        add(jamLabel, BorderLayout.SOUTH);
    }

    private void styleButton(JButton btn) {

        btn.setFocusPainted(false);

        btn.setBackground(
                new Color(70, 70, 70)
        );

        btn.setForeground(Color.BLACK);

        btn.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        btn.setMargin(
                new Insets(
                        15,
                        20,
                        15,
                        20
                )
        );

        btn.setPreferredSize(
                new Dimension(
                        160,
                        50
                )
        );
    }

    private void startClock() {

        new Thread(() -> {

            while (true) {

                SwingUtilities.invokeLater(() -> {

                    jamLabel.setText(
                            "<html><center>"
                            + LocalDate.now()
                            + "<br>"
                            + LocalTime.now()
                                    .withNano(0)
                            + "</center></html>"
                    );
                });

                try {

                    Thread.sleep(1000);

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }

        }).start();
    }

    // =========================================
    // GETTER
    // =========================================
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
