package com.iavtar.gems.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class DiamondCalculationResponse {

    private int totalGems;

    private double totalWeight;

    private Object unused;

    private Object metaResponse;

}
