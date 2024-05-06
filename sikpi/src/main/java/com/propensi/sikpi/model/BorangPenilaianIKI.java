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
@DiscriminatorValue("borang_iki")
public class BorangPenilaianIKI extends BorangPenilaian{

    @Column(name = "evaluated_user")
    private Long evaluatedUser;

    @Column(name = "evaluator")
    private Long evaluator;

    @Column(name = "accepted_by_evaluated_user")
    private boolean acceptedByEvaluatedUser;

    @Column(name = "template_accepted")
    private boolean templateAccepted;

    @Column(name = "accepted_by_evaluator")
    private boolean acceptedByEvaluator;

    @OneToMany(mappedBy = "borangPenilaian")
    private List<KriteriaScoresIKI> kriteriaScoresIKI = new ArrayList<>();

}
