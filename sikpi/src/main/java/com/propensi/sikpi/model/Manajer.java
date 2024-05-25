package com.propensi.sikpi.model;

import java.util.List;

import jakarta.persistence.Column;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
public class Manajer extends UserModel {

    @Column(name = "kepalaUnit", nullable = true)
    private Long kepalaUnit;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_unit_manajer")
    private Unit unitMan;
}
