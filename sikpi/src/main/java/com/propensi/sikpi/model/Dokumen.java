package com.propensi.sikpi.model;

import java.time.LocalDateTime;

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
@Table(name="dokumen")
public class Dokumen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDokumen;

    @Column(name="nama_dokumen", nullable=false)
    private String namaDokumen;

    @Column(name="status", nullable=false)
    private boolean status;  

    @Column(name="uploaded_date", nullable=false)
    private LocalDateTime uploadedDate;

    @Column(name="id_employee", nullable=false)
    private Long idEmployee;
}
