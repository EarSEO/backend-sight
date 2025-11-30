package com.earseo.sight.controller;

import com.earseo.sight.common.BaseResponse;
import com.earseo.sight.service.InitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/admin/sight")
@RequiredArgsConstructor
public class SightAdminController {

    private final InitService initService;

    @PostMapping("/init")
    public ResponseEntity<BaseResponse<String>> initSight() {
        initService.initSight();
        initService.initDocent();
        return ResponseEntity.ok(BaseResponse.ok(null));
    }
}
