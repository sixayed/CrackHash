package com.example.crackhash.controller.manager;

import com.example.crackhash.dto.CrackRequestDto;
import com.example.crackhash.dto.ResultDto;
import com.example.crackhash.service.ManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    public ResponseEntity<String> crackHash(@RequestBody CrackRequestDto request) {
        String requestId = managerService.submitCrackRequest(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(requestId);
    }

    @GetMapping(value = "/status")
    public ResponseEntity<ResultDto> getRequestStatus(@RequestParam String requestId) {
        Optional<ResultDto> status = managerService.getRequestStatus(requestId);
        return status
                .map(s -> ResponseEntity.ok().body(s))
                .orElse(ResponseEntity.notFound().build());
    }
}
