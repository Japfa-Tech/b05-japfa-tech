package com.propensi.sikpi.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "kriteria_penilaian")
public class KriteriaPenilaian {
    @Id
    @Column(name = "id_kriteria")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idKriteriaPenilaian;

    @Column(name = "judul_kriteria", nullable = false)
    private String judulKriteria;

    @Column(name = "bobot", nullable = false)
    private Integer bobot;

    @Column(name = "skor")
    private Integer skor;

    @ManyToOne
    @JoinColumn(name = "id_template")
    @JsonIgnore
    private TemplatePenilaian templatePenilaian;

    @OneToMany(mappedBy = "kriteria", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<KriteriaScores> kriteriaScoresList = new ArrayList<>();
}

