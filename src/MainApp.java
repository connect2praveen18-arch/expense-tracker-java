package util;

import dao.ExpenseDAO;
import dao.UserDAO;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Expense;








public class MainApp extends Application {

    @Override
    public void start(Stage stage) {

        Label title = new Label("Smart Expense Tracker");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");


        Text message = new Text();
        Button loginButton = new Button("Login");

        UserDAO userDAO = new UserDAO();
        loginButton.setOnAction(e -> {

                    String username = usernameField.getText();
                    String password = passwordField.getText();

                    if (username.isEmpty() || password.isEmpty()) {
                        message.setText("Please enter username and password");
                        return;
                    }

                    boolean success = userDAO.login(username, password);

                    if (success) {
                        showDashboard(stage);   // move to next screen
                    } else {
                        message.setText("Invalid username or password");
                    }
                });



        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");
        root.getChildren().addAll(title, usernameField, passwordField, loginButton, message);


        Scene scene = new Scene(root, 400, 300);
        stage.setTitle("Expense Tracker App");
        stage.setScene(scene);
        stage.show();


    }
    private void showDashboard(Stage stage) {


        Label welcome = new Label("Welcome to Smart Expense Tracker");

        Button addExpenseBtn = new Button("Add Expense");
        Button viewExpenseBtn = new Button("View Expenses");
        Button logoutBtn = new Button("Logout");
        addExpenseBtn.setOnAction(e -> showAddExpense(stage));
        viewExpenseBtn.setOnAction(e -> showViewExpenses(stage));


        VBox root = new VBox(15);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");
        root.getChildren().addAll(
                welcome,
                addExpenseBtn,
                viewExpenseBtn,
                logoutBtn


        );

        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);


    }
    private void showAddExpense(Stage stage) {

        Label title = new Label("Add Expense");

        TextField amountField = new TextField();
        amountField.setPromptText("Amount");

        TextField noteField = new TextField();
        noteField.setPromptText("Note");


        Button saveBtn = new Button("Save Expense");
        ExpenseDAO expenseDAO = new ExpenseDAO();

        saveBtn.setOnAction(e -> {

            String amountText = amountField.getText();
            String note = noteField.getText();

            if (amountText.isEmpty()) {
                title.setText("Amount is required!");
                return;
            }

            try {
                double amount = Double.parseDouble(amountText);

                // TEMP values (we’ll improve later)
                int expenseId = (int) (Math.random() * 10000);
                int userId = 1;
                int categoryId = 1;

                expenseDAO.addExpense(expenseId, userId, categoryId, amount, note);

                title.setText("Expense Added Successfully!");

                amountField.clear();
                noteField.clear();

            } catch (NumberFormatException ex) {
                title.setText("Enter valid amount!");
            }
        });

        Button backBtn = new Button("Back");


        // Back button → return to dashboard
        backBtn.setOnAction(e -> showDashboard(stage));



        VBox root = new VBox(15);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");
        root.getChildren().addAll(
                title,
                amountField,
                noteField,
                saveBtn,
                backBtn


        );


        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
    }
    private void showViewExpenses(Stage stage) {


            ExpenseDAO expenseDAO = new ExpenseDAO();

            TableView<Expense> table = new TableView<>();

            TableColumn<Expense, Integer> idCol = new TableColumn<>("ID");
            idCol.setCellValueFactory(new PropertyValueFactory<>("expenseId"));

            TableColumn<Expense, Integer> userCol = new TableColumn<>("User");
            userCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

            TableColumn<Expense, Integer> catCol = new TableColumn<>("Category");
            catCol.setCellValueFactory(new PropertyValueFactory<>("categoryId"));

            TableColumn<Expense, Double> amtCol = new TableColumn<>("Amount");
            amtCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

            TableColumn<Expense, String> noteCol = new TableColumn<>("Note");
            noteCol.setCellValueFactory(new PropertyValueFactory<>("note"));

            table.getColumns().addAll(idCol, userCol, catCol, amtCol, noteCol);
            table.getItems().addAll(expenseDAO.getAllExpenses());

            Button backBtn = new Button("Back");
            Button editBtn = new Button("Edit");
            Button deleteBtn = new Button("Delete");

            editBtn.setOnAction(e -> {
                Expense selected = table.getSelectionModel().getSelectedItem();
                if (selected == null) {
                    showAlert("Please select an expense to edit");
                    return;
                }
                showAlert("Edit screen coming next");
            });

            deleteBtn.setOnAction(e -> {
                Expense selected = table.getSelectionModel().getSelectedItem();
                if (selected == null) {
                    showAlert("Please select an expense to delete");
                    return;
                }
                expenseDAO.deleteExpense(selected.getExpenseId());
                table.getItems().remove(selected);
            });

            backBtn.setOnAction(e -> showDashboard(stage));

            HBox actionBox = new HBox(10, editBtn, deleteBtn);
            actionBox.setStyle("-fx-alignment: center;");

            VBox root = new VBox(15, table, actionBox, backBtn);
            root.setStyle("-fx-padding: 20; -fx-alignment: center;");

            Scene scene = new Scene(root, 600, 400);
            stage.setScene(scene);
        }



        private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);

        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showEditExpense(Stage stage, Expense expense) {

        Label title = new Label("Edit Expense");

        TextField amountField = new TextField(
                String.valueOf(expense.getAmount())
        );

        TextField noteField = new TextField(expense.getNote());

        Button saveBtn = new Button("Update");
        Button backBtn = new Button("Back");

        ExpenseDAO expenseDAO = new ExpenseDAO();

        saveBtn.setOnAction(e -> {
            double amount = Double.parseDouble(amountField.getText());
            String note = noteField.getText();

            expenseDAO.updateExpense(
                    expense.getExpenseId(),
                    amount,
                    note
            );

            showAlert("Expense updated successfully");
            showViewExpenses(stage);
        });

        backBtn.setOnAction(e -> showViewExpenses(stage));

        VBox root = new VBox(10, title, amountField, noteField, saveBtn, backBtn);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        stage.setScene(new Scene(root, 400, 300));
    }


    public static void main(String[] args) {
        launch(args);
    }
}