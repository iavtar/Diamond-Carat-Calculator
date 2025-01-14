package com.iavtar.gems.service;

import com.iavtar.gems.descriptor.DiamondAndCaratDescriptor;
import com.iavtar.gems.model.enums.DiamondCutType;
import com.iavtar.gems.model.enums.RoundDiamondMeta;
import com.iavtar.gems.model.response.DiamondCalculationResponse;
import com.iavtar.gems.model.response.MetaResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class DiamondCaratCalculationServiceImpl implements DiamondCaratCalculationService {

    @Override
    public ResponseEntity<DiamondCalculationResponse> calculate(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            List<DiamondAndCaratDescriptor> diamondAndCrateDescriptorList = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) {
                    break;
                }
                String[] values = line.split(",");
                diamondAndCrateDescriptorList.add(
                        DiamondAndCaratDescriptor.builder()
                                .cutType(values[2])
                                .diamondQuantity(values[0])
                                .size(values[4].substring(3))
                                .build()
                );
            }
            List<DiamondAndCaratDescriptor> unusedDiamondAndCrateDescriptorList = new ArrayList<>();
            Map<String, MetaResponse> responseMap = new HashMap<>();
            int totalQuantity = 0;
            double totalWeight = 0;
            int totalQuantity1 = 0;
            double totalWeight1 = 0;
            int totalQuantity2 = 0;
            double totalWeight2 = 0;
            int totalQuantity3 = 0;
            double totalWeight3 = 0;
            int totalQuantity4 = 0;
            double totalWeight4 = 0;
            for (DiamondAndCaratDescriptor descriptor : diamondAndCrateDescriptorList) {
                if (DiamondCutType.BAGUETTE_STRAIGHT.equals(DiamondCutType.fromValue(descriptor.getCutType()))) {
                    unusedDiamondAndCrateDescriptorList.add(descriptor);
                    continue;
                }
                totalQuantity += Integer.parseInt(descriptor.getDiamondQuantity().trim());
                Double caratWeight = RoundDiamondMeta.fromValue(descriptor.getSize().trim());
                double res = Integer.parseInt(descriptor.getDiamondQuantity().trim()) * caratWeight;
                totalWeight += res;
                if (Double.parseDouble(descriptor.getSize()) >= RoundDiamondMeta.MM_0_5.getMmSize() &&
                        Double.parseDouble(descriptor.getSize()) <= RoundDiamondMeta.MM_2_3.getMmSize()) {
                    totalQuantity1 += Integer.parseInt(descriptor.getDiamondQuantity().trim());
                    Double caratWeight1 = RoundDiamondMeta.fromValue(descriptor.getSize().trim());
                    double res1 = Integer.parseInt(descriptor.getDiamondQuantity().trim()) * caratWeight1;
                    totalWeight1 += res1;
                } else if ((Double.parseDouble(descriptor.getSize()) >= RoundDiamondMeta.MM_2_4.getMmSize() &&
                        Double.parseDouble(descriptor.getSize()) <= RoundDiamondMeta.MM_2_75.getMmSize())) {
                    totalQuantity2 += Integer.parseInt(descriptor.getDiamondQuantity().trim());
                    Double caratWeight2 = RoundDiamondMeta.fromValue(descriptor.getSize().trim());
                    double res2 = Integer.parseInt(descriptor.getDiamondQuantity().trim()) * caratWeight2;
                    totalWeight2 += res2;
                } else if ((Double.parseDouble(descriptor.getSize()) >= RoundDiamondMeta.MM_2_8.getMmSize() &&
                        Double.parseDouble(descriptor.getSize()) <= RoundDiamondMeta.MM_3_3.getMmSize())) {
                    totalQuantity3 += Integer.parseInt(descriptor.getDiamondQuantity().trim());
                    Double caratWeight3 = RoundDiamondMeta.fromValue(descriptor.getSize().trim());
                    double res3 = Integer.parseInt(descriptor.getDiamondQuantity().trim()) * caratWeight3;
                    totalWeight3 += res3;
                } else {
                    totalQuantity4 += Integer.parseInt(descriptor.getDiamondQuantity().trim());
                    Double caratWeight4 = RoundDiamondMeta.fromValue(descriptor.getSize().trim());
                    double res4 = Integer.parseInt(descriptor.getDiamondQuantity().trim()) * caratWeight4;
                    totalWeight4 += res4;
                }
                log.info("Cut Type: " + descriptor.getCutType() + ", Diamond Quantity: " + descriptor.getDiamondQuantity() + ", Size: " + descriptor.getSize());
            }
            log.info("Total Gems : " + totalQuantity);
            log.info("Total Weight : " + Math.round(totalWeight * 1000) / 1000.0);
            responseMap.put("0.50-2.30", MetaResponse.builder().totalGems(totalQuantity1).totalWeight(Math.round(totalWeight1 * 1000) / 1000.0).build());
            responseMap.put("2.40-2.75", MetaResponse.builder().totalGems(totalQuantity2).totalWeight(Math.round(totalWeight2 * 1000) / 1000.0).build());
            responseMap.put("2.80-3.30", MetaResponse.builder().totalGems(totalQuantity3).totalWeight(Math.round(totalWeight3 * 1000) / 1000.0).build());
            responseMap.put("Above-3.30", MetaResponse.builder().totalGems(totalQuantity4).totalWeight(Math.round(totalWeight4 * 1000) / 1000.0).build());
            return new ResponseEntity<>(DiamondCalculationResponse.builder()
                    .totalGems(totalQuantity)
                    .totalWeight(Math.round(totalWeight * 1000) / 1000.0)
                    .unused(unusedDiamondAndCrateDescriptorList)
                    .metaResponse(responseMap)
                    .build(), HttpStatus.OK);
        } catch (Exception exception) {
            log.info(exception.getMessage());
        }
        return null;
    }

