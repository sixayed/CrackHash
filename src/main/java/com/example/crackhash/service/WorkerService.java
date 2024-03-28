package com.example.crackhash.service;

import com.example.crackhash.dto.WorkerCrackRequestDto;
import com.example.crackhash.dto.WorkerResponseDto;
import com.example.crackhash.utils.HashCracker;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class WorkerService {

    private final WebClient webClient;

    public WorkerService(@Qualifier("managerWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    // TODO: здесь должен быть учтен номер воркера
    public List<String> processCrack(@NotNull WorkerCrackRequestDto task) {
        log.info("Worker {} started to crack", task.getPartNumber());
        List<String> selectedAnswers = new ArrayList<>();
        for(int i = 1; i < task.getMaxLength() + 1; i++) {
            selectedAnswers.addAll(HashCracker.matchHash(task.getAlphabet(), task.getHash(), i));
        }

        return selectedAnswers;
    }

    public void patchData(@NotNull WorkerResponseDto workerResponse) {
        webClient.patch()
                .uri("/internal/api/manager/hash/crack/request")
                .bodyValue(workerResponse)
                .retrieve()
                .toBodilessEntity()
                .subscribe(response -> {

                }, error -> {
                    throw new RuntimeException("Worker failed to send patch request");
                });
    }
}
