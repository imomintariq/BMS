import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class BankAccount
{
    private long accountNumber;
    private double balance;
    private Date dateCreated;
    public List<Transaction> TransactionsList;

    BankAccount(long _accountNumber, double _balance){
        accountNumber = _accountNumber;
        balance = _balance;
        dateCreated = new Date();
        TransactionsList = new ArrayList<Transaction>();

    }

    public BankAccount() {
        accountNumber = 0;
        balance = 0;
        dateCreated = new Date();
        TransactionsList = new ArrayList<Transaction>();
    }

    public long getAccountNumber()
    {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber)
    {
        this.accountNumber = accountNumber;
    }

    public double getBalance()
    {
        return balance;
    }

    public void setBalance(double balance)
    {
        this.balance = balance;
    }

    public Date getDateCreated()
    {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated)
    {
        this.dateCreated = dateCreated;
    }

    public int mode(int a,int b){
        return(a%b);
    }
    public void checkBalance(){
        //Also displays Name of the customer
        System.out.println("Balance          = " + getBalance());
    }
    void makeDeposit(double _amount){
        Transaction newTransaction = new Transaction("deposit", _amount);
        TransactionsList.add(newTransaction);
        setBalance(getBalance() + _amount);
    }

    public void printStatement(){
        //customer,
        System.out.println("Account Number          = " + getAccountNumber());
        // time of the transaction,
        // date of transaction,
        // transaction amount, and
        // remaining balance
        System.out.println("Remaining Balance       = " + getBalance());

    }

    public void transferAmount(double _amount){
        //transfers the amount from one bank account to the bank account selected by the user
    }
    public boolean makeWithdrawal(double _amount){
        Transaction newTransaction = new Transaction("withdraw", _amount);
        TransactionsList.add(newTransaction);
        setBalance(getBalance() - _amount);
        return true;
    }

    public void displayAllDeductions(){
        for(int i = 0; i < TransactionsList.size(); i++) {
            System.out.println("-------------------------------------------------");
            System.out.println("Date             : "+TransactionsList.get(i).getDate());
            System.out.println("Trans Amount     : "+TransactionsList.get(i).getTransactionAmount());
            System.out.println("Trans Fee        : "+TransactionsList.get(i).getTransactionFee());
            System.out.println("Trans Type       : "+TransactionsList.get(i).getTransactionType());
        }
    }
}


