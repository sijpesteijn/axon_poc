package nl.minfin.fiod.banktransactionsapi.banktransactions;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.annotation.Id;

import java.time.Instant;

@Document(indexName = "banktransactions")
public class BankTransactionEntity {
    @Id
    private String bankTransactionId;
    private String toAccount;
    private String toAccountHolder;
    private String fromAccount;
    private String fromAccountHolder;
    private ParseStatus parseStatus;

    public BankTransactionEntity() {
    }

    public BankTransactionEntity(String bankTransactionId, String toAccount, String toAccountHolder, String fromAccount,
                                 String fromAccountHolder) {
        this.bankTransactionId = bankTransactionId;
        this.toAccount = toAccount;
        this.toAccountHolder = toAccountHolder;
        this.fromAccount = fromAccount;
        this.fromAccountHolder = fromAccountHolder;
    }

    public void setParseStatus(ParseStatus parseStatus) {
        this.parseStatus = parseStatus;
    }

    public String getBankTransactionId() {
        return bankTransactionId;
    }

    public void setBankTransactionId(String bankTransactionId) {
        this.bankTransactionId = bankTransactionId;
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

    public ParseStatus getParseStatus() {
        return parseStatus;
    }
}
