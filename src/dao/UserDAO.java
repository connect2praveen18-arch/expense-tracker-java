package dao;


import util.DBConnection;
import java.sql.*;

public class UserDAO {
    public boolean login(String username, String password) {
        boolean isValid = false;

        try (Connection con = DBConnection.getConnection()) {

            CallableStatement cs = con.prepareCall("{ call login_user_proc(?, ?, ?) }");


            cs.setString(1, username);
            cs.setString(2, password);
            cs.registerOutParameter(3, Types.INTEGER);

            cs.execute();

            int result = cs.getInt(3);
            isValid = (result > 0);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return isValid;
    }
}
