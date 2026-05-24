package View;

import Panel.SidebarPanel;
import Controller.ParkirController;
import Model.SlotParkir;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class ParkirView extends JFrame {

    private ParkirController controller;

    public ParkirView(String username) {

        controller = new ParkirController();

        setTitle("Sistem Manajemen Parkir Mall");
        setSize(1200, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // =========================================
        // PANEL UTAMA
        // =========================================
        JPanel mainPanel = new JPanel(
                new BorderLayout(20, 20)
        );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 15, 15, 15
                )
        );

        mainPanel.setBackground(new Color(240, 240, 240));

        // =========================================
        // HEADER
        // =========================================
        JPanel header = new JPanel(
                new BorderLayout()
        );

        header.setPreferredSize(new Dimension(1200, 70));

        header.setBackground(new Color(30, 30, 30));

        header.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 20, 10, 20
                )
        );

        JLabel title = new JLabel(
                "DASHBOARD SISTEM PARKIR MALL"
        );

        title.setForeground(Color.WHITE);

        title.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        JLabel adminLabel = new JLabel(
                "Login sebagai : " + username
        );

        adminLabel.setForeground(Color.WHITE);

        adminLabel.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );

        header.add(title, BorderLayout.WEST);
        header.add(adminLabel, BorderLayout.EAST);

        // =========================================
        // SIDEBAR
        // =========================================
        SidebarPanel sidebar = new SidebarPanel();

        sidebar.getDashboardBtn().addActionListener(e -> {

            dispose();

            new ParkirView(username);
        });
        
        sidebar.getMasukBtn().addActionListener(e -> {

            dispose();

            new KendaraanMasukView(username);
        });

        sidebar.getRiwayatBtn().addActionListener(e -> {

            new RiwayatView(username);
            dispose();

        });

        sidebar.getLogoutBtn().addActionListener(e -> {

            dispose();

            new LoginView();
        });

        // =========================================
        // PANEL TENGAH
        // =========================================
        JPanel centerPanel = new JPanel(
                new BorderLayout(20, 20)
        );

        centerPanel.setOpaque(false);

        centerPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 10
                )
        );

        // =========================================
        // INFO PANEL
        // =========================================
        JPanel infoPanel = new JPanel(
                new GridLayout(1, 3, 20, 20)
        );

        infoPanel.setOpaque(false);

        JPanel kosongPanel = createInfoCard(
                "Slot Kosong",
                String.valueOf(
                        controller.getSlotKosong()
                )
        );

        JPanel terisiPanel = createInfoCard(
                "Slot Terisi",
                String.valueOf(
                        controller.getSlotTerisi()
                )
        );

        JPanel totalPanel = createInfoCard(
                "Total Slot",
                String.valueOf(
                        controller.getTotalSlot()
                )
        );

        infoPanel.add(kosongPanel);
        infoPanel.add(terisiPanel);
        infoPanel.add(totalPanel);

        // =========================================
        // AREA PARKIR
        // =========================================
        JPanel semuaArea = new JPanel(
                new BorderLayout(20, 20)
        );

        semuaArea.setBackground(Color.WHITE);

        semuaArea.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(Color.LIGHT_GRAY, 1),
                        BorderFactory.createEmptyBorder(
                                20, 20, 20, 20
                        )
                )
        );

        // =========================================
        // AREA ATAS
        // =========================================
        JPanel areaAtas = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 15, 15)
        );

        areaAtas.setOpaque(false);

        // =========================================
        // JALAN TENGAH
        // =========================================
        JPanel jalanPanel = new JPanel(
                new BorderLayout()
        );

        jalanPanel.setPreferredSize(
                new Dimension(800, 120)
        );

        jalanPanel.setBackground(
                new Color(80, 80, 80)
        );

        jalanPanel.setBorder(
                BorderFactory.createLineBorder(
                        Color.DARK_GRAY,
                        2
                )
        );

        JLabel jalanLabel = new JLabel(
                "- - - - - - - - - - - - - - - - - - -",
                SwingConstants.CENTER
        );

        jalanLabel.setForeground(Color.WHITE);

        jalanLabel.setFont(
                new Font("Arial", Font.BOLD, 72)
        );

        jalanPanel.add(jalanLabel);

        // =========================================
        // AREA BAWAH
        // =========================================
        JPanel areaBawah = new JPanel(
                new FlowLayout(
                        FlowLayout.CENTER,
                        15,
                        15
                )
        );

        areaBawah.setOpaque(false);

        ArrayList<SlotParkir> slots
                = controller.getAllSlot();

        for (SlotParkir data : slots) {

            JButton slot
                    = createSlotButton(
                            data.getNomorSlot()
                    );

            // =====================================
            // WARNA BERDASARKAN DATABASE
            // =====================================
            if (data.getStatusSlot()
                    .equals("Terisi")) {

                slot.setBackground(Color.RED);

            } else {

//                slot.setBackground(Color.GREEN);

            }

            // =====================================
            // MASUKKAN KE AREA
            // =====================================
            if (data.getNomorSlot()
                    .startsWith("A")) {

                areaAtas.add(slot);

            } else {

                areaBawah.add(slot);
            }
        }

        // =========================================
        // ADD AREA
        // =========================================
        semuaArea.add(areaAtas, BorderLayout.NORTH);

        semuaArea.add(jalanPanel, BorderLayout.CENTER);

        semuaArea.add(areaBawah, BorderLayout.SOUTH);

        // =========================================
        // LEGEND PANEL
        // =========================================
        JPanel legendPanel = new JPanel(
                new FlowLayout(
                        FlowLayout.LEFT,
                        15,
                        10
                )
        );

        legendPanel.setOpaque(false);

        legendPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 10
                )
        );

        JPanel hijau = new JPanel();
