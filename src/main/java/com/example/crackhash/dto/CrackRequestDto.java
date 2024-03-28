package com.example.crackhash.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CrackRequestDto {
    private String hash;
    private int maxLength;
}
