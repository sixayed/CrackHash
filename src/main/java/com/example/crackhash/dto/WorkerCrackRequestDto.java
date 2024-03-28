package com.example.crackhash.dto;

import lombok.*;

@Getter
@Setter
@ToString
public class WorkerCrackRequestDto extends CrackRequestDto {
    private int partCount;
    private int partNumber;
    private String alphabet;
    private String requestId;

    public WorkerCrackRequestDto(String hash, int maxLength, int partCount, int partNumber,
                                 String alphabet, String requestId) {
        super(hash, maxLength);
        this.partCount = partCount;
        this.partNumber = partNumber;
        this.alphabet = alphabet;
        this.requestId = requestId;
    }
}
