package org.example.worker.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.models.dto.WorkerCrackRequestDto;
import org.example.models.dto.WorkerResponseDto;
import org.example.worker.exception.WorkerPatchException;
import org.example.worker.util.HashCracker;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Slf4j
@Service
@RequiredArgsConstructor
public class WorkerService {

    private final WebClient webClient;

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public ResponseEntity<Void> takeTask(@NonNull WorkerCrackRequestDto task) {
        executorService.submit(()-> processCrack(task));
        return ResponseEntity.ok().build();
    }

    private void processCrack(@NonNull WorkerCrackRequestDto task) {
        List<String> crackedMessages = bruteForce(task);

        try {
            patchData(new WorkerResponseDto(task.getRequestId(), crackedMessages));
        }
        catch (RuntimeException e) {
            log.error("Worker {} failed to patch data: {}", task.getPartNumber(), e.getMessage());
        }
    }

    // TODO: здесь должен быть учтен номер воркера
    private List<String> bruteForce(@NonNull WorkerCrackRequestDto task) {
        log.info("Worker {} started to crack", task.getPartNumber());
        List<String> selectedAnswers = new ArrayList<>();
        for(int i = 1; i < task.getCrackRequestDto().getMaxLength() + 1; i++) {
            selectedAnswers.addAll(HashCracker.matchHash(task.getAlphabet(), task.getCrackRequestDto().getHash(), i));
        }

        return selectedAnswers;
    }

    private void patchData(@NonNull WorkerResponseDto workerResponse) {
        webClient.patch()
                .uri("/internal/api/manager/hash/crack/request")
                .bodyValue(workerResponse)
                .retrieve()
                .toBodilessEntity()
                .subscribe(response -> {

                }, error -> {
                    throw new WorkerPatchException("Error during patch request, requestId: " +
                            workerResponse.getRequestId(), error);
                });
    }
}