//    @Override
//    public ResponseEntity<DiamondCalculationResponse> calculate(MultipartFile file) {
//        if(file.isEmpty()) {
//            return null;
//        }
//        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))){
//            List<DiamondAndCaratDescriptor> diamondAndCrateDescriptorList = new ArrayList<>();
//            List<DiamondAndCaratDescriptor> unusedDiamondAndCrateDescriptorList = new ArrayList<>();
//            int totalQuantity = 0;
//            double totalWeight = 0;
//            String line;
//            while ((line = br.readLine()) != null) {
//                if(line.isEmpty()) {
//                    break;
//                }
//                String[] values  = line.split(",");
//                diamondAndCrateDescriptorList.add(
//                        DiamondAndCaratDescriptor.builder()
//                                .cutType(values[2])
//                                .diamondQuantity(values[0])
//                                .size(values[4].substring(3))
//                                .build()
//                );
//                log.info(String.join(" | ", values));
//            }
//            for (DiamondAndCaratDescriptor descriptor : diamondAndCrateDescriptorList) {
//                if(DiamondCutType.BAGUETTE_STRAIGHT.equals(DiamondCutType.fromValue(descriptor.getCutType()))) {
//                    unusedDiamondAndCrateDescriptorList.add(descriptor);
//                    continue;
//                }
//                totalQuantity+=Integer.parseInt(descriptor.getDiamondQuantity().trim());
//                Double caratWeight = RoundDiamondMeta.fromValue(descriptor.getSize().trim());
//                double res = Integer.parseInt(descriptor.getDiamondQuantity().trim()) * caratWeight;
//                totalWeight+=res;
//                log.info("Cut Type: " + descriptor.getCutType() + ", Diamond Quantity: " + descriptor.getDiamondQuantity() + ", Size: " + descriptor.getSize());
//            }
//            log.info("Total Gems : " + totalQuantity);
//            log.info("Total Weight : " + Math.round(totalWeight * 1000) / 1000.0);
//            return new ResponseEntity<>(DiamondCalculationResponse.builder()
//                    .totalGems(totalQuantity)
//                    .totalWeight(Math.round(totalWeight * 1000) / 1000.0)
//                    .unused(unusedDiamondAndCrateDescriptorList)
//                    .build(), HttpStatus.OK);
//        } catch (Exception exception) {
//            log.info(exception.getMessage());
//        }
//        return null;
//    }

}
