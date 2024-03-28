package com.example.crackhash.service;

import com.example.crackhash.dto.ResultDto;
import com.example.crackhash.model.StatusEnum;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class ResultService {
    private final Map<String, ResultDto> statusMap = new ConcurrentHashMap<>();

    public void createResult(@NotNull String uuid) {
        statusMap.put(uuid, new ResultDto(StatusEnum.IN_PROGRESS.name(), new ArrayList<>()));
        log.info("Create request with uuid = {}", uuid);
    }

    public Optional<ResultDto> getResult(@NotNull String uuid) {
        return Optional.ofNullable(statusMap.get(uuid));
    }

    public void updateResult(@NotNull String uuid, @NotNull ResultDto status) {
        if(statusMap.containsKey(uuid)) {
            statusMap.put(uuid, status);
        }

        throw new NoSuchElementException("No such request with ID" + uuid);
    }

    public void updateStatus(@NotNull String uuid, @NotNull String status) {
        if(statusMap.containsKey(uuid)) {
            getResult(uuid).ifPresent(
                    resultDto -> {
                        resultDto.setStatus(status);
                        statusMap.put(uuid, resultDto);
                    });
        }
    }

    public void updateData(@NotNull String uuid, @NotNull List<String> data) {
        if(statusMap.containsKey(uuid)) {
            getResult(uuid).ifPresent(
                    resultDto -> {
                        resultDto.setData(data);
                        statusMap.put(uuid, resultDto);
                    }
            );
        }
    }

    public void addToData(@NotNull String uuid, @NotNull List<String> data) {
        if(statusMap.containsKey(uuid)) {
            getResult(uuid).ifPresent(
                    resultDto -> {
                        List<String> currentData = resultDto.getData();
                        currentData.addAll(data);

                        resultDto.setData(currentData);
                        statusMap.put(uuid, resultDto);
                    }
            );
        }
    }
}
