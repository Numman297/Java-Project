import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class FastCash extends JFrame implements ActionListener {

    JButton b1, b2, b3, b4, b5, b6, b7;
    String pin;

    FastCash(String pin) {
        this.pin = pin;

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/atm2.png"));
        Image i2 = i1.getImage().getScaledInstance(1550, 830, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0, 0, 1550, 830);
        add(l3);

        JLabel label = new JLabel("SELECT WITHDRAWAL AMOUNT");
        label.setBounds(445, 180, 700, 35);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("System", Font.BOLD, 23));
        l3.add(label);

        b1 = new JButton("Tk. 100");
        b1.setForeground(Color.WHITE);
        b1.setBackground(new Color(65, 125, 128));
        b1.setBounds(410, 274, 150, 35);
        b1.addActionListener(this);
        l3.add(b1);

        b2 = new JButton("Tk. 500");
        b2.setForeground(Color.WHITE);
        b2.setBackground(new Color(65, 125, 128));
        b2.setBounds(700, 274, 150, 35);
        b2.addActionListener(this);
        l3.add(b2);

        b3 = new JButton("Tk. 1000");
        b3.setForeground(Color.WHITE);
        b3.setBackground(new Color(65, 125, 128));
        b3.setBounds(410, 318, 150, 35);
        b3.addActionListener(this);
        l3.add(b3);

        b4 = new JButton("Tk. 2000");
        b4.setForeground(Color.WHITE);
        b4.setBackground(new Color(65, 125, 128));
        b4.setBounds(700, 318, 150, 35);
        b4.addActionListener(this);
        l3.add(b4);

        b5 = new JButton("Tk. 5000");
        b5.setForeground(Color.WHITE);
        b5.setBackground(new Color(65, 125, 128));
        b5.setBounds(410, 362, 150, 35);
        b5.addActionListener(this);
        l3.add(b5);

        b6 = new JButton("Tk. 10000");
        b6.setForeground(Color.WHITE);
        b6.setBackground(new Color(65, 125, 128));
        b6.setBounds(700, 362, 150, 35);
        b6.addActionListener(this);
        l3.add(b6);

        b7 = new JButton("BACK");
        b7.setForeground(Color.WHITE);
        b7.setBackground(new Color(65, 125, 128));
        b7.setBounds(700, 406, 150, 35);
        b7.addActionListener(this);
        l3.add(b7);

        setLayout(null);
        setSize(1550, 830);
        setLocation(0, 0);
        setVisible(true);
    }

    private int calculateBalance() {
        int balance = 0;
        File file = new File("text/transactions.txt");
        if (!file.exists()) {
            return 0;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] transaction = line.split(",\\s*");
                if (transaction.length == 4 && transaction[0].equals(pin)) {
                    String type = transaction[2].trim();
                    int amount = Integer.parseInt(transaction[3].trim());

                    if (type.equalsIgnoreCase("Deposit")) {
                        balance += amount;
                    } else if (type.equalsIgnoreCase("Withdraw")) {
                        balance -= amount;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return balance;
    }

    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b7) {  
            setVisible(false);
            new main_Class(pin);  
        } else {  
            String amount = ((JButton) e.getSource()).getText().replaceAll("[^0-9]", "");  
            Date date = new Date();

            try {
                int balance = calculateBalance(); 
                int withdrawAmount = Integer.parseInt(amount);

                if (balance < withdrawAmount) {
                    JOptionPane.showMessageDialog(null, "Insufficient Balance. Current Balance: Tk." + balance);
                    return;  
                }

                File file = new File("text/transactions.txt");
                if (file.getParentFile() != null && !file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }

                try (FileWriter writer = new FileWriter(file, true)) {
                    writer.write(pin + ", " + date + ", Withdraw, " + amount + "\n");
                }

                JOptionPane.showMessageDialog(null, "Tk. " + amount + " Debited Successfully");
                setVisible(false);
                new main_Class(pin);  

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new FastCash("");  
    }
}
