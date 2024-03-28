package com.example.crackhash.controller.manager;

import com.example.crackhash.dto.WorkerResponseDto;
import com.example.crackhash.service.ResultService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/internal/api/manager/hash/crack")
public class InternalManagerController {
    private final ResultService resultService;

    public InternalManagerController(ResultService resultService) {
        this.resultService = resultService;
    }

    @PatchMapping("/request")
    public ResponseEntity<Void> handleWorkerResponse(@RequestBody WorkerResponseDto workerResponse) {
        for(String data : workerResponse.getData()) {
            log.info("DATA FROM WORKER: {}", data);
        }

        // тут data кладется не в то приложение

        resultService.addToData(workerResponse.getRequestId(), workerResponse.getData());
        return ResponseEntity.ok().build();
    }
}
