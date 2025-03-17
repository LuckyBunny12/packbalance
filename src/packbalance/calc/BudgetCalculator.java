package packbalance.calc;

public class BudgetCalculator {
    public static double calculateDailyBudget(double balance, int days) {
        if (days > 0) {
            return balance / days;
        } else {
            throw new IllegalArgumentException("Days must be greater than zero.");
        }
    }
}