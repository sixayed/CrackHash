package com.example.crackhash.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ResultDto {
    private String status;
    private List<String> data;
}
