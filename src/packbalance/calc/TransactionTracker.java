package packbalance.calc;

import java.util.ArrayList;
import java.util.List;

public class TransactionTracker {
    private double balance;
    private List<Transaction> transactions;

    public TransactionTracker(double initialBalance) {
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
        // Add an initial permanent entry for the current balance
        transactions.add(new Transaction(initialBalance, "Initial Balance"));
    }

    public void addTransaction(double amount, String description) {
        if (amount <= 0) {
            System.out.println("Invalid transaction amount.");
            return;
        }
        balance -= amount;  // Deduct the amount from balance
        transactions.add(new Transaction(balance, description));
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public static class Transaction {
        private double balanceAfterTransaction;
        private String description;

        public Transaction(double balanceAfterTransaction, String description) {
            this.balanceAfterTransaction = balanceAfterTransaction;
            this.description = description;
        }

        public double getBalanceAfterTransaction() {
            return balanceAfterTransaction;
        }

        public String getDescription() {
            return description;
        }
    }
}
