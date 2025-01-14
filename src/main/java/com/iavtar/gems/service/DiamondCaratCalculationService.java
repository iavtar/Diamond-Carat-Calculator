package com.iavtar.gems.service;

import com.iavtar.gems.model.response.DiamondCalculationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface DiamondCaratCalculationService {
    ResponseEntity<DiamondCalculationResponse> calculate(MultipartFile file);
}
