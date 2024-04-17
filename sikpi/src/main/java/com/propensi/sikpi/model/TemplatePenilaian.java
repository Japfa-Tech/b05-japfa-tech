package com.propensi.sikpi.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
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
@Table(name="template_penilaian")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@SQLDelete(sql = "UPDATE template_penilaian SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted=false")
public class TemplatePenilaian {
    @Id
    @Column(name = "id_template")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTemplatePenilaian;

    @Column(name="nama_template")
    private String namaTemplate;

    @OneToMany(mappedBy = "templatePenilaian", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnore
    private List<KriteriaPenilaian> listKriteria = new ArrayList<KriteriaPenilaian>();

    @Column(name="bobot_total")
    private Integer bobotTotal;

    // @Column(name="status")
    // private String status;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = Boolean.FALSE;

    @Column(name ="evaluated_user")
    private Long evaluatedUser;
}

