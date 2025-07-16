package com.library.management_system.controller;

import com.library.management_system.dto.FineResponseDto;
import com.library.management_system.payload.ApiResponse;
import com.library.management_system.service.FineService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/fines")
public class FineController {

    private final FineService fineService;

    public FineController(FineService fineService) {
        this.fineService = fineService;
    }

    @GetMapping("/id/{fineId}")
    public ApiResponse<FineResponseDto> getFineById(@PathVariable("fineId") Integer fineId) {
        FineResponseDto fine = fineService.getFineById(fineId);
        return ApiResponse.success(fine);
    }

    @PatchMapping("/status/{fineId}")
    public ApiResponse<FineResponseDto> updatePaymentStatus(@PathVariable("fineId") Integer fineId) {
        FineResponseDto fine = fineService.updatePaymentStatus(fineId);
        return ApiResponse.success(fine);
    }


}
