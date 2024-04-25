package org.example.manager.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.manager.repository.CrackRepository;
import org.example.manager.service.InternalManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.example.models.dto.WorkerResponseDto;

@Slf4j
@RestController
@RequestMapping("/internal/api/manager/hash/crack")
@RequiredArgsConstructor
public class InternalManagerController {

    private final InternalManagerService internalManagerService;

    @PatchMapping("/request")
    public ResponseEntity<Void> handleWorkerResponse(@RequestBody WorkerResponseDto workerResponse) {
        internalManagerService.handleWorkerResponse(workerResponse);
        return ResponseEntity.ok().build();
    }
}
