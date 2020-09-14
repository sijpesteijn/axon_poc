package nl.minfin.fiod.banktransactionsapi.controllers;

import nl.minfin.fiod.banktransactionsapi.domain.Replayer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.NumberFormat;

@RestController
public class ReplayController {

    private Replayer replayer;

    public ReplayController(Replayer replayer) {
        this.replayer = replayer;
    }

    @PostMapping("/replay")
    public String replay() {
        replayer.replay("nl.minfin.fiod.banktransactionsapi.banktransactions");

        return "Replay started...";
    }

    @GetMapping("/replay")
    public String progress() {
        return replayer.getProgress("nl.minfin.fiod.banktransactionsapi.banktransactions").map(progress ->
                String.format("Replay at %d/%d (%s complete)",
                        progress.getCurrent(), progress.getTail(), NumberFormat.getPercentInstance().format(progress.getProgress().doubleValue()))
        ).orElse("No replay active");
    }
}
