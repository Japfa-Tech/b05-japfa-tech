package com.propensi.sikpi.model;

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
@Table(name = "kriteria_scores_norma")
public class KriteriaScoresNorma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_borang")
    private BorangPenilaian borangPenilaian;

    // @OneToOne
    // @JoinColumn(name = "id_kriteria", referencedColumnName = "id_kriteria")
    // private KriteriaPenilaian kriteria;
    
    @ManyToOne // Add this annotation
    @JoinColumn(name = "id_kriteria_norma")
    private KriteriaPenilaianNorma kriteriaNorma;

    private Integer score;
}
