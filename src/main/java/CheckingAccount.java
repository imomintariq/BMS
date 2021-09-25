import java.util.*;
public class CheckingAccount extends BankAccount {
    //makeWithdrawal()
    //Can withdraw more than their account balance( up to 5000PKR
    // List of all transactions and their fee
    public int freeTransactions;


    public CheckingAccount(long _accountNumber, double _balance) {
        super(_accountNumber, _balance);
        this.freeTransactions = 0;
    }



    public boolean checkTransactionFee(){
        if(TransactionsList.size() != 0) {
            Date lastTransaction = TransactionsList.get(TransactionsList.size() - 1).getDate();
            Date NwDate = new Date();
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(lastTransaction);
            cal2.setTime(NwDate);
            boolean sameMonth = cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                    cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
            if (sameMonth == true && freeTransactions < 3) {
                freeTransactions++;
                if (freeTransactions >= 3) {
                    return true;
                } else
                    return false;
            } else if (sameMonth == false) {
                freeTransactions = 0;
                return false;

            }
            else if (sameMonth == true && freeTransactions >= 3){
                freeTransactions++;
                return true;
            }
            else {
                return false;
            }
        }
        else{
            freeTransactions++;
            return false;
        }
    }
    public boolean makeWithdrawal(double _amount){
        if(_amount > this.getBalance()){
            double difference = _amount - this.getBalance();
            if(difference <= 5000)
            {

                Transaction newTransaction = new Transaction("withdraw", _amount);
                if(checkTransactionFee()){
                    newTransaction.setTransactionFee(10);
                }
                this.TransactionsList.add(newTransaction);
                setBalance(getBalance() - _amount);
                //Transaction Fee
                setBalance(getBalance() - 10);
                return true;
            }

            else{
                System.out.println("You can only withdraw Rs.5000 more than your account balance");
                return false;
            }

        }
        else if(_amount <= this.getBalance()){
            Transaction newTransaction = new Transaction("withdraw", _amount);
            if(checkTransactionFee()){
                newTransaction.setTransactionFee(10);
            }
            this.TransactionsList.add(newTransaction);
            setBalance(getBalance() - _amount);
            //Transaction Fee
            setBalance(getBalance() - 10);
            return true;
        }
        return false;
    }
    public void makeDeposit(double _amount){
        Transaction newTransaction = new Transaction("deposit", _amount);
        if(checkTransactionFee()){
            newTransaction.setTransactionFee(10);
        }
        TransactionsList.add(newTransaction);
        setBalance(getBalance() + _amount);
        //Transaction Fee
        setBalance(getBalance() - 10);
    }

}
