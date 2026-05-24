package DAO;

import Connection.DBConnection;
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
    Connection conn = DBConnection.Connect();
    public boolean login(User user){
        try{
            String query = "SELECT * FROM admin WHERE username=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
