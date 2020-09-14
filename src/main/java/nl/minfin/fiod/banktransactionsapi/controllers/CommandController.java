package nl.minfin.fiod.banktransactionsapi.controllers;

import com.github.javafaker.Faker;
import nl.minfin.fiod.banktransactionsapi.banktransactions.ParseStatus;
import nl.minfin.fiod.banktransactionsapi.domain.BankTransactionDto;
import nl.minfin.fiod.banktransactionsapi.domain.CreateBankTransactionCommand;
import nl.minfin.fiod.banktransactionsapi.domain.UpdateBankTransactionParseStatusCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.Future;
import javax.validation.Valid;

@RestController
public class CommandController {
    private final CommandGateway commandGateway;
    private Faker faker = new Faker();

    public CommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/banktransactions")
    public Future<String> createBankTransaction(@RequestBody @Valid BankTransactionDto bankTransaction) {
        String bankTransactionId = bankTransaction.getBankTransactionId() == null ? UUID.randomUUID().toString() : bankTransaction.getBankTransactionId();
        return commandGateway.send(new CreateBankTransactionCommand(bankTransactionId, bankTransaction));
    }

    @PostMapping("/banktransactions/random")
    public Future<String> createRandomBankTransaction() {
        String bankTransactionId = UUID.randomUUID().toString();
        BankTransactionDto bankTransaction = new BankTransactionDto();
        bankTransaction.setBankTransactionId(bankTransactionId);
        bankTransaction.setFromAccount(faker.finance().iban());
        bankTransaction.setFromAccountHolder(faker.lebowski().character());
        bankTransaction.setToAccount(faker.finance().iban());
        bankTransaction.setToAccountHolder(faker.lebowski().character());
        bankTransaction.setCurrency(faker.currency().code());
        return commandGateway.send(new CreateBankTransactionCommand(bankTransactionId, bankTransaction));
    }

    @PostMapping("/banktransactions/{bankTransactionId}/{status}")
    public Future<String> updateParseStatus(@PathVariable String bankTransactionId, @PathVariable ParseStatus status) {
        return commandGateway.send(new UpdateBankTransactionParseStatusCommand(bankTransactionId, status));
    }
}
