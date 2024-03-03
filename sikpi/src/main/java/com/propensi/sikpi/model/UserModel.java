package com.propensi.sikpi.model;


import java.time.LocalDate;
import java.util.UUID;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name="user_model")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nama_lengkap", nullable=false)
    private String namaLengkap;

    @Column(name="divisi", nullable=false)
    private String divisi;

    @Column(name="title", nullable=false)
    private String title;

    @Column(name="username", nullable=false)
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="birth_date", nullable=false)
    private LocalDate birthDate;

    @Column(name="nrp", nullable=false)
    private String nrp;

    @Column(name="dokumen")
    private Byte dokumen;

    @Column(name="report", nullable=false)
    private Byte report;
}
