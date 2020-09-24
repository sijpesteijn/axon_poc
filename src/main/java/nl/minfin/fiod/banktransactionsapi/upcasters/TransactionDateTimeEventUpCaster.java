package nl.minfin.fiod.banktransactionsapi.upcasters;

import nl.minfin.fiod.banktransactionsapi.domain.BankTransactionCreatedEvent;
import org.axonframework.serialization.SimpleSerializedType;
import org.axonframework.serialization.upcasting.event.IntermediateEventRepresentation;
import org.axonframework.serialization.upcasting.event.SingleEventUpcaster;

import java.time.Instant;

public class TransactionDateTimeEventUpCaster extends SingleEventUpcaster {
    private static SimpleSerializedType targetType =
            new SimpleSerializedType(BankTransactionCreatedEvent.class.getTypeName(), "1.0");
    @Override
    protected boolean canUpcast(IntermediateEventRepresentation intermediateEventRepresentation) {
        return intermediateEventRepresentation.getType().equals(targetType);
    }

    @Override
    protected IntermediateEventRepresentation doUpcast(IntermediateEventRepresentation intermediateEventRepresentation) {
        return intermediateEventRepresentation.upcastPayload(
                new SimpleSerializedType(targetType.getName(), "2.0"),
                org.dom4j.Document.class,
                document -> {
                    document.getRootElement().elements().get(1)
                            .addElement("transactionDateTime")
                            .setText(Instant.now().toString()); // Default value
                    return document;
                }
        );
    }
}
