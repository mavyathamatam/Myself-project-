import java.util.Scanner;

abstract class AbstractBankList {
    abstract void addCustomer(String name, String email);

    abstract void createAccount(String customerName);

    abstract void showAccounts();

    abstract void deposit(String accNo, double amount);

    abstract void withdraw(String accNo, double amount);
}

class BankNode {
    String customerName;
    String email;
    String accountNumber;
    double balance;

    BankNode next;
    BankNode prev;

    BankNode(String name, String email, String accNo) {
        this.customerName = name;
        this.email = email;
        this.accountNumber = accNo;
        this.balance = 0.0;
        this.next = null;
        this.prev = null;
    }
}

class DoublyBankList extends AbstractBankList {
    private BankNode head;

    private boolean customerExists(String name) {
        BankNode temp = head;
        while (temp != null) {
            if (temp.customerName.equalsIgnoreCase(name)) {
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    @Override
    void addCustomer(String name, String email) {
        if (customerExists(name)) {
            System.out.println("Customer already exists.");
            return;
        }

        String accNo = "ACC" + (int) (Math.random() * 9000 + 1000);
        BankNode newNode = new BankNode(name, email, accNo);

        if (head == null) {
            head = newNode;
        } else {
            BankNode temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
            newNode.prev = temp;
        }

        System.out.println("Customer added. Account Number: " + accNo);
    }

    @Override
    void createAccount(String name) {
        if (!customerExists(name)) {
            System.out.println("Customer not found. Please add customer first.");
        } else {
            System.out.println("Customer already has an account.");
        }
    }

    @Override
    void showAccounts() {
        if (head == null) {
            System.out.println("No accounts found.");
            return;
        }
        BankNode temp = head;
        System.out.println("=== Account List ===");
        while (temp != null) {
            System.out.println("→ Name: " + temp.customerName + ", Email: " + temp.email +
                    ", Account No: " + temp.accountNumber + ", Balance: $" + temp.balance);
            temp = temp.next;
        }
    }

    private BankNode findAccount(String accNo) {
        BankNode temp = head;
        while (temp != null) {
            if (temp.accountNumber.equalsIgnoreCase(accNo)) {
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }

    @Override
    void deposit(String accNo, double amount) {
        BankNode account = findAccount(accNo);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }
        if (amount <= 0) {
            System.out.println("Enter a valid amount.");
            return;
        }
        account.balance += amount;
        System.out.println("Deposited $" + amount + " to account " + accNo);
    }

    @Override
    void withdraw(String accNo, double amount) {
        BankNode account = findAccount(accNo);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }
        if (amount <= 0 || amount > account.balance) {
            System.out.println("Invalid amount or insufficient balance.");
            return;
        }
        account.balance -= amount;
        System.out.println("Withdrew $" + amount + " from account " + accNo);
    }
}

public class BankManagerApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AbstractBankList bank = new DoublyBankList();
        int choice;

        do {
            System.out.println("\n=== BANK MANAGER MENU ===");
            System.out.println("1. Add Customer");
            System.out.println("2. Show Accounts");
            System.out.println("3. Deposit Money");
            System.out.println("4. Withdraw Money");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {
                System.out.print("Invalid input. Enter a number: ");
                sc.next();
            }

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter customer name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter email: ");
                    String email = sc.nextLine();
                    bank.addCustomer(name, email);
                    break;

                case 2:
                    bank.showAccounts();
                    break;

                case 3:
                    System.out.print("Enter account number: ");
                    String accNoD = sc.nextLine();
                    System.out.print("Enter amount to deposit: ");
                    double deposit = sc.nextDouble();
                    sc.nextLine();
                    bank.deposit(accNoD, deposit);
                    break;

                case 4:
                    System.out.print("Enter account number: ");
                    String accNoW = sc.nextLine();
                    System.out.print("Enter amount to withdraw: ");
                    double withdraw = sc.nextDouble();
                    sc.nextLine();
                    bank.withdraw(accNoW, withdraw);
                    break;

                case 5:
                    System.out.println("Exiting program. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 5);

        sc.close();
    }
}
