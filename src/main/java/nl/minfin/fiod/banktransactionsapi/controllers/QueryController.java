package nl.minfin.fiod.banktransactionsapi.controllers;

import nl.minfin.fiod.banktransactionsapi.banktransactions.BankTransactionEntity;
import nl.minfin.fiod.banktransactionsapi.domain.AllBankTransactionsQuery;
import nl.minfin.fiod.banktransactionsapi.domain.BankTransactionQuery;
import org.axonframework.messaging.responsetypes.MultipleInstancesResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.Future;

import static org.axonframework.messaging.responsetypes.ResponseTypes.instanceOf;
import static org.axonframework.messaging.responsetypes.ResponseTypes.multipleInstancesOf;

@RestController
public class QueryController {

    private final QueryGateway queryGateway;

    public QueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("banktransactions")
    public Future<List<BankTransactionEntity>> listBankTransactions() {
        return queryGateway.query(new AllBankTransactionsQuery(), new MultipleInstancesResponseType<>(BankTransactionEntity.class));
    }

    @GetMapping(value = "/banktransactions/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<BankTransactionEntity> subscribeBankTransactions() {
        AllBankTransactionsQuery query = new AllBankTransactionsQuery();
        SubscriptionQueryResult<List<BankTransactionEntity>, BankTransactionEntity> result;
        result = queryGateway.subscriptionQuery(
                query, multipleInstancesOf(BankTransactionEntity.class), instanceOf(BankTransactionEntity.class)
        );
        Flux<BankTransactionEntity> initialResult = result.initialResult().flatMapMany(Flux::fromIterable);
        return Flux.concat(initialResult, result.updates());
    }

    @GetMapping("/banktransactions/{banktransactionId}")
    public Future<BankTransactionEntity> getBankTransaction(@PathVariable(value = "banktransactionId") String banktransactionId) {
        return queryGateway.query(new BankTransactionQuery(banktransactionId), BankTransactionEntity.class);
    }

    @GetMapping(value = "/banktransactions/{banktransactionId}/events/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<BankTransactionEntity> subscribeBankTransactionEvents(@PathVariable(value = "banktransactionId") String banktransactionId) {
        BankTransactionQuery query = new BankTransactionQuery(banktransactionId);
        SubscriptionQueryResult<BankTransactionEntity, BankTransactionEntity> result;
        result = queryGateway.subscriptionQuery(
                query, ResponseTypes.instanceOf(BankTransactionEntity.class), instanceOf(BankTransactionEntity.class)
        );
        Flux<BankTransactionEntity> initialResult = Flux.from(result.initialResult());
        return Flux.concat(initialResult, result.updates());
    }

}
