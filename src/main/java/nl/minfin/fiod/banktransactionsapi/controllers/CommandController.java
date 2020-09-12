package nl.minfin.fiod.banktransactionsapi.controllers;

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

    public CommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/banktransactions")
    public Future<String> createChatRoom(@RequestBody @Valid BankTransactionDto bankTransaction) {
        String bankTransactionId = bankTransaction.getBankTransactionId() == null ? UUID.randomUUID().toString() : bankTransaction.getBankTransactionId();
        return commandGateway.send(new CreateBankTransactionCommand(bankTransactionId, bankTransaction));
    }

    @PostMapping("/banktransactions/{bankTransactionId}/{status}")
    public Future<String> updateParseStatsu(@PathVariable String bankTransactionId, @PathVariable ParseStatus status) {
        return commandGateway.send(new UpdateBankTransactionParseStatusCommand(bankTransactionId, status));
    }
}
