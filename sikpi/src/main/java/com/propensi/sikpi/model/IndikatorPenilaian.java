package com.propensi.sikpi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Entity;
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
@Table(name="indikator_penilaian")
public class IndikatorPenilaian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idIndikatorPenilaian;

    @Column(name="judul_indikator", nullable=false)
    private String judulIndikator;

    @Column(name="skor", nullable=false)
    private Integer skor;

    @ManyToOne
    @JoinColumn(name = "id_kriteria")
    @JsonIgnore
    private Long idKriteriaPenilaian;
}
