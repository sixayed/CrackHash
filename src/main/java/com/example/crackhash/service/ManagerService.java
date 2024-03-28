package com.example.crackhash.service;

import com.example.crackhash.config.CrackConfig;
import com.example.crackhash.config.WebClientConfig;
import com.example.crackhash.dto.CrackRequestDto;
import com.example.crackhash.dto.ResultDto;
import com.example.crackhash.dto.WorkerCrackRequestDto;
import com.example.crackhash.model.StatusEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
public class ManagerService {

    private final WebClient webClient;

    private final ResultService resultService;

    private final CrackConfig crackConfig;

    public ManagerService(@Qualifier("workerWebClient") WebClient webClient, ResultService resultService,
                          CrackConfig crackConfig) {
        this.webClient = webClient;
        this.resultService = resultService;
        this.crackConfig = crackConfig;
    }

    public String submitCrackRequest(@NotNull CrackRequestDto request) {
        String requestId = UUID.randomUUID().toString();
        resultService.createResult(requestId);

        Flux<WorkerCrackRequestDto> workerRequestsData = Flux.range(0, crackConfig.getWorkerCount())
                .map(i -> new WorkerCrackRequestDto(
                        request.getHash(),
                        request.getMaxLength(),
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
                resultService.updateStatus(requestId, StatusEnum.READY.name());
                log.info("All workers responded for request with uuid = {}", requestId);
            } else {
                resultService.updateStatus(requestId, StatusEnum.ERROR.name());
                log.warn("Some workers not responded for request with uuid = {}", requestId);
            }
        });

        return requestId;
    }

    public Optional<ResultDto> getRequestStatus(@NotNull String requestId) {
        return resultService.getResult(requestId);
    }
}
