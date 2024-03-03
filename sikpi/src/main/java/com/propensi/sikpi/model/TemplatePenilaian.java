package com.propensi.sikpi.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name="template_penilaian")
public class TemplatePenilaian {
    @Id
    @Column(name = "id_template")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTemplatePenilaian;

    // @Column(name="id_kriteria", nullable=false)
    @OneToMany(mappedBy = "template_penilaian", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnore
    private List<KriteriaPenilaian> listKriteria;

    @Column(name="bobot_total", nullable=false)
    private Integer bobotTotal;

    @Column(name="status", nullable=false)
    private String status;
}
