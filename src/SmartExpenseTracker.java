



import dao.UserDAO;
import dao.ExpenseDAO;
import java.util.Scanner;

public class SmartExpenseTracker {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();
        ExpenseDAO expenseDAO = new ExpenseDAO();

        System.out.println("===== SMART EXPENSE TRACKER =====");

        // LOGIN
        System.out.print("Enter username: ");
        String username = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        if (!userDAO.login(username, password)) {
            System.out.println("Invalid username or password");
            return;
        }

        System.out.println("Login Successful");

        int choice;

        // MENU LOOP
        do {
            System.out.println("\n---- MENU ----");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter Expense ID: ");
                    int expenseId = sc.nextInt();

                    System.out.print("Enter User ID: ");
                    int userId = sc.nextInt();

                    System.out.print("Enter Category ID: ");
                    int categoryId = sc.nextInt();

                    System.out.print("Enter Amount: ");
                    double amount = sc.nextDouble();
                    sc.nextLine(); // consume newline

                    System.out.print("Enter Note: ");
                    String note = sc.nextLine();

                    expenseDAO.addExpense(
                            expenseId,
                            userId,
                            categoryId,
                            amount,
                            note
                    );
                    break;

                case 2:
                    expenseDAO.viewExpenses();
                    break;

                case 3:
                    System.out.println("Thank you! Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 3);

        sc.close();
    }
}





