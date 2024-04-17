package com.propensi.sikpi.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="borang_penilaian")
public class BorangPenilaian {

    @Id
    @Column(name = "id_borang")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBorangPenilaian;

    @Column(name = "id_template")
    private Long idTemplate;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "borangPenilaian")
    private List<KriteriaScores> kriteriaScores = new ArrayList<>();
    
    private List<Long> listAkses = new ArrayList<>();
}

