package model;

public class Expense {
    private int expenseId;
    private int userId;
    private int categoryId;
    private double amount;
    private String note;

    public Expense(int expenseId, int userId, int categoryId, double amount, String note) {
        this.expenseId = expenseId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.note = note;
    }

    public int getExpenseId() { return expenseId; }
    public int getUserId() { return userId; }
    public int getCategoryId() { return categoryId; }
    public double getAmount() { return amount; }
    public String getNote() { return note; }
}
