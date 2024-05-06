package com.propensi.sikpi.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("borang_iku")
public class BorangPenilaianIKU extends BorangPenilaian{

    @Column(name = "evaluated_unit")
    private Long evaluatedUnit;

    @Column(name = "evaluator")
    private Long evaluator;

    // @Column(name = "accepted_by_evaluated_unit")
    // private boolean acceptedByEvaluatedUnit;

    @Column(name = "accepted_by_evaluator")
    private boolean acceptedByEvaluator;

    @OneToMany(mappedBy = "borangPenilaian")
    private List<KriteriaScoresIKU> kriteriaScoresIKU = new ArrayList<>();
}
