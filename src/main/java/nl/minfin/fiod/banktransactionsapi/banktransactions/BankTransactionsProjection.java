package nl.minfin.fiod.banktransactionsapi.banktransactions;

import nl.minfin.fiod.banktransactionsapi.domain.AllBankTransactionsQuery;
import nl.minfin.fiod.banktransactionsapi.domain.BankTransactionCreatedEvent;
import nl.minfin.fiod.banktransactionsapi.domain.UpdateBankTransactionParseStatusCommand;
import nl.minfin.fiod.banktransactionsapi.domain.UpdateBankTransactionParseStatusEvent;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class BankTransactionsProjection {

    private final BankTransactionESRepository bankTransactionRepository;

    public BankTransactionsProjection(BankTransactionESRepository bankTransactionRepository) {
        this.bankTransactionRepository = bankTransactionRepository;
    }

    @QueryHandler
    public Iterable<BankTransactionEntity> handle(AllBankTransactionsQuery query) {
        return bankTransactionRepository.findAll();
    }

    @EventHandler
    public void on(BankTransactionCreatedEvent event) {
        bankTransactionRepository.save(new BankTransactionEntity(event.getBankTransactionId(),
                event.getBankTransaction().getToAccount(), event.getBankTransaction().getToAccountHolder(),
                event.getBankTransaction().getFromAccount(), event.getBankTransaction().getFromAccountHolder()));
    }

    @EventHandler
    public void on(UpdateBankTransactionParseStatusEvent event) {
        bankTransactionRepository.findById(event.getBankTransactionId())
                .ifPresentOrElse(bankTransactionEntity -> {
                            bankTransactionEntity.setParseStatus(event.getParseStatus());
                            bankTransactionRepository.save(bankTransactionEntity);
                        },
                        () -> {
                            throw new IllegalArgumentException();
                        });
    }
}
