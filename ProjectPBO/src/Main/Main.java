package Main;

import Connection.DBConnection;
import Controller.LoginController;
import View.ParkirView;
import View.LoginView;
import View.KendaraanMasukView;

public class Main {

    
    public static void main(String[] args) {
        DBConnection.Connect();
        LoginView view = new LoginView();
        new LoginController(view);
//          new KendaraanMasukView("Admin");
    }
    
}
