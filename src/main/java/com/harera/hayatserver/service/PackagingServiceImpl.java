package com.harera.hayatserver.service;

import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harera.hayatserver.model.dto.model.Packaging;
import com.harera.hayatserver.model.entity.PackagingEntity;
import com.harera.hayatserver.mapper.PackagingMapper;
import com.harera.hayatserver.repository.PackagingRepository;

@Service
public class PackagingServiceImpl implements PackagingService {

    @Autowired
    private PackagingRepository packagingRepository;

    @Override
    public Mono<Packaging> getPackaging(Integer packagingId) {
        return Mono.fromCallable(() -> {
            Optional<PackagingEntity> packagingEntity = packagingRepository.getPackaging(packagingId);
            return packagingEntity.map(PackagingMapper.INSTANCE::map).orElse(null);
        });
    }

    @Override
    public Mono<List<Packaging>> getPackagingList() {
        return Mono.fromCallable(() -> {
            List<PackagingEntity> packagingEntityList = packagingRepository.getAllPackaging();
            return packagingEntityList.stream().map(PackagingMapper.INSTANCE::map).toList();
        });
    }
}