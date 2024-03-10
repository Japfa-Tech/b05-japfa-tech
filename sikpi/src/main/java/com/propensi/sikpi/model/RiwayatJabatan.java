package com.propensi.sikpi.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="riwayat_jabatan")
public class RiwayatJabatan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRiwayatJabatan;

    @Column(name="jabatan", nullable=false)
    private String jabatan;

    @Column(name="uploaded_date", nullable=false)
    private LocalDateTime uploadedDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user", referencedColumnName = "id", nullable = false)
    private UserModel idUser;

    @Lob
    @Column(name = "dokumen_jabatan", nullable = false)
    private byte[] dokumen;
    
    @Transient // For Document
    private String dokumenBase64;
}
