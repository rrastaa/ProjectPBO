package Controller;

import DAO.UserDAO;
import Model.User;
import View.LoginView;
import View.ParkirView;

import javax.swing.*;

public class LoginController {

    LoginView view;
    UserDAO dao;

    public LoginController(LoginView view) {
        this.view = view;
        dao = new UserDAO();
        view.btnLogin.addActionListener(e -> login());
    }
    public void login(){
        String username = view.tfUsername.getText();
        String password = String.valueOf(view.pfPassword.getPassword());
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Field Tidak Boleh Kosong!");
            return;
        }
        User user = new User(username,password);
        if (dao.login(user)) {
            JOptionPane.showMessageDialog(null, "Login Berhasil!");
            new ParkirView(username);
            view.dispose();
        }
        else{
            JOptionPane.showMessageDialog(null, "Username / Password Salah!");
        }
    }
}
