package org.example.worker.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.models.dto.WorkerCrackRequestDto;
import org.example.models.dto.WorkerResponseDto;
import org.example.worker.service.WorkerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/internal/api/worker/hash/crack")
public class WorkerController {

    private final WorkerService workerService;

    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @PostMapping("/task")
    public ResponseEntity<Void> takeTask(@RequestBody WorkerCrackRequestDto task) {
        log.info("Worker {} take task with requestId = {}", task.getPartNumber(), task.getRequestId());
        return workerService.takeTask(task);
    }
}
