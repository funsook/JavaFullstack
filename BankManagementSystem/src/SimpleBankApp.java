import java.util.HashMap;
import java.util.Scanner;

public class SimpleBankApp {
    static class BankAccount {
        String accountNo;
        double balance = 0;
        double fdAmount = 0;

        BankAccount(String accountNo) {
            this.accountNo = accountNo;
        }

        void deposit(double amount) {
            balance += amount;
        }

        // Only this method is synchronized
        synchronized boolean withdraw(double amount) {
            if (balance >= amount) {
                balance -= amount;
                return true;
            }
            return false;
        }

        void createFD(double amount) {
            if (balance >= amount) {
                balance -= amount;
                fdAmount += amount;
            }
        }

        double getBalance() {
            return balance;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HashMap<String, BankAccount> accounts = new HashMap<>();

        while (true) {
            System.out.println("\n--- PNB Bank Menu ---");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Check Balance");
            System.out.println("5. Create Fixed Deposit");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            if (choice == 6) {
                System.out.println("Thank you for using PNB Bank!");
                break;
            }

            System.out.print("Enter Account Number: ");
            String accNo = sc.next();
            BankAccount account = accounts.get(accNo);

            if (choice == 1) {
                if (accounts.containsKey(accNo)) {
                    System.out.println("Account already exists.");
                } else {
                    accounts.put(accNo, new BankAccount(accNo));
                    System.out.println("Account created successfully!");
                }
            } else if (account == null) {
                System.out.println("Account not found. Please create an account first.");
            } else {
                switch (choice) {
                    case 2:
                        System.out.print("Enter deposit amount: ");
                        account.deposit(sc.nextDouble());
                        System.out.println("Deposit successful.");
                        break;
                    case 3:
                        System.out.print("Enter withdrawal amount: ");
                        if (account.withdraw(sc.nextDouble())) {
                            System.out.println("Withdrawal successful.");
                        } else {
                            System.out.println("Insufficient balance.");
                        }
                        break;
                    case 4:
                        System.out.println("Available Balance: " + account.getBalance());
                        break;
                    case 5:
                        System.out.print("Enter FD amount: ");
                        account.createFD(sc.nextDouble());
                        System.out.println("Fixed Deposit created.");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            }
        }

        sc.close();
    }
}
