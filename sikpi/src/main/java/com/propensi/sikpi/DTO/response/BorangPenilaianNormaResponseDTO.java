package com.propensi.sikpi.DTO.response;

import java.util.List;

import com.propensi.sikpi.model.KriteriaScores;
import com.propensi.sikpi.model.KriteriaScoresNorma;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BorangPenilaianNormaResponseDTO {
    private String status;
    private List<KriteriaScoresNorma> kriteriaScoresNorma;
    private boolean acceptedByEvaluator;
    private Long evaluator;
    private Long evaluatedUser;
}
