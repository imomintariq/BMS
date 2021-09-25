public class SavingsAccount extends BankAccount{
/*    calculateZakat()->if the total balance is greater than or equal to 20,000. Zakat will be calculated using this formula (TotalBalance*2.5)/100
    calculatelnterest()->interest can be changed at run time
		#Cannot withdraw more than their balance*/

    public SavingsAccount(long _accountNumber, double _balance, double interestRate) {
        super(_accountNumber, _balance);
        this.interestRate = interestRate;
    }

    public double interestRate;
    public boolean makeWithdrawal(double _amount){
        if(_amount > this.getBalance()){
            System.out.println("You cannot withdraw more than your balance");
            return false;
        }
        else
        {
            Transaction newTransaction = new Transaction("withdraw", _amount);
            TransactionsList.add(newTransaction);
            setBalance(getBalance() - _amount);
            return true;
        }

    }
    public double calculateZakat(){
        if(this.getBalance()>= 20000){
            double zakaat = (getBalance()*2.5)/100;
            System.out.println("Zakaat Amount = " + zakaat);
            return zakaat;
        }
        else{
            System.out.println("You are not eligible for Zakaat");
            return 0;
        }
    }
    public void  calculatelnterest(){
        System.out.println("Total Interest is " + getBalance()*interestRate);
    }
    public void displayAllDeductions(){
        for(int i = 0; i < TransactionsList.size(); i++) {
            System.out.println("-------------------------------------------------");
            System.out.println("Date             : "+TransactionsList.get(i).getDate());
            System.out.println("Trans Amount     : "+TransactionsList.get(i).getTransactionAmount());
            System.out.println("Trans Fee        : "+TransactionsList.get(i).getTransactionFee());
            System.out.println("Trans Type       : "+TransactionsList.get(i).getTransactionType());
        }
        System.out.println    ("Zakaat Amount    : "+ calculateZakat());

    }
}
