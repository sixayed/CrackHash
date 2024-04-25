package org.example.manager.repository;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.example.models.dto.CrackResultDto;
import org.example.models.model.StatusEnum;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class CrackRepository {
    private final Map<String, CrackResultDto> statusMap = new ConcurrentHashMap<>();

    public void createResult(@NonNull String uuid) {
        statusMap.put(uuid, new CrackResultDto(StatusEnum.IN_PROGRESS.name(), new ArrayList<>()));
        log.info("Create request with uuid = {}", uuid);
    }

    public Optional<CrackResultDto> getResult(@NonNull String uuid) {
        return Optional.ofNullable(statusMap.get(uuid));
    }

    public void updateResult(@NonNull String uuid, @NonNull CrackResultDto status) {
        if(statusMap.containsKey(uuid)) {
            statusMap.put(uuid, status);
        }

        throw new NoSuchElementException("No such request with ID" + uuid);
    }

    public void updateStatus(@NonNull String uuid, @NonNull String status) {
        if(statusMap.containsKey(uuid)) {
            getResult(uuid).ifPresent(
                    resultDto -> {
                        resultDto.setStatus(status);
                        statusMap.put(uuid, resultDto);
                    });
        }
    }

    public void updateData(@NonNull String uuid, @NonNull List<String> data) {
        if(statusMap.containsKey(uuid)) {
            getResult(uuid).ifPresent(
                    resultDto -> {
                        resultDto.setData(data);
                        statusMap.put(uuid, resultDto);
                    }
            );
        }
    }

    public void addToData(@NonNull String uuid, @NonNull List<String> data) {
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
