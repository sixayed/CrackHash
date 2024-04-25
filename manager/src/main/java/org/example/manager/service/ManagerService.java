package org.example.manager.service;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.manager.config.CrackConfig;
import org.example.manager.repository.CrackRepository;
import org.example.models.dto.CrackRequestDto;
import org.example.models.dto.CrackResultDto;
import org.example.models.dto.WorkerCrackRequestDto;
import org.example.models.model.StatusEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagerService {

    private final WebClient webClient;

    private final CrackRepository crackRepository;

    private final CrackConfig crackConfig;


    public String submitCrackRequest(@NonNull CrackRequestDto request) {
        String requestId = UUID.randomUUID().toString();
        crackRepository.createResult(requestId);

        Flux<WorkerCrackRequestDto> workerRequestsData = Flux.range(0, crackConfig.getWorkerCount())
                .map(i -> new WorkerCrackRequestDto(
                        request,
                        crackConfig.getWorkerCount(),
                        i,
                        crackConfig.getAlphabet(),
                        requestId
                ));

        Flux<ResponseEntity<Void>> workerRequests = workerRequestsData.flatMap(workerTask ->
                webClient.post()
                        .uri("/internal/api/worker/hash/crack/task")
                        .bodyValue(workerTask)
                        .retrieve()
                        .toBodilessEntity()
                        .timeout(Duration.ofSeconds(crackConfig.getWorkerTimeoutSeconds()))
                        .onErrorResume(Throwable.class, e ->
                                Mono.empty())
        );

        Mono<List<ResponseEntity<Void>>> responses = workerRequests.collectList();
        responses.subscribe(resp -> {
            if (resp.size() == crackConfig.getWorkerCount()) {
                log.info("All workers handle request with uuid = {}", requestId);
            } else {
                crackRepository.updateStatus(requestId, StatusEnum.ERROR.name());
                log.warn("Some workers not handle request with uuid = {}", requestId);
            }
        });

        return requestId;
    }

    public ResponseEntity<CrackResultDto> getRequestStatus(@NonNull String requestId) {
        log.info("Getting request status with id = {}", requestId);
        Optional<CrackResultDto> status = crackRepository.getResult(requestId);
        return status
                .map(s -> ResponseEntity.ok().body(s))
                .orElse(ResponseEntity.notFound().build());
    }
}