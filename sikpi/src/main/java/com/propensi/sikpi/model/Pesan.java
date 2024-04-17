package com.propensi.sikpi.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="pesan")
public class Pesan {
    @Id
    @Column(name = "id_pesan")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPesan;

    @Column(name="id_pengirim", nullable=true)
    private Long idPengirim;

    @Column(name="isi_pesan", nullable=true)
    private String isiPesan;

    @Column(name="waktu_pesan", nullable=false)
    private LocalDateTime waktuPesan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_template")
    @JsonIgnore
    private TemplatePenilaian templatePenilaian;

}
