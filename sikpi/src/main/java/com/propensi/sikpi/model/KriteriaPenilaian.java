package com.propensi.sikpi.model;


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
@Table(name="kriteria_penilaian")
public class KriteriaPenilaian {
    @Id
    @Column(name="id_kriteria")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idKriteriaPenilaian;

    @Column(name="judul_kriteria", nullable=false)
    private String judulKriteria;

    @Column(name="bobot", nullable=false)
    private Integer bobot;

    // @Column(name="id_kriteria", nullable=false)
    @OneToMany(mappedBy = "kriteria_penilaian", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnore
    private List<IndikatorPenilaian> listIndikator;

    @ManyToOne
    @JoinColumn(name = "id_template")
    @JsonIgnore
    private TemplatePenilaian templatePenilaian;
}
