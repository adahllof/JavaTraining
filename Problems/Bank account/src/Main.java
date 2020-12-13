class BankAccount {

    protected String number;
    protected Long balance;

    public BankAccount(String number, Long balance) {
        this.number = number;
        this.balance = balance;
    }
}
class CheckingAccount extends BankAccount {

    double fee;

    CheckingAccount(String number, Long balance, double fee) {
        super(number, balance);
        this.fee = fee;
    }
}
class SavingAccount extends BankAccount {

    double interestRate;

    SavingAccount(String number, Long ballance, double interestRate) {
        super(number, ballance);
        this.interestRate = interestRate;
    }
}
