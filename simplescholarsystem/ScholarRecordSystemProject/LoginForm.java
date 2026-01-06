import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginForm extends JFrame implements ActionListener {

    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton, clearButton;

    public LoginForm() {
       
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ignored) {
        }

        setTitle("Login - Scholar Record System");
        setSize(450, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout()); 

        Color primaryColor = new Color(38, 166, 154);
        Color backgroundColor = new Color(250, 250, 250);
        Font labelFont = new Font("Segos UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segos UI", Font.PLAIN, 14);

       
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        add(mainPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

       
        JLabel titleLabel = new JLabel("🎓 System Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segos UI", Font.BOLD, 30));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 5, 20, 5); 
        mainPanel.add(titleLabel, gbc);
        
        
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.gridwidth = 1;

        
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(userLabel, gbc);

        usernameField = new JTextField();
        usernameField.setFont(fieldFont);
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(usernameField, gbc);

       
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(passLabel, gbc);

        passwordField = new JPasswordField();
        passwordField.setFont(fieldFont);
        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(passwordField, gbc);

       
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(backgroundColor);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 5, 0, 5); 
        mainPanel.add(buttonPanel, gbc);

       
        loginButton = new JButton("Login");
        loginButton.setFont(labelFont);
        loginButton.setBackground(primaryColor);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        buttonPanel.add(loginButton);

        
        clearButton = new JButton("Clear");
        clearButton.setFont(labelFont);
        clearButton.setBackground(new Color(96, 125, 139));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        buttonPanel.add(clearButton);

        
        loginButton.addActionListener(this);
        clearButton.addActionListener(this);
        
       
        getContentPane().setBackground(backgroundColor);
        setVisible(true);
        
        }

    @Override
    public void actionPerformed(ActionEvent e) {    
        if (e.getSource() == loginButton) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.equals("kyross") && password.equals("1234")) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                dispose();
                new ScholarRecordSystem().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else if (e.getSource() == clearButton) {
            usernameField.setText("");
            passwordField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm());
    }
}