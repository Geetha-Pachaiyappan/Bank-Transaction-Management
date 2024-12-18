import bank.Customer;
import bank.Menus;
import bank.Transaction;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int input = 0;
        int transactionInput = 0;
        int custId = 0;

        do{
            Menus.mainMenu();   // printing main menu items
            input = sc.nextInt();
            switch (input) {
                case 1 -> {
                    Customer customer = new Customer();
                    customer.insertCustomer();
                }
                case 2 -> {
                    Menus.transactionMenu();
                    transactionInput = sc.nextInt();
                    if(transactionInput == 1){
                        System.out.println("Check Balance");
                        System.out.println("Enter your customer id: ");
                        custId = sc.nextInt();
                        Customer customer = new Customer(custId);
                        customer.checkBalance();
                    } if(transactionInput == 2) {
                        System.out.println("Withdrawal");
                        Transaction transaction = new Transaction();
                        System.out.println(transaction.withdrawal());
                    } if(transactionInput == 3) {
                        System.out.println("Deposit");
                        Transaction transaction = new Transaction();
                        System.out.println(transaction.deposit());
                    }
                }

                case 3 -> Menus.exitFunc();
                default -> System.out.println("No Cases are selected!");
            }
        }while(input < 3);
    }
}