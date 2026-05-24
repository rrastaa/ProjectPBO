package View;

import Controller.KendaraanController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FormKendaraanView extends JFrame {

    public String mode;
    public int id;

    public JTextField tfPlat;
    public JComboBox<String> cbJenis;
    public JTextField tfSlot;

    public JButton btnSimpan;
    public JButton btnBatal;

    public FormKendaraanView(
            JFrame parent,
            String mode
    ) {

        this.mode = mode;

        setTitle(mode + " Kendaraan");

        setSize(450, 300);

        setLocationRelativeTo(parent);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(
                new BorderLayout(15, 15)
        );

        mainPanel.setBorder(
                new EmptyBorder(20, 20, 20, 20)
        );

        // TITLE
        JLabel title = new JLabel(
                mode + " Kendaraan"
        );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        // FORM PANEL
        JPanel formPanel = new JPanel(
                new GridLayout(3, 2, 15, 15)
        );

        // PLAT
        formPanel.add(
                new JLabel("Plat Nomor")
        );

        tfPlat = new JTextField();

        formPanel.add(tfPlat);

        // JENIS
        formPanel.add(
                new JLabel("Jenis Kendaraan")
        );

        cbJenis = new JComboBox<>(
                new String[]{
                    "Motor",
                    "Mobil"
                }
        );

        formPanel.add(cbJenis);

        // SLOT
        formPanel.add(
                new JLabel("Nomor Slot")
        );

        tfSlot = new JTextField();

        formPanel.add(tfSlot);

        // BUTTON PANEL
        JPanel buttonPanel = new JPanel(
                new FlowLayout(
                        FlowLayout.CENTER,
                        15,
                        10
                )
        );

        btnSimpan = new JButton("Simpan");

        btnBatal = new JButton("Batal");

        buttonPanel.add(btnSimpan);

        buttonPanel.add(btnBatal);

        // ADD PANEL
        mainPanel.add(
                title,
                BorderLayout.NORTH
        );

        mainPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        add(mainPanel);

        // BATAL
        btnBatal.addActionListener(e -> {

            dispose();
        });

        // controller otomatis bind listener
        new KendaraanController(this);

        setVisible(true);
    }

    // =========================================
    // CONSTRUCTOR EDIT
    // =========================================
    public FormKendaraanView(
            JFrame parent,
            String mode,
            int id,
            String plat,
            String jenis,
            String slot
    ) {

        this(parent, mode);

        this.id = id;

        tfPlat.setText(plat);

        cbJenis.setSelectedItem(jenis);

        tfSlot.setText(slot);
    }
}