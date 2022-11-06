package com.harera.dwaaserver.service;

import reactor.core.publisher.Mono;

import java.util.List;

import com.harera.dwaaserver.dto.model.Packaging;

public interface PackagingService {

    Mono<Packaging> getPackaging(Integer packagingId);
    Mono<List<Packaging>> getPackagingList();
}
