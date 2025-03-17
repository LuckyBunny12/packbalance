package packbalance.ui;

import javax.swing.*;
import packbalance.calc.BudgetCalculator;
import packbalance.calc.TransactionTracker;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BudgetUI {
    private JFrame frame;
    private JTextField balanceField;
    private JTextField daysField;
    private JTextField amountField;
    private JTextField descriptionField;
    private JLabel resultLabel;
    private JTextArea transactionHistoryArea;
    private TransactionTracker transactionTracker;

    public BudgetUI() {
        frame = new JFrame("Dining Dollars Budgeter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 350);
        frame.setLayout(new GridLayout(7, 2, 10, 10));

        frame.add(new JLabel("Current Balance ($):"));
        balanceField = new JTextField();
        frame.add(balanceField);

        frame.add(new JLabel("Days Remaining:"));
        daysField = new JTextField();
        frame.add(daysField);

        JButton calculateButton = new JButton("Calculate");
        frame.add(calculateButton);

        resultLabel = new JLabel("Daily Budget: ", SwingConstants.CENTER);
        frame.add(resultLabel);

        frame.add(new JLabel("Transaction Amount ($):"));
        amountField = new JTextField();
        frame.add(amountField);

        frame.add(new JLabel("Transaction Description:"));
        descriptionField = new JTextField();
        frame.add(descriptionField);

        JButton addTransactionButton = new JButton("Add Transaction");
        frame.add(addTransactionButton);

        JButton signOutButton = new JButton("Sign Out");
        frame.add(signOutButton);

        frame.add(new JLabel("Transaction History:"));
        transactionHistoryArea = new JTextArea(5, 20);
        transactionHistoryArea.setEditable(false);
        frame.add(new JScrollPane(transactionHistoryArea));

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateBudget();
            }
        });

        addTransactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTransaction();
                updateTransactionHistory();
            }
        });

        signOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new LoginUI();
            }
        });

        transactionTracker = new TransactionTracker(0.0); // Initialize with a default balance of 0.0
        updateTransactionHistory();
        frame.setVisible(true);
    }

    private void calculateBudget() {
        try {
            double balance = Double.parseDouble(balanceField.getText());
            int days = Integer.parseInt(daysField.getText());

            double dailyBudget = BudgetCalculator.calculateDailyBudget(balance, days);
            resultLabel.setText(String.format("Daily Budget: $%.2f", dailyBudget));

            // Update transaction tracker balance
            transactionTracker = new TransactionTracker(balance);
            updateTransactionHistory();
        } catch (NumberFormatException e) {
            resultLabel.setText("Invalid input. Enter numbers only.");
        }
    }

    private void addTransaction() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            String description = descriptionField.getText();

            if (!description.isEmpty()) {
                transactionTracker.addTransaction(amount, description);
                resultLabel.setText(String.format("Transaction Added! New Balance: $%.2f", transactionTracker.getBalance()));
            } else {
                resultLabel.setText("Please provide a description.");
            }
        } catch (NumberFormatException e) {
            resultLabel.setText("Invalid amount. Please enter a valid number.");
        }
    }

    private void updateTransactionHistory() {
        StringBuilder historyText = new StringBuilder();
        for (TransactionTracker.Transaction transaction : transactionTracker.getTransactions()) {
            historyText.append(String.format("Balance: $%.2f - %s\n", transaction.getBalanceAfterTransaction(), transaction.getDescription()));
        }
        transactionHistoryArea.setText(historyText.toString());
    }
}