//        hijau.setBackground(Color.GREEN);
        hijau.setPreferredSize(
                new Dimension(20, 20)
        );

        JPanel merah = new JPanel();
        merah.setBackground(Color.RED);
        merah.setPreferredSize(
                new Dimension(20, 20)
        );

        legendPanel.add(hijau);
        legendPanel.add(new JLabel("Kosong"));

        legendPanel.add(Box.createHorizontalStrut(20));

        legendPanel.add(merah);
        legendPanel.add(new JLabel("Terisi"));

        // =========================================
        // ADD CENTER PANEL
        // =========================================
        centerPanel.add(infoPanel, BorderLayout.SOUTH);

        centerPanel.add(semuaArea, BorderLayout.CENTER);

//        centerPanel.add(legendPanel, BorderLayout.NORTH);
        // =========================================
        // ADD MAIN
        // =========================================
        mainPanel.add(header, BorderLayout.NORTH);

        mainPanel.add(sidebar, BorderLayout.WEST);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);

        setVisible(true);
    }

    // =========================================
    // SLOT BUTTON
    // =========================================
    private JButton createSlotButton(String text) {

        JButton btn = new JButton(text);

        btn.setPreferredSize(
                new Dimension(75, 100)
        );

//        btn.setBackground(Color.GREEN);

        btn.setOpaque(true);

        btn.setBorderPainted(false);

        btn.setForeground(Color.BLACK);

        btn.setFocusPainted(false);

        btn.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        btn.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        btn.setMargin(
                new Insets(10, 10, 10, 10)
        );

        btn.setBorder(
                new CompoundBorder(
                        new LineBorder(Color.BLACK, 2),
                        new EmptyBorder(10, 10, 10, 10)
                )
        );

        btn.addActionListener(e -> {

            if (btn.getBackground() == Color.GREEN) {

                int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "Pilih slot " + text + "?",
                        "Konfirmasi Slot",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {

                    btn.setBackground(Color.RED);

                    JOptionPane.showMessageDialog(
                            null,
                            "Slot " + text
                            + " berhasil dipilih!"
                    );
                }

            } else {

                JOptionPane.showMessageDialog(
                        null,
                        "Slot sudah terisi!"
                );
            }
        });

        return btn;
    }

    // =========================================
    // INFO CARD
    // =========================================
    private JPanel createInfoCard(
            String title,
            String value
    ) {

        JPanel panel = new JPanel(
                new BorderLayout()
        );

        panel.setBackground(Color.WHITE);

        panel.setBorder(
                new CompoundBorder(
                        new LineBorder(
                                Color.LIGHT_GRAY,
                                1
                        ),
                        new EmptyBorder(
                                20, 20, 20, 20
                        )
                )
        );

        JLabel titleLabel = new JLabel(
                title,
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        JLabel valueLabel = new JLabel(
                value,
                SwingConstants.CENTER
        );

        valueLabel.setFont(
                new Font("Arial", Font.BOLD, 32)
        );

        panel.add(titleLabel, BorderLayout.NORTH);

        panel.add(valueLabel, BorderLayout.CENTER);

        return panel;
    }

}
