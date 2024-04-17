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
@DiscriminatorValue("borang_norma")
public class BorangPenilaianNorma extends BorangPenilaian{

    @Column(name = "evaluated_user")
    private Long evaluatedUser;

    @Column(name = "evaluator")
    private Long evaluator;

    @Column(name = "accepted_by_evaluator")
    private boolean acceptedByEvaluator;

    @OneToMany(mappedBy = "borangPenilaian")
    private List<KriteriaScoresNorma> kriteriaScoresNorma = new ArrayList<>();
}