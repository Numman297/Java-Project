import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List; 

public class Pin extends JFrame implements ActionListener {
    JButton b1, b2;
    JPasswordField p1, p2;
    JTextField cardNumberField;
    String pin;

    Pin(String pin) {
        this.pin = pin;

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/atm2.png"));
        Image i2 = i1.getImage().getScaledInstance(1550, 830, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0, 0, 1550, 830);
        add(l3);

        JLabel label1 = new JLabel("CHANGE YOUR PIN");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("System", Font.BOLD, 16));
        label1.setBounds(430, 180, 400, 35);
        l3.add(label1);

        JLabel cardLabel = new JLabel("Card Number: ");
        cardLabel.setForeground(Color.WHITE);
        cardLabel.setFont(new Font("System", Font.BOLD, 16));
        cardLabel.setBounds(430, 220, 150, 35);
        l3.add(cardLabel);

        cardNumberField = new JTextField();
        cardNumberField.setBackground(new Color(65, 125, 128));
        cardNumberField.setForeground(Color.WHITE);
        cardNumberField.setBounds(600, 220, 180, 25);
        cardNumberField.setFont(new Font("Raleway", Font.BOLD, 22));
        l3.add(cardNumberField);

        JLabel label2 = new JLabel("New PIN: ");
        label2.setForeground(Color.WHITE);
        label2.setFont(new Font("System", Font.BOLD, 16));
        label2.setBounds(430, 260, 150, 35);
        l3.add(label2);

        p1 = new JPasswordField();
        p1.setBackground(new Color(65, 125, 128));
        p1.setForeground(Color.WHITE);
        p1.setBounds(600, 260, 180, 25);
        p1.setFont(new Font("Raleway", Font.BOLD, 22));
        l3.add(p1);

        JLabel label3 = new JLabel("Re-Enter New PIN: ");
        label3.setForeground(Color.WHITE);
        label3.setFont(new Font("System", Font.BOLD, 16));
        label3.setBounds(430, 300, 400, 35);
        l3.add(label3);

        p2 = new JPasswordField();
        p2.setBackground(new Color(65, 125, 128));
        p2.setForeground(Color.WHITE);
        p2.setBounds(600, 305, 180, 25);
        p2.setFont(new Font("Raleway", Font.BOLD, 22));
        l3.add(p2);

        b1 = new JButton("CHANGE");
        b1.setBounds(700, 362, 150, 35);
        b1.setBackground(new Color(65, 125, 128));
        b1.setForeground(Color.WHITE);
        b1.addActionListener(this);
        l3.add(b1);

        b2 = new JButton("BACK");
        b2.setBounds(700, 406, 150, 35);
        b2.setBackground(new Color(65, 125, 128));
        b2.setForeground(Color.WHITE);
        b2.addActionListener(this);
        l3.add(b2);

        String autoCard = getCardNumberByPin(pin);
        if (!autoCard.isEmpty()) {
            cardNumberField.setText(autoCard);
            cardNumberField.setEditable(false);
        }

        setSize(1550, 830);
        setLayout(null);
        setLocation(0, 0);
        setVisible(true);
    }

    private String getCardNumberByPin(String pin) {
        if (pin == null || pin.trim().isEmpty()) {
            return "";
        }
        File accountsFile = new File("text/accounts.txt");
        if (!accountsFile.exists()) {
            return "";
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(accountsFile))) {
            String line;
            String currentCard = "";
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Card Number: ")) {
                    currentCard = line.substring(13).trim();
                } else if (line.startsWith("PIN: ")) {
                    String currentPin = line.substring(5).trim();
                    if (currentPin.equals(pin.trim())) {
                        return currentCard;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void actionPerformed(ActionEvent e) {
        try {
            String pin1 = new String(p1.getPassword()).trim();
            String pin2 = new String(p2.getPassword()).trim();
            String cardNumber = cardNumberField.getText().trim();

            if (e.getSource() == b2) { 
                new main_Class(pin);
                setVisible(false);
                return;
            }
            if (cardNumber.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Card number cannot be empty.");
                return;
            }

            if (pin1.isEmpty()) {
                JOptionPane.showMessageDialog(null, "PIN cannot be empty.");
                return;
            }

            if (!pin1.matches("^\\d{4}$")) {
                JOptionPane.showMessageDialog(null, "PIN must be exactly 4 digits (0-9).");
                return;
            }

            if (!pin1.equals(pin2)) {
                JOptionPane.showMessageDialog(null, "Entered PIN does not match.");
                return;
            }

            if (e.getSource() == b1) {
                boolean success = updatePinInFiles(cardNumber, pin1);
                if (success) {
                    this.pin = pin1;
                    JOptionPane.showMessageDialog(null, "PIN updated successfully.");
                    setVisible(false);
                    new main_Class(pin1);
                }
            } 

        } catch (Exception ex) {
            System.out.println("An error occurred: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private boolean updatePinInFiles(String cardNumber, String newPin) {
        String accountsPath = "text/accounts.txt";
        String transactionsPath = "text/transactions.txt";
        List<String> fileContents = new ArrayList<>();
        boolean cardFound = false;
        boolean insideAccountSection = false;
        String cardOldPin = "";

        File accountsFile = new File(accountsPath);
        if (!accountsFile.exists()) {
            JOptionPane.showMessageDialog(null, "Accounts file not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // 1. Read and update text/accounts.txt
        try (BufferedReader reader = new BufferedReader(new FileReader(accountsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Card Number: ")) {
                    String cardInFile = line.substring(13).trim();
                    if (cardInFile.equals(cardNumber)) {
                        insideAccountSection = true;
                        cardFound = true;
                    } else {
                        insideAccountSection = false;
                    }
                    fileContents.add(line);
                } else if (insideAccountSection && line.startsWith("PIN: ")) {
                    cardOldPin = line.substring(5).trim();
                    fileContents.add("PIN: " + newPin);
                    insideAccountSection = false;
                } else {
                    fileContents.add(line);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading account data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!cardFound) {
            JOptionPane.showMessageDialog(null, "Card number not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(accountsFile))) {
            for (String fileLine : fileContents) {
                writer.write(fileLine);
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving account data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // 2. Read and update text/transactions.txt with new PIN so balance & history stay intact
        File transactionsFile = new File(transactionsPath);
        if (transactionsFile.exists() && !cardOldPin.isEmpty()) {
            List<String> transContents = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(transactionsFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty()) {
                        continue;
                    }
                    String[] parts = line.split(",", 2);
                    if (parts.length == 2 && parts[0].trim().equals(cardOldPin)) {
                        transContents.add(newPin + ", " + parts[1].trim());
                    } else {
                        transContents.add(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(transactionsFile))) {
                for (String transLine : transContents) {
                    writer.write(transLine);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    public static void main(String[] args) {
        new Pin("");
    }
}
