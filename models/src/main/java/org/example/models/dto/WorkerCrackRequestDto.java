package org.example.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class WorkerCrackRequestDto {
    private CrackRequestDto crackRequestDto;
    private int partCount;
    private int partNumber;
    private String alphabet;
    private String requestId;

}