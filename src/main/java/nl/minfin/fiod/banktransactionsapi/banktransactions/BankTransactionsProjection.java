package nl.minfin.fiod.banktransactionsapi.banktransactions;

import nl.minfin.fiod.banktransactionsapi.domain.AllBankTransactionsQuery;
import nl.minfin.fiod.banktransactionsapi.domain.BankTransactionCreatedEvent;
import nl.minfin.fiod.banktransactionsapi.domain.UpdateBankTransactionParseStatusEvent;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ReplayStatus;
import org.axonframework.eventhandling.Timestamp;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class BankTransactionsProjection {

    private final BankTransactionESRepository bankTransactionRepository;
    private final QueryUpdateEmitter updateEmitter;

    public BankTransactionsProjection(BankTransactionESRepository bankTransactionRepository, QueryUpdateEmitter updateEmitter) {
        this.bankTransactionRepository = bankTransactionRepository;
        this.updateEmitter = updateEmitter;
    }

    @QueryHandler
    public Iterable<BankTransactionEntity> handle(AllBankTransactionsQuery query) {
        return bankTransactionRepository.findAll();
    }

    @EventHandler
    public void on(BankTransactionCreatedEvent event, ReplayStatus replayStatus, @Timestamp Instant timestamp) {
        System.out.println("Replay status: " + replayStatus.isReplay());
        BankTransactionEntity bankTransactionEntity = new BankTransactionEntity(event.getBankTransactionId(),
                event.getBankTransaction().getToAccount(), event.getBankTransaction().getToAccountHolder(),
                event.getBankTransaction().getFromAccount(), event.getBankTransaction().getFromAccountHolder(),
                event.getBankTransaction().getCurrency(), ParseStatus.NEW, timestamp, timestamp);
        bankTransactionRepository.save(bankTransactionEntity);
        this.updateEmitter.emit(AllBankTransactionsQuery.class, query -> true, bankTransactionEntity);
    }

    @EventHandler
    public void on(UpdateBankTransactionParseStatusEvent event, @Timestamp Instant timestamp) {
        bankTransactionRepository.findById(event.getBankTransactionId())
                .ifPresentOrElse(bankTransactionEntity -> {
                            bankTransactionEntity.setParseStatus(event.getParseStatus());
                            bankTransactionEntity.setLastUpdateDate(timestamp);
                            bankTransactionRepository.save(bankTransactionEntity);
                            this.updateEmitter.emit(AllBankTransactionsQuery.class, query -> true, bankTransactionEntity);
                        },
                        () -> {
                            throw new IllegalArgumentException();
                        });
    }
}
