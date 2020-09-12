package nl.minfin.fiod.banktransactionsapi.domain;

public class BankTransactionDto {
    private String bankTransactionId;
    private String fromAccount;
    private String fromAccountHolder;
    private String toAccount;
    private String toAccountHolder;

    public BankTransactionDto() {
    }

    public String getBankTransactionId() {
        return bankTransactionId;
    }

    public void setBankTransactionId(String bankTransactionId) {
        this.bankTransactionId = bankTransactionId;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getFromAccountHolder() {
        return fromAccountHolder;
    }

    public void setFromAccountHolder(String fromAccountHolder) {
        this.fromAccountHolder = fromAccountHolder;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public String getToAccountHolder() {
        return toAccountHolder;
    }

    public void setToAccountHolder(String toAccountHolder) {
        this.toAccountHolder = toAccountHolder;
    }

}
