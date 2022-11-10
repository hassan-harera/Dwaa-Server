package com.harera.hayatserver.controller;

import reactor.core.publisher.Mono;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harera.hayatserver.controller.utils.Param;
import com.harera.hayatserver.model.dto.model.Packaging;
import com.harera.hayatserver.service.PackagingService;

@RestController
@RequestMapping("api/v1/packaging")
public class PackagingController {

    @Autowired
    private PackagingService packagingService;

    @GetMapping("/all")
    Mono<List<Packaging>> getAllPackaging() {
        return packagingService.getPackagingList();
    }

    @GetMapping("/{packagingId}")
    Mono<Packaging> getPackaging(@PathVariable(name = Param.PACKAGING_ID) Integer packagingId) {
        return packagingService.getPackaging(packagingId);
    }
}