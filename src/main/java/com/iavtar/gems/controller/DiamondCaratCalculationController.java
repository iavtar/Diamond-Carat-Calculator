package com.iavtar.gems.controller;

import com.iavtar.gems.model.response.DiamondCalculationResponse;
import com.iavtar.gems.service.DiamondCaratCalculationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequestMapping("/api/v1/dc")
@RestController
@AllArgsConstructor(onConstructor_ = @Autowired)
public class DiamondCaratCalculationController {

    private final DiamondCaratCalculationService diamondCaratCalculationService;

    @PostMapping("/cal")
    public ResponseEntity<DiamondCalculationResponse> calculate(@RequestParam("file") MultipartFile file) {
        return diamondCaratCalculationService.calculate(file);
    }

}
