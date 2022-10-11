package com.harera.dwaaserver.service;

import com.harera.dwaaserver.dto.model.Packaging;
import reactor.core.publisher.Mono;

import java.util.List;

public interface PackagingService {

    Mono<Packaging> getPackaging(Integer packagingId);
    Mono<List<Packaging>> getPackagingList();
}
