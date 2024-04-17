package com.propensi.sikpi.DTO.response;

import java.util.List;

import com.propensi.sikpi.model.KriteriaScores;

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
    private List<KriteriaScores> kriteriaScores;
    private boolean acceptedByEvaluator;
    private Long evaluator;
    private Long evaluatedUser;
}
