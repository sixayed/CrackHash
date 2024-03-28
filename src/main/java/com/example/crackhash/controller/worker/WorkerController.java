package com.example.crackhash.controller.worker;

import com.example.crackhash.dto.WorkerCrackRequestDto;
import com.example.crackhash.dto.WorkerResponseDto;
import com.example.crackhash.service.WorkerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        List<String> crackedMessages = workerService.processCrack(task);
        WorkerResponseDto workerResponse = new WorkerResponseDto(task.getRequestId(), crackedMessages);

        try {
            workerService.patchData(workerResponse);
            return ResponseEntity.ok().build();
        }
        catch (RuntimeException e) {
            log.error("Worker " + task.getPartNumber() + "failed to patch data: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
