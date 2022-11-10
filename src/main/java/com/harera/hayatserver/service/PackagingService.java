package com.harera.hayatserver.service;

import reactor.core.publisher.Mono;

import java.util.List;

import com.harera.hayatserver.model.dto.model.Packaging;

public interface PackagingService {

    Mono<Packaging> getPackaging(Integer packagingId);
    Mono<List<Packaging>> getPackagingList();
}
