package com.propensi.sikpi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "aktivitas")
public class Aktivitas {
    @Id
    private UUID id = UUID.randomUUID();

    @Column(name = "aksi", nullable = false)
    private String aksi;

}
