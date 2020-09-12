package nl.minfin.fiod.banktransactionsapi.banktransactions;

import org.springframework.data.repository.CrudRepository;

public interface BankTransactionESRepository extends CrudRepository<BankTransactionEntity, String> {
}
