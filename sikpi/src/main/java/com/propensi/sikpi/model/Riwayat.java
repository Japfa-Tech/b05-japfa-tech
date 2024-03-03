package com.propensi.sikpi.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name="riwayat")
public class Riwayat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRiwayat;

    @Column(name="jabatan", nullable=false)
    private String jabatan;

    @Column(name="penugasan", nullable=false)
    private String penugasan;

    @Column(name="uploaded_date", nullable=false)
    private LocalDateTime uploadedDate;

    @Column(name="id_employee", nullable=false)
    private Long idEmployee;
}
