
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.io.*;

public class Deposit extends JFrame implements ActionListener {
    String pin;
    JTextField textField;
    JButton b1, b2;
    
    Deposit(String pin) {
        this.pin = pin;

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/atm2.png"));
        Image i2 = i1.getImage().getScaledInstance(1550, 830, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0, 0, 1550, 830);
        add(l3);

        JLabel label1 = new JLabel("ENTER AMOUNT YOU WANT TO DEPOSIT");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("System", Font.BOLD, 16));
        label1.setBounds(460, 180, 400, 35);
        l3.add(label1);

        textField = new JTextField();
        textField.setBackground(new Color(65, 125, 128));
        textField.setForeground(Color.WHITE);
        textField.setBounds(460, 230, 320, 28);
        textField.setFont(new Font("Raleway", Font.BOLD, 22));
        l3.add(textField);

        b1 = new JButton("DEPOSIT");
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
        try {
            if (e.getSource() == b1) {
                String amountStr = textField.getText().trim();
                if (amountStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter the Amount you want to Deposit");
                    return;
                }

                int amount;
                try {
                    amount = Integer.parseInt(amountStr);
                    if (amount <= 0) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid amount greater than 0");
                        return;
                    }
                    if (amount > 1000000) {
                        JOptionPane.showMessageDialog(null, "Deposit amount exceeds maximum allowed per transaction (Tk. 1,000,000)");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid numeric amount");
                    return;
                }

                Date date = new Date();
                saveTransaction(pin, date, "Deposit", String.valueOf(amount));
                JOptionPane.showMessageDialog(null, "Tk. " + amount + " Deposited Successfully");
                setVisible(false);
                new main_Class(pin);

            } else if (e.getSource() == b2) {
                setVisible(false);
                new main_Class(pin);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

   private void saveTransaction(String pin, Date date, String type, String amount) {
    try {
        File file = new File("text/transactions.txt");  
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
		
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(pin + ", " + date + ", " + type + ", " + amount + "\n");
        }
    } catch (IOException ex) {
        ex.printStackTrace();
    }
}

    public static void main(String[] args) {
        new Deposit("");
    }
}
