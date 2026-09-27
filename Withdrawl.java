import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Date;

public class Withdrawl extends JFrame implements ActionListener {
    String pin;
    JTextField textField;
    JButton b1, b2;

    public Withdrawl(String pin) {
        this.pin = pin;
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/atm2.png"));
        Image i2 = i1.getImage().getScaledInstance(1550, 830, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0, 0, 1550, 830);
        add(l3);

        JLabel label1 = new JLabel("MAXIMUM WITHDRAWAL IS TK.10,000");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("System", Font.BOLD, 16));
        label1.setBounds(460, 180, 700, 35);
        l3.add(label1);

        JLabel label2 = new JLabel("PLEASE ENTER YOUR AMOUNT");
        label2.setForeground(Color.WHITE);
        label2.setFont(new Font("System", Font.BOLD, 16));
        label2.setBounds(460, 220, 400, 35);
        l3.add(label2);

        textField = new JTextField();
        textField.setBackground(new Color(65, 125, 128));
        textField.setForeground(Color.WHITE);
        textField.setBounds(460, 260, 320, 28);
        textField.setFont(new Font("Raleway", Font.BOLD, 22));
        l3.add(textField);

        b1 = new JButton("WITHDRAW");
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

        setLayout(null);
        setSize(1550, 830);
        setLocation(0, 0);
        setVisible(true);
    }

    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            try {
                String amountText = textField.getText().trim();
                if (amountText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter the Amount you want to withdraw");
                    return;
                }

                int amount = Integer.parseInt(amountText);
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(null, "Please enter an amount greater than 0");
                    return;
                }
                if (amount > 10000) {
                    JOptionPane.showMessageDialog(null, "Maximum withdrawal limit is Tk.10,000");
                    return;
                }

                int balance = calculateBalance();
                if (balance < amount) {
                    JOptionPane.showMessageDialog(null, "Insufficient Balance. Current Balance: Tk." + balance);
                    return;
                }

                updateTransaction(amount);
                JOptionPane.showMessageDialog(null, "Tk. " + amount + " Debited Successfully");
                setVisible(false);
                new main_Class(pin);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid numeric amount");
            }
        } else if (e.getSource() == b2) {
            setVisible(false);
            new main_Class(pin);
        }
    }

private int calculateBalance() {
    int balance = 0;
    File file = new File("text/transactions.txt");
    if (!file.exists()) {
        return 0;
    }
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            try {
                String[] parts = line.split(",\\s*"); 
                if (parts.length != 4) continue; 
                String filePin = parts[0].trim();
                String type = parts[2].trim();
                int amount = Integer.parseInt(parts[3].trim());

                if (filePin.equals(pin)) {
                    if (type.equalsIgnoreCase("Deposit")) {
                        balance += amount;
                    } else if (type.equalsIgnoreCase("Withdraw")) {
                        balance -= amount;
                    }
                }
            } catch (Exception ex) {
                System.out.println("Skipping malformed line: " + line);
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return balance;
}




   private void updateTransaction(int amount) {
    try {
        File file = new File("text/transactions.txt");
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(pin + ", " + new Date() + ", Withdraw, " + amount + "\n");
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    public static void main(String[] args) {
        new Withdrawl("");
    }
}

