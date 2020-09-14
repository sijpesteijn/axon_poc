package nl.minfin.fiod.banktransactionsapi.controllers;

import nl.minfin.fiod.banktransactionsapi.domain.AllBankTransactionsQuery;
import nl.minfin.fiod.banktransactionsapi.banktransactions.BankTransactionEntity;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.Message;
import org.axonframework.messaging.responsetypes.MultipleInstancesResponseType;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static org.axonframework.messaging.responsetypes.ResponseTypes.instanceOf;
import static org.axonframework.messaging.responsetypes.ResponseTypes.multipleInstancesOf;

@RestController
public class QueryController {

    private final QueryGateway queryGateway;
    private EventStore eventStore;

    public QueryController(QueryGateway queryGateway, EventStore eventStore) {
        this.queryGateway = queryGateway;
        this.eventStore = eventStore;
    }

    @GetMapping("banktransactions")
    public Future<List<BankTransactionEntity>> listRooms() {
        return queryGateway.query(new AllBankTransactionsQuery(), new MultipleInstancesResponseType<>(BankTransactionEntity.class));
    }

    @GetMapping(value = "/banktransactions/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<BankTransactionEntity> subscribeRoomMessages() {
        AllBankTransactionsQuery query = new AllBankTransactionsQuery();
        SubscriptionQueryResult<List<BankTransactionEntity>, BankTransactionEntity> result;
        result = queryGateway.subscriptionQuery(
                query, multipleInstancesOf(BankTransactionEntity.class), instanceOf(BankTransactionEntity.class)
        );
        /* If you only want to send new messages to the client, you could simply do:
                return result.updates();
           However, in our implementation we want to provide both existing messages and new ones,
           so we combine the initial result and the updates in a single flux. */
        Flux<BankTransactionEntity> initialResult = result.initialResult().flatMapMany(Flux::fromIterable);
        return Flux.concat(initialResult, result.updates());
    }

    @GetMapping("/banktransactions/{banktransactionId}/events")
    public List<Object> listEventsForAccount(@PathVariable(value = "banktransactionId") String banktransactionId) {
        return this.eventStore
                .readEvents(banktransactionId)
                .asStream()
                .map(Message::getPayload)
                .collect(Collectors.toList());
    }
}
