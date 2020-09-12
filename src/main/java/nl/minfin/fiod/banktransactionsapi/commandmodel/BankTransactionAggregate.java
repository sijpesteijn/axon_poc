package nl.minfin.fiod.banktransactionsapi.commandmodel;

import nl.minfin.fiod.banktransactionsapi.domain.BankTransactionCreatedEvent;
import nl.minfin.fiod.banktransactionsapi.domain.CreateBankTransactionCommand;
import nl.minfin.fiod.banktransactionsapi.domain.UpdateBankTransactionParseStatusCommand;
import nl.minfin.fiod.banktransactionsapi.domain.UpdateBankTransactionParseStatusEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class BankTransactionAggregate {

    @AggregateIdentifier
    private String bankTransactionId;

    public BankTransactionAggregate() {}

    @CommandHandler
    public BankTransactionAggregate(CreateBankTransactionCommand cmd) {
        apply(new BankTransactionCreatedEvent(cmd.getBankTransactionId(), cmd.getBankTransaction()));
    }

    @CommandHandler
    public void handle(UpdateBankTransactionParseStatusCommand cmd) {
        apply(new UpdateBankTransactionParseStatusEvent(bankTransactionId, cmd.getParseStatus()));
    }

   @EventHandler
   public void handle(BankTransactionCreatedEvent event) {
        this.bankTransactionId = event.getBankTransactionId();
   }
}
