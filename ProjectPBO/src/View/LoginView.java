package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;


public class LoginView extends JFrame {
    public JTextField tfUsername;
    public JPasswordField pfPassword;
    public JButton btnLogin;
    
    public LoginView(){
        setTitle("Login");
        setSize(350,250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5,1,10,10));
        panel.setBorder(new EmptyBorder(20,20,20,20));
        
        panel.add(new JLabel("Username"));
        tfUsername = new JTextField();
        panel.add(tfUsername);
        
        panel.add(new JLabel("Password"));
        pfPassword = new JPasswordField();
        panel.add(pfPassword);
        
        btnLogin = new JButton("Log In");
        panel.add(btnLogin);
        add(panel);
        
        setVisible(true);
    }
    
}
