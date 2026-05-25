package View;

import Controller.LoginController;
import Controller.RiwayatKendaraanController;
import Model.Kendaraan;
import Panel.HeaderPanel;
import Panel.SidebarPanel;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class RiwayatView extends JFrame {

    public JTextField tfSearch;

    public JButton btnTambah;
    public JButton btnEdit;
    public JButton btnHapus;
    public JButton btnCari;
    public JButton btnKembali;

    public JTable table;

    private RiwayatKendaraanController controller;

    public RiwayatView(String username) {

        controller = new RiwayatKendaraanController();
        controller.getKendaraanParkir();

        setTitle("Riwayat Kendaraan");

        setSize(1200, 750);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(
                new BorderLayout()
        );

        mainPanel.setBackground(
                new Color(240, 240, 240)
        );

        HeaderPanel header = new HeaderPanel("Riwayat Parkir", username);

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

            int confirm
                    = JOptionPane.showConfirmDialog(
                            null,
                            "Log out?",
                            "Konfirmasi",
                            JOptionPane.YES_NO_OPTION
                    );

            if (confirm == JOptionPane.YES_OPTION) {

                JOptionPane.showMessageDialog(
                        null,
                        "Berhasil Log out"
                );
                dispose();
                LoginView view = new LoginView();
                new LoginController(view);
            }

        });

        JPanel centerPanel = new JPanel(
                new BorderLayout(20, 20)
        );

        centerPanel.setBorder(
                new EmptyBorder(20, 20, 20, 20)
        );

        centerPanel.setOpaque(false);

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

        String[] column = {
            "ID",
            "Plat Nomor",
            "Jenis",
            "Slot",
            "Masuk",
            "Keluar",
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

        btnKembali = createButton(
                "Kembali",
                new Color(149, 165, 166)
        );

        buttonPanel.add(btnTambah);

        buttonPanel.add(btnEdit);

        buttonPanel.add(btnHapus);

        buttonPanel.add(btnKembali);

        centerPanel.add(topPanel,
                BorderLayout.NORTH);

        centerPanel.add(scrollPane,
                BorderLayout.CENTER);

        mainPanel.add(header,
                BorderLayout.NORTH);

        mainPanel.add(sidebar,
                BorderLayout.WEST);

        mainPanel.add(centerPanel,
                BorderLayout.CENTER);

        add(mainPanel);

        btnTambah.addActionListener(e -> {

            new FormKendaraanView(this,
                    "Tambah");
        });

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

                    loadTable();

                    JOptionPane.showMessageDialog(
                            null,
                            "Data berhasil dihapus!"
                    );
                }
            }
        });

        btnCari.addActionListener(e -> {

            String keyword
                    = tfSearch.getText();

            loadTableSearch(keyword);
        });

        btnKembali.addActionListener(e -> {

            dispose();

            new ParkirView("Admin");
        });

        setVisible(true);
    }

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
                k.getWaktuKeluarFormat(),
                k.getLamaParkir() + " Jam",
                "Rp" + k.getTarifParkir()
            };

            model.addRow(row);
        }
    }

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
                k.getWaktuKeluarFormat(),
                k.getLamaParkir() + " Jam",
                "Rp" + k.getTarifParkir()
            };

            model.addRow(row);
        }
    }

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
