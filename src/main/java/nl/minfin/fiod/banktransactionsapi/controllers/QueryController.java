package nl.minfin.fiod.banktransactionsapi.controllers;

import nl.minfin.fiod.banktransactionsapi.domain.AllBankTransactionsQuery;
import nl.minfin.fiod.banktransactionsapi.banktransactions.BankTransactionEntity;
import org.axonframework.messaging.responsetypes.MultipleInstancesResponseType;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.Future;

@RestController
public class QueryController {

    private final QueryGateway queryGateway;

    public QueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("banktransactions")
    public Future<List<BankTransactionEntity>> listRooms() {
        return queryGateway.query(new AllBankTransactionsQuery(), new MultipleInstancesResponseType<>(BankTransactionEntity.class));
    }
}
