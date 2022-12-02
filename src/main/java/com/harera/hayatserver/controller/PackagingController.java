package com.harera.hayatserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harera.hayatserver.service.PackagingService;

@RestController
@RequestMapping("api/v1/packaging")
public class PackagingController {

    @Autowired
    private PackagingService packagingService;

}
