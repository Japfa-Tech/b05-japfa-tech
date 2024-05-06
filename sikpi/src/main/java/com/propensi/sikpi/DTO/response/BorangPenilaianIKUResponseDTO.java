package com.propensi.sikpi.DTO.response;

import java.util.List;

import com.propensi.sikpi.model.KriteriaScores;
import com.propensi.sikpi.model.KriteriaScoresIKU;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BorangPenilaianIKUResponseDTO {
    private String status;
    private List<KriteriaScoresIKU> kriteriaScoresIKU;
    private boolean acceptedByEvaluator;
    private Long evaluator;
    private Long evaluatedUnit;
}
