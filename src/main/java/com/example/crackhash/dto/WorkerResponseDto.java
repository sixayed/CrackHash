package com.example.crackhash.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class WorkerResponseDto {
    private String requestId;
    private List<String> data;
}
