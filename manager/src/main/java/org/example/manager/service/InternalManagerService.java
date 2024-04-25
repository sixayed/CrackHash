package org.example.manager.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.manager.repository.CrackRepository;
import org.example.models.dto.WorkerResponseDto;
import org.example.models.model.StatusEnum;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InternalManagerService {

    private final CrackRepository crackRepository;

    public void handleWorkerResponse(@NonNull WorkerResponseDto workerResponse) {
        for(String data : workerResponse.getData()) {
            log.info("DATA FROM WORKER: {}", data);
        }

        // TODO обновить статус зарпроса, но это только для одного воркера ))))))
        crackRepository.updateStatus(workerResponse.getRequestId(), StatusEnum.READY.name());
        crackRepository.addToData(workerResponse.getRequestId(), workerResponse.getData());
    }
}
