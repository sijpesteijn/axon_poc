package nl.minfin.fiod.banktransactionsapi.domain;

import java.time.Instant;

public class BankTransactionDto {
    private String bankTransactionId;
    private String fromAccount;
    private String fromAccountHolder;
    private String toAccount;
    private String toAccountHolder;
    private Instant transactionDateTime;
    private String currency;

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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Instant getTransactionDateTime() {
        return transactionDateTime;
    }

    public void setTransactionDateTime(Instant transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }
}
