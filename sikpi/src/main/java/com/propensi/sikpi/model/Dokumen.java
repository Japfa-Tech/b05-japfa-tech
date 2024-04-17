package com.propensi.sikpi.model;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dokumen")
public class Dokumen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dokumen", unique = true)
    private long idDokumen;

    @Column(name = "nama_dokumen", nullable = false)
    private String namaDokumen;

    // 0: Submitted, 1:Approved, 2:Rejected
    @Column(name = "status", nullable = false)
    private int status = 0;

    @Column(name = "uploaded_date", nullable = false)
    private LocalDateTime uploadedDate;

    @Column(name = "reviewed_date", nullable = true)
    private LocalDateTime reviewedDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user", referencedColumnName = "id", nullable = false)
    private UserModel idUser;

    @Lob
    @Column(name = "dokumen", nullable = false)
    private byte[] dokumen;
    @Transient // For Document
    private String dokumenBase64;
}

