package View;

import Controller.KendaraanController;
import Model.Kendaraan;
import Panel.SidebarPanel;
import View.FormKendaraanView;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class KendaraanMasukView extends JFrame {

    public JTextField tfSearch;

    public JButton btnTambah;
    public JButton btnEdit;
    public JButton btnHapus;
    public JButton btnCari;
    public JButton btnSelesai;

    public JTable table;

    private KendaraanController controller;

    public KendaraanMasukView(String username) {

        controller = new KendaraanController();
        controller.getKendaraanParkir();

        setTitle("Data Kendaraan");

        setSize(1200, 750);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // =========================================
        // MAIN PANEL
        // =========================================
        JPanel mainPanel = new JPanel(
                new BorderLayout(20, 20)
        );

        mainPanel.setBorder(
                new EmptyBorder(15, 15, 15, 15)
        );

        mainPanel.setBackground(
                new Color(240, 240, 240)
        );

        // =========================================
        // HEADER
        // =========================================
        JPanel header = new JPanel(
                new BorderLayout()
        );

        header.setPreferredSize(
                new Dimension(1200, 70)
        );

        header.setBackground(
                new Color(30, 30, 30)
        );

        header.setBorder(
                new EmptyBorder(10, 20, 10, 20)
        );

        JLabel title = new JLabel(
                "DATA KENDARAAN PARKIR"
        );

        title.setForeground(Color.WHITE);

        title.setFont(
                new Font("Arial",
                        Font.BOLD,
                        24)
        );

        JLabel adminLabel = new JLabel(
                "Login sebagai : " + username
        );

        adminLabel.setForeground(Color.WHITE);

        adminLabel.setFont(
                new Font("Arial",
                        Font.PLAIN,
                        16)
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
        // CENTER PANEL
        // =========================================
        JPanel centerPanel = new JPanel(
                new BorderLayout(20, 20)
        );

        centerPanel.setOpaque(false);

        // =========================================
        // TOP PANEL
        // =========================================
        JPanel topPanel = new JPanel(
                new BorderLayout()
        );

        topPanel.setOpaque(false);

        JLabel subTitle = new JLabel(
                "Tabel Data Kendaraan"
        );

        subTitle.setFont(
                new Font("Arial",
                        Font.BOLD,
                        22)
        );

        JPanel searchPanel = new JPanel(
                new FlowLayout(
                        FlowLayout.RIGHT
                )
        );

        searchPanel.setOpaque(false);

        tfSearch = new JTextField(18);

        tfSearch.setPreferredSize(
                new Dimension(200, 35)
        );

        btnCari = createButton(
                "Cari",
                new Color(52, 152, 219)
        );

        searchPanel.add(tfSearch);

        searchPanel.add(btnCari);

        topPanel.add(subTitle,
                BorderLayout.WEST);

        topPanel.add(searchPanel,
                BorderLayout.EAST);

        // =========================================
        // TABLE
        // =========================================
        String[] column = {
            "ID",
            "Plat Nomor",
            "Jenis",
            "Slot",
            "Waktu Masuk",
            "Durasi Parkir",
            "Total (Rp)"
        };

        DefaultTableModel model
                = new DefaultTableModel(column, 0);

        table = new JTable(model);

        loadTable();

        table.setRowHeight(30);

        table.setFont(
                new Font("Arial",
                        Font.PLAIN,
                        14)
        );

        table.getTableHeader().setFont(
                new Font("Arial",
                        Font.BOLD,
                        14)
        );

        JScrollPane scrollPane
                = new JScrollPane(table);

        scrollPane.setBorder(
                new CompoundBorder(
                        new LineBorder(
                                Color.LIGHT_GRAY,
                                1
                        ),
                        new EmptyBorder(
                                10, 10, 10, 10
                        )
                )
        );

        // =========================================
        // BUTTON PANEL
        // =========================================
        JPanel buttonPanel = new JPanel(
                new FlowLayout(
                        FlowLayout.CENTER,
                        15,
                        10
                )
        );

        buttonPanel.setOpaque(false);

        btnTambah = createButton(
                "Tambah",
                new Color(46, 204, 113)
        );

        btnEdit = createButton(
                "Edit",
                new Color(241, 196, 15)
        );

        btnHapus = createButton(
                "Hapus",
                new Color(231, 76, 60)
        );

        btnSelesai = createButton(
                "Selesai",
                new Color(149, 165, 166)
        );

        buttonPanel.add(btnTambah);

        buttonPanel.add(btnEdit);

        buttonPanel.add(btnHapus);

        buttonPanel.add(btnSelesai);

        // =========================================
        // ADD CENTER PANEL
        // =========================================
        centerPanel.add(topPanel,
                BorderLayout.NORTH);

        centerPanel.add(scrollPane,
                BorderLayout.CENTER);

        centerPanel.add(buttonPanel,
                BorderLayout.SOUTH);

        // =========================================
        // ADD MAIN PANEL
        // =========================================
        mainPanel.add(header,
                BorderLayout.NORTH);

        mainPanel.add(sidebar,
                BorderLayout.WEST);

        mainPanel.add(centerPanel,
                BorderLayout.CENTER);

        add(mainPanel);

        // =========================================
        // ACTION BUTTON
        // =========================================
        // TAMBAH
        btnTambah.addActionListener(e -> {

            new FormKendaraanView(this,
                    "Tambah");
        });

        // EDIT
        btnEdit.addActionListener(e -> {

            int row = table.getSelectedRow();

            if (row == -1) {

                JOptionPane.showMessageDialog(null,
                        "Pilih data terlebih dahulu!");

            } else {

                int id = Integer.parseInt(
                        table.getValueAt(row, 0).toString()
                );

                String plat = table.getValueAt(row, 1).toString();
                String jenis = table.getValueAt(row, 2).toString();
                String slot = table.getValueAt(row, 3).toString();

                new FormKendaraanView(
                        this,
                        "Edit",
                        id,
                        plat,
                        jenis,
                        slot
                );
            }
        });

        // HAPUS
        btnHapus.addActionListener(e -> {

            int row = table.getSelectedRow();

            if (row == -1) {

                JOptionPane.showMessageDialog(
                        null,
                        "Pilih data terlebih dahulu!"
                );

            } else {

                int id = Integer.parseInt(
                        table.getValueAt(row, 0)
                                .toString()
                );

                int confirm
                        = JOptionPane.showConfirmDialog(
                                null,
                                "Yakin hapus data?",
                                "Konfirmasi",
                                JOptionPane.YES_NO_OPTION
                        );

                if (confirm == JOptionPane.YES_OPTION) {

                    controller.deleteKendaraan(id);

                    loadTable();

                    JOptionPane.showMessageDialog(
                            null,
                            "Data berhasil dihapus!"
                    );
                }
            }
        });

        // CARI
        btnCari.addActionListener(e -> {

            String keyword
                    = tfSearch.getText();

            loadTableSearch(keyword);
        });

        // SELESAI
        btnSelesai.addActionListener(e -> {

            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Pilih data terlebih dahulu!");
                return;
            }

            int id = Integer.parseInt(table.getValueAt(row, 0).toString());
            String tarif = table.getValueAt(row, 6).toString(); // kolom total

            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Total yang harus dibayar: " + tarif + "\n\nSelesaikan transaksi?",
                    "Konfirmasi Pembayaran",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {

                controller.selesaiParkir(id);
                loadTable();

                JOptionPane.showMessageDialog(
                        null,
                        "Pembayaran berhasil!\nTotal: " + tarif
                );
            }
        });

        setVisible(true);
    }

    // =========================================
    // LOAD TABLE
    // =========================================
    public void loadTable() {

        DefaultTableModel model
                = (DefaultTableModel) table.getModel();

        model.setRowCount(0);

        ArrayList<Kendaraan> list
                = controller.getKendaraanParkir();

        for (Kendaraan k : list) {

            Object[] row = {
                k.getIdKendaraan(),
                k.getPlatNomor(),
                k.getJenisKendaraan(),
                k.getNomorSlot(),
                k.getWaktuMasukFormat(),
                k.getLamaParkir(),
                "Rp" + k.getTarifParkir()
            };

            model.addRow(row);
        }
    }

    // =========================================
    // LOAD SEARCH
    // =========================================
    public void loadTableSearch(String keyword) {

        DefaultTableModel model
                = (DefaultTableModel) table.getModel();

        model.setRowCount(0);

        ArrayList<Kendaraan> list
                = controller.searchKendaraan(keyword);

        for (Kendaraan k : list) {

            Object[] row = {
                k.getIdKendaraan(),
                k.getPlatNomor(),
                k.getJenisKendaraan(),
                k.getNomorSlot(),
                k.getWaktuMasukFormat(),
                k.getLamaParkir(),
                "Rp" + k.getTarifParkir()
            };

            model.addRow(row);
        }
    }

    // =========================================
    // BUTTON STYLE
    // =========================================
    private JButton createButton(
            String text,
            Color color
    ) {

        JButton btn = new JButton(text);

        btn.setBackground(color);

        btn.setForeground(Color.BLACK);

        btn.setFocusPainted(false);

        btn.setFont(
                new Font("Arial",
                        Font.BOLD,
                        14)
        );

        btn.setPreferredSize(
                new Dimension(120, 40)
        );

        return btn;
    }

}
