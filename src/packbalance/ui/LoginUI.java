package packbalance.ui;

import javax.swing.*;

import packbalance.auth.UserAuth;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUI {
    private JFrame frame;
    private JTextField userIdField;
    private JPasswordField passwordField;
    private JLabel messageLabel;

    public LoginUI() {
        frame = new JFrame("Login / Signup");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new GridLayout(4, 2, 10, 10));

        frame.add(new JLabel("User ID:"));
        userIdField = new JTextField();
        frame.add(userIdField);
        
        frame.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        frame.add(passwordField);
        
        JButton loginButton = new JButton("Login");
        JButton signupButton = new JButton("Sign Up");
        frame.add(loginButton);
        frame.add(signupButton);
        
        messageLabel = new JLabel("", SwingConstants.CENTER);
        frame.add(messageLabel);
        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSignup();
            }
        });
        
        frame.setVisible(true);
    }
    
    private void handleLogin() {
        String userId = userIdField.getText();
        String password = new String(passwordField.getPassword());
        if (UserAuth.verifyUser(userId, password)) {
            frame.dispose();
            new BudgetUI();
        } else {
            messageLabel.setText("Invalid credentials.");
        }
    }
    
    private void handleSignup() {
        String userId = userIdField.getText();
        String password = new String(passwordField.getPassword());
        if (UserAuth.registerUser(userId, password)) {
            messageLabel.setText("Signup successful. Please log in.");
        } else {
            messageLabel.setText("User already exists.");
        }
    }
}
