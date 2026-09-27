# Bank Management System (Java ATM)

A simple Bank Management and ATM simulation project made with Java Swing and AWT for my university course.

The application allows users to create a new bank account, log in using their card number and PIN, and perform regular ATM operations like depositing money, withdrawing cash, fast cash withdrawal, checking balance, and changing the PIN. All data is stored locally in text files (`text/` directory).

## Features

- **Account Registration:** 
  - Personal details form (Name, Email, Address, etc.)
  - Account configuration (Savings, Current, Fixed Deposit, etc.)
  - Services selection (ATM Card, Mobile Banking, Cheque Book, etc.)
  - Automatic 16-digit Card Number and 4-digit PIN generation
- **Login:** Secure sign-in with Card Number and PIN.
- **Deposit:** Credit money into the account with input validation.
- **Cash Withdrawal:** Withdraw cash (up to Tk. 10,000 per transaction) with real-time balance check.
- **Fast Cash:** Quick withdrawal shortcuts (100, 500, 1000, 2000, 5000, 10000).
- **Balance Enquiry:** Displays the current available balance.
- **PIN Change:** Update account PIN securely.

## Project Structure

- `Start.java`: Main entry point to launch the application.
- `Login.java`: Login window with Card No and PIN fields.
- `Signup.java`: Step 1 registration form for personal details.
- `Signup3.java`: Step 2 form for account type and card details.
- `main_Class.java`: ATM dashboard menu after login.
- `Deposit.java`: Screen to deposit money.
- `Withdrawl.java`: Screen to withdraw custom amount.
- `FastCash.java`: Quick cash withdrawal options.
- `BalanceEnquriy.java`: Screen to check account balance.
- `Pin.java`: Screen to change the account PIN.
- `icon/`: Contains icons and ATM UI background graphics.
- `text/`: Text files used to store user accounts and transaction history.

## How to Run

### Prerequisites
- Java Development Kit (JDK 8 or higher) installed.

### Steps

1. Clone the repository:
```bash
git clone https://github.com/Numman297/Java-Project.git
cd Java-Project
```

2. Compile all Java files:
```bash
javac *.java
```

3. Run the application:
```bash
java Start
```

## Author
MD Numman
