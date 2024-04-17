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
@Table(name="indikator_norma")
public class IndikatorNorma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idIndikator;

    @Column(name="judul_indikator", nullable=false)
    private String judulIndikator;

    @OneToMany(mappedBy = "indikatorNorma", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<KriteriaPenilaianNorma> kriteriaPenilaianList = new ArrayList<>();

}
