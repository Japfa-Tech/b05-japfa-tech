package com.propensi.sikpi.model;

import jakarta.persistence.Table;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rapor")
public class Rapor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rapor", unique = true)
    private long idRapor;

    @Column(name = "sign_evaluated", nullable = false)
    private boolean signEvaluated = false;

    @Column(name = "sign_penilai", nullable = false)
    private boolean signPenilai = false;

    @Column(name = "sign_penyetuju", nullable = false)
    private boolean signPenyetuju = false;

    @ManyToOne
    @JoinColumn(name = "id_sdm", referencedColumnName = "id", nullable = true)
    private SDM sdm;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user", referencedColumnName = "id", nullable = false)
    private UserModel evaluatedUser;

    @Column(name = "sign_evaluated_time", nullable = true)
    private LocalDateTime signEvaluatedTime;

    @Column(name = "sign_penilai_time", nullable = true)
    private LocalDateTime signPenilaiTime;

    @Column(name = "sign_penyetuju_time", nullable = true)
    private LocalDateTime signPenyetujuTime;

}
