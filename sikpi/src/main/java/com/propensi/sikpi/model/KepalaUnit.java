package com.propensi.sikpi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
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
@DiscriminatorValue("kepala_unit")
public class KepalaUnit extends UserModel {
    @Column(name="list_karyawan")
    private List<Long> listKaryawan = new ArrayList<>();

    @Column(name="id_manajer")
    private Long idManajer;

    @Column(name="id_unit")
    private Long idUnit;
}
