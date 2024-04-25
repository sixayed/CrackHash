package org.example.manager.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.manager.service.ManagerService;
import org.example.models.dto.CrackRequestDto;
import org.example.models.dto.CrackResultDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController()
@RequestMapping("/api/hash")
public class ManagerController {

    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @PostMapping("/crack")
    public ResponseEntity<String> submitCrackRequest(@RequestBody CrackRequestDto request) {
        String requestId = managerService.submitCrackRequest(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(requestId);
    }

    @GetMapping(value = "/status")
    public ResponseEntity<CrackResultDto> getRequestStatus(@RequestParam String requestId) {
        return managerService.getRequestStatus(requestId);
    }
}
