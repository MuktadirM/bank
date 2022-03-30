package com.company;

import com.company.infrastructure.AccountServices;
import com.company.infrastructure.BankServices;
import com.company.infrastructure.CustomerServices;
import com.company.infrastructure.TransactionServices;
import com.company.models.Account;
import com.company.models.Bank;
import com.company.models.Customer;
import com.company.services.IAccountServices;
import com.company.services.IBankServices;
import com.company.services.ICustomerServices;
import com.company.services.ITransactionServices;
import com.company.utils.Resource;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Bank bank = new Bank("OCBC Bank (Malaysia) Berhad","Menara OCBC, 18 Jalan Tun Perak, 50050 Kuala Lumpur", "+60 3 2034 5034", "www.ocbc.com.my");
        ITransactionServices transactionServices = new TransactionServices();
        ICustomerServices customerServices = new CustomerServices();
        IAccountServices accountServices = new AccountServices(customerServices,transactionServices);
        IBankServices bankServices = new BankServices(transactionServices,bank,accountServices);
        Customer alice = new Customer("Alice");
        Customer bob = new Customer("Bob");

        var accountResourceAlice = accountServices.createAccount(alice,100);
        var accountResourceBob = accountServices.createAccount(bob,80);

        assert accountResourceAlice.data != null;
        System.out.println(accountResourceAlice.data);
        System.out.println(accountResourceBob.data);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to " + bankServices.getBank().getName());
        printInitialMenu();
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();

        while (choice != 0) {

            Account account = null;
            if (choice == 1) {
                String transaction ;

                System.out.print("Enter your name to login: ");
                String name = scanner.next();

                //loginUserOrCreate(customerServices,accountServices,name);
                account = loginUserOrCreate(customerServices,accountServices,name);

                if (account != null) {
                    instruction();
                    System.out.println("**********************************************************");
                    System.out.println("Welcome " + account.getCustomer().getName());
                    System.out.println("Your balance " + bankServices.getBalance(account));

                    System.out.print("> ");
                    scanner.useDelimiter("\n");
                    transaction = scanner.next();
                    while (!transaction.equals("e")) {
                        var splits = transaction.split(" ");
                        String command = splits.length >= 1? splits[0].trim().toLowerCase():"";
                        String second = splits.length >= 2 ? splits[1].trim().toLowerCase():"";
                        String third = splits.length >= 3 ? splits[2].trim().toLowerCase():"";

                        switch (command) {
                            case "topup" -> {
                                bankServices.deposit(account, Double.parseDouble(second));
                                if(bankServices.getBalance(account)<0){
                                    System.out.println("Your balance is 0");
                                }

                                if(bankServices.getBalance(account)> 0 && bankServices.getOwing(account).size()>0){
                                    System.out.println("Your balance is " + bankServices.getBalance(account));
                                }

                                if(bankServices.getOwing(account) != null && bankServices.getOwing(account).size()>0){
                                    for (var founded : bankServices.getOwing(account)) {
                                        var accountName = accountServices.getAccountById(founded.getReceiverId()).getCustomer().getName();
                                        System.out.println("Your balance is " + bankServices.getBalance(account));
                                        System.out.println("Transferred to " + second+" "+ Double.parseDouble(third));
                                        System.out.println("You owing " +founded.getAmount()+" to "+ accountName);
                                    }
                                }
                            }
                            case "pay" -> {
                                var receiverResource = customerServices.getCustomerByName(second);
                                if(receiverResource.data != null) {
                                    var receiverAccountResource = accountServices.getAccount(receiverResource.data);
                                    if(receiverAccountResource.data != null) {
                                        var result = bankServices.transfer(account, receiverAccountResource.data,Double.parseDouble(third));
                                        if(result.data != null) {
                                            System.out.println("Transferred to " + second+" "+ Double.parseDouble(third));
                                            System.out.println("Your balance " + bankServices.getBalance(account));
                                        }
                                    }
                                }
                                else {
                                    System.out.println("Receiver not found");
                                }

                            }

                            case "login" -> {
                                account = loginUserOrCreate(customerServices,accountServices,second);
                                if (account != null) {
                                    System.out.println("Hello " + account.getCustomer().getName()+"!");
                                    System.out.println("Your balance " + bankServices.getBalance(account));
                                } else {
                                    System.out.println("Login failed");
                                }
                            }
                            case "check" ->{
                                bankServices.getOwing(account).forEach(System.out::println);
                                transactionServices.getAllTransactions().data.forEach(System.out::println);
                                System.out.println("**********************************************************");
                                transactionServices.getAllTransactionsByAccount(account).data.forEach(System.out::println);
                                System.out.println("**********************************************************");
                            }
                            default -> {
                                System.out.println("Invalid command");
                                System.out.println("Press ( e ) to exit");
                                System.out.print("> ");
                                transaction = scanner.next();
                            }

                        }

                        System.out.print(">");
                        transaction = scanner.next();
                    }
                }
            } else {
                System.out.println("Invalid choice");
                printInitialMenu();
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
            }
            printInitialMenu();
            System.out.print("Enter your choice : ");
            choice = scanner.nextInt();
        }


//        try {
//
//
//        } catch (Exception e) {
//            System.out.println("Invalid input last" + e.getMessage());
//        }


    }

    private static void printInitialMenu() {
        System.out.println("1. Login");
        System.out.println("0. Exit");
    }

    private static void continueProgram() {
        System.out.println("Press any key to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.next();
    }
    private static void instruction() {
        System.out.println("Now you perform the following actions \n " +
                "To Top-up your account write topup <amount> \n" +
                "To pay write pay <another_client> <amount> \n");
    }

    private static Account loginUserOrCreate(ICustomerServices customerServices,IAccountServices accountServices,String name) {
        var customerResource = customerServices.getCustomerByName(name);
        Resource<Account> accountResource;
        if (customerResource.data != null) {
            accountResource = accountServices.getAccount(customerResource.data);
        } else {
            accountResource = accountServices.createAccount(new Customer(name), 0);
        }
        if (accountResource.data != null) {
            return accountResource.data;
        }
        return null;
    }
}
