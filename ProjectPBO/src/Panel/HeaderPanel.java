package Panel;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class HeaderPanel extends JPanel {

    public HeaderPanel(String head, String username) {

        setLayout(new BorderLayout());

        setPreferredSize(
                new Dimension(1200, 70)
        );

        setBackground(
                new Color(30, 30, 30)
        );

        setBorder(
                new EmptyBorder(10, 20, 10, 20)
        );

        JLabel title = new JLabel(
                head
        );

        title.setForeground(Color.WHITE);

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        JLabel adminLabel = new JLabel(
                username
        );

        adminLabel.setForeground(Color.WHITE);

        adminLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        16
                )
        );

        add(title, BorderLayout.WEST);

        add(adminLabel, BorderLayout.EAST);
    }
}