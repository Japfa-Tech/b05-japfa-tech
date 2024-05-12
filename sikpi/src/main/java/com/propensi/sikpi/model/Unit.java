package com.propensi.sikpi.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="unit_model")
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nama_unit")
    private String namaUnit;

    @OneToMany(mappedBy = "unit", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<Karyawan> users;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "unitKu")
    private KepalaUnit kepalaUnit;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "unitMan")
    private Manajer manajer;
}
