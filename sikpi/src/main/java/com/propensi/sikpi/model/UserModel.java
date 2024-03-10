package com.propensi.sikpi.model;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
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

    @OneToMany(mappedBy = "idUser", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private List<Dokumen> listDokumen;

    @Column(name="report")
    private Byte report;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_role", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Role role;
}
