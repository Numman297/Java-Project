import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Login extends JFrame implements ActionListener {
    JLabel label1, label2, label3;
    JTextField textField2;
    JPasswordField passwordField3;

    JButton button1,button2,button3,exitBtn;
    Login(){
        super("Bank Management System");
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/bank.png"));
        Image i2 = i1.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(350,10,100,100);
        add(image);

        ImageIcon ii1 = new ImageIcon(ClassLoader.getSystemResource("icon/card.png"));
        Image ii2 = ii1.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT);
        ImageIcon ii3 = new ImageIcon(ii2);
        JLabel iimage = new JLabel(ii3);
        iimage.setBounds(630,350,100,100);
        add(iimage);

        label1 = new JLabel("WELCOME TO BANK");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("AvantGarde", Font.BOLD, 38));
        label1.setBounds(230,125,450,40);
        add(label1);

        label2 = new JLabel("Card No:");
        label2.setFont(new Font("Ralway", Font.BOLD, 28));
        label2.setForeground(Color.WHITE);
        label2.setBounds(150,190,375,30);
        add(label2);

        textField2 = new JTextField(15);
        textField2.setBounds(325,190,230,30);
        textField2.setFont(new Font("Arial", Font.BOLD,14));
        add(textField2);

        label3 = new JLabel("PIN: ");
        label3.setFont(new Font("Ralway", Font.BOLD, 28));
        label3.setForeground(Color.WHITE);
        label3.setBounds(150,250,375,30);
        add(label3);

        passwordField3 = new JPasswordField(15);
        passwordField3.setBounds(325,250,230,30);
        passwordField3.setFont(new Font("Arial", Font.BOLD, 14));
        add(passwordField3);

        button1 = new JButton("SIGN IN");
        button1.setFont(new Font("Arial", Font.BOLD, 14));
        button1.setForeground(Color.WHITE);
        button1.setBackground(Color.BLACK);
        button1.setBounds(325,300,100, 30);
        button1.addActionListener(this);
        add(button1);

        button2 = new JButton("CLEAR");
        button2.setFont(new Font("Arial", Font.BOLD, 14));
        button2.setForeground(Color.WHITE);
        button2.setBackground(Color.BLACK);
        button2.setBounds(455,300,100, 30);
        button2.addActionListener(this);
        add(button2);

        button3 = new JButton("SIGN UP");
        button3.setFont(new Font("Arial", Font.BOLD, 14));
        button3.setForeground(Color.WHITE);
        button3.setBackground(Color.BLACK);
        button3.setBounds(325,350,230, 30);
        button3.addActionListener(this);
        add(button3);
		
		exitBtn = new JButton("Exit");
		exitBtn.setFont(new Font("Arial", Font.BOLD, 14));
		exitBtn.setForeground(Color.WHITE);
        exitBtn.setBackground(Color.BLACK);
	    exitBtn.setBounds(325, 400, 80, 30);
        exitBtn.addActionListener(this);
        add(exitBtn);

        ImageIcon iii1 = new ImageIcon(ClassLoader.getSystemResource("icon/backbg.png"));
        Image iii2 = iii1.getImage().getScaledInstance(850,480,Image.SCALE_DEFAULT);
        ImageIcon iii3 = new ImageIcon(iii2);
        JLabel iiimage = new JLabel(iii3);
        iiimage.setBounds(0,0,850,480);
        add(iiimage);


        setLayout(null);
        setSize(850,480);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
    }
	public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1) {
            String cardno = textField2.getText().trim();
            String pin = new String(passwordField3.getPassword()).trim();

            if (cardno.isEmpty() || pin.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter both Card Number and PIN", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (validateLogin(cardno, pin)) {
                JOptionPane.showMessageDialog(null, "Login Successful!");
                setVisible(false);
                new main_Class(pin);
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect Card Number or PIN", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == button2) {
            textField2.setText("");
            passwordField3.setText("");
        } else if (e.getSource() == button3){
                new Signup();
                setVisible(false);
            
        } else if (e.getSource() == exitBtn) {
            System.exit(0);
        }
    }

    private boolean validateLogin(String cardno, String pin) {
    File file = new File("text/accounts.txt");
    if (!file.exists()) {
        return false;
    }
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String line;
        String storedCardNo = null;
        String storedPin = null;

        while ((line = br.readLine()) != null) {
            if (line.startsWith("Card Number: ")) {
                storedCardNo = line.substring(13).trim(); 
            } else if (line.startsWith("PIN: ")) {
                storedPin = line.substring(5).trim(); 

                if (storedCardNo != null && storedCardNo.equals(cardno) &&
                    storedPin != null && storedPin.equals(pin)) {
                    return true;
                }
            } else if (line.startsWith("===")) {
                storedCardNo = null;
                storedPin = null;
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Error reading account data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    return false;
}


    public static void main(String[] args) {
        new Login();
}}