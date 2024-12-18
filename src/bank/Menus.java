package bank;

import java.util.Scanner;

public class Menus {

    public static void mainMenu(){
        System.out.println("Indian Bank");
        System.out.println(".............");
        System.out.println("1. Create New Account (note: initial account balance: 1000)");
        System.out.println("2. Make Transaction");
        System.out.println("3. Exit");
    }

    public static void transactionMenu(){
        System.out.println("Transaction Menu");
        System.out.println(".................");
        System.out.println("1. Check Balance");
        System.out.println("2. Withdrawal");
        System.out.println("3. Deposit");
       // System.out.println("4. Exit");
    }

    public static void exitFunc(){
        System.out.println("Thank you for using Indian Bank!....");
    }
}
