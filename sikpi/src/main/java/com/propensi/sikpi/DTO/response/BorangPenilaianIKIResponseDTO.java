package com.propensi.sikpi.DTO.response;

import java.util.List;

import com.propensi.sikpi.model.KriteriaScores;
import com.propensi.sikpi.model.KriteriaScoresIKI;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BorangPenilaianIKIResponseDTO {
    private String status;
    private List<KriteriaScoresIKI> kriteriaScoresIKI;
    private boolean acceptedByEvaluator;
    private Long evaluator;
    private Long evaluatedUser;
}
