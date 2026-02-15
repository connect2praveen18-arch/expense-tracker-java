package dao;
import util.DBConnection;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.*;
import model.Expense;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class ExpenseDAO {
    public void addExpense(int expenseId, int userId, int categoryId,
                           double amount, String note) {

        try (Connection con = DBConnection.getConnection()) {

            CallableStatement cs = con.prepareCall("{ call add_expense(?, ?, ?, ?, ?) }");

            cs.setInt(1, expenseId);
            cs.setInt(2, userId);
            cs.setInt(3, categoryId);
            cs.setDouble(4, amount);
            cs.setString(5, note);

            cs.execute();

            System.out.println("Expense Added Successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void updateExpense(int expenseId, double amount, String note) {

        String sql = "UPDATE expenses SET amount = ?, note = ? WHERE expense_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, amount);
            ps.setString(2, note);
            ps.setInt(3, expenseId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void viewExpenses() {

        try (Connection con = DBConnection.getConnection()) {

            String sql = "SELECT expense_id, user_id, category_id, amount, note FROM expenses";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            System.out.println("\nID | USER | CAT | AMOUNT | NOTE");
            System.out.println("----------------------------------");

            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println(
                        rs.getInt("expense_id") + " | " +
                                rs.getInt("user_id") + " | " +
                                rs.getInt("category_id") + " | " +
                                rs.getDouble("amount") + " | " +
                                rs.getString("note")
                );
            }

            if (!found) {
                System.out.println("No expenses found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Expense> getAllExpenses() {

        List<Expense> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {

            String sql = "SELECT expense_id, user_id, category_id, amount, note FROM expenses";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                list.add(new Expense(
                        rs.getInt("expense_id"),
                        rs.getInt("user_id"),
                        rs.getInt("category_id"),
                        rs.getDouble("amount"),
                        rs.getString("note")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void deleteExpense(int expenseId) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "DELETE FROM expenses WHERE expense_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, expenseId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}


