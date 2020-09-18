package nl.minfin.fiod.banktransactionsapi.upcasters;

import nl.minfin.fiod.banktransactionsapi.domain.BankTransactionCreatedEvent;
import org.axonframework.serialization.SimpleSerializedType;
import org.axonframework.serialization.upcasting.event.IntermediateEventRepresentation;
import org.axonframework.serialization.upcasting.event.SingleEventUpcaster;

public class CurrencyEventUpCaster extends SingleEventUpcaster {
    private static SimpleSerializedType targetType =
            new SimpleSerializedType(BankTransactionCreatedEvent.class.getTypeName(), null);
    @Override
    protected boolean canUpcast(IntermediateEventRepresentation intermediateEventRepresentation) {
        return intermediateEventRepresentation.getType().equals(targetType);
    }

    @Override
    protected IntermediateEventRepresentation doUpcast(IntermediateEventRepresentation intermediateEventRepresentation) {
        return intermediateEventRepresentation.upcastPayload(
                new SimpleSerializedType(targetType.getName(), "1.0"),
                org.dom4j.Document.class,
                document -> {
                    document.getRootElement().elements().get(1)
                            .addElement("currency")
                            .setText("DOLLAR"); // Default value
                    return document;
                }
        );
    }
}
