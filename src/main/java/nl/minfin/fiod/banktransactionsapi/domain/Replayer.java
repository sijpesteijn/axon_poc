package nl.minfin.fiod.banktransactionsapi.domain;

import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventhandling.ReplayToken;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.axonframework.eventhandling.TrackingToken;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventhandling.tokenstore.inmemory.InMemoryTokenStore;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.stream.IntStream;

@Component
public class Replayer {
    private EventProcessingConfiguration configuration;
    private InMemoryTokenStore tokenStore = new InMemoryTokenStore();

    public Replayer(EventProcessingConfiguration configuration) {
        this.configuration = configuration;
    }

    public void replay(String name) {
        configuration.eventProcessor(name, TrackingEventProcessor.class).ifPresent(processor -> {
            processor.shutDown();
            processor.resetTokens();
            processor.start();
        });
    }

    @Transactional
    public Optional<Progress> getProgress(String name) {
        int[] segments = tokenStore.fetchSegments(name);

        if (segments.length == 0) {
            return Optional.empty();
        } else {
            Progress accumulatedProgress = IntStream.of(segments).mapToObj(segment -> {
                TrackingToken token = tokenStore.fetchToken(name, segment);

                OptionalLong maybeCurrent = token.position();
                OptionalLong maybePositionAtReset = OptionalLong.empty();

                if (token instanceof ReplayToken) {
                    maybePositionAtReset = ((ReplayToken) token).getTokenAtReset().position();
                }

                return new Progress(maybeCurrent.orElse(0L), maybePositionAtReset.orElse(0L));
            }).reduce(new Progress(0, 0), (acc, progress) ->
                    new Progress(acc.getCurrent() + progress.getCurrent(), acc.getTail() + progress.getTail()));

            return (accumulatedProgress.getTail() == 0L) ? Optional.empty() : Optional.of(accumulatedProgress);
        }
    }

    public static class Progress {
        private long current;
        private long tail;

        public Progress(long current, long tail) {
            this.current = current;
            this.tail = tail;
        }

        public BigDecimal getProgress() {
            return BigDecimal.valueOf(current, 2).divide(BigDecimal.valueOf(tail, 2), RoundingMode.HALF_UP);
        }

        public long getCurrent() {
            return current;
        }

        public long getTail() {
            return tail;
        }
    }
}
