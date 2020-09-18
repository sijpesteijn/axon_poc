package nl.minfin.fiod.banktransactionsapi.banktransactions;

import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;

@Document(indexName = "banktransactions")
public class BankTransactionEntity {
    @Id
    @Field(type = FieldType.Text)
    private String bankTransactionId;
    @Field(type = FieldType.Text)
    private String toAccount;
    @Field(type = FieldType.Text)
    private String toAccountHolder;
    @Field(type = FieldType.Text)
    private String fromAccount;
    @Field(type = FieldType.Text)
    private String fromAccountHolder;
    @Field(type = FieldType.Text)
    private String currency;
    @Field(type = FieldType.Text)
    private ParseStatus parseStatus;
    @Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    private Instant createDate;
    @Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    private Instant lastUpdateDate;

    public BankTransactionEntity() {
    }

    public BankTransactionEntity(String bankTransactionId, String toAccount, String toAccountHolder, String fromAccount,
                                 String fromAccountHolder, String currency, ParseStatus parseStatus, Instant createDate, Instant lastUpdateDate) {
        this.bankTransactionId = bankTransactionId;
        this.toAccount = toAccount;
        this.toAccountHolder = toAccountHolder;
        this.fromAccount = fromAccount;
        this.fromAccountHolder = fromAccountHolder;
        this.currency = currency;
        this.parseStatus = parseStatus;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setLastUpdateDate(Instant lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Instant getLastUpdateDate() {
        return lastUpdateDate;
    }
}
