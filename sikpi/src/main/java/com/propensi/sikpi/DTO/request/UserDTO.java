package com.propensi.sikpi.DTO.request;

import java.time.LocalDate;
import java.util.List;

import com.propensi.sikpi.model.Cabinet;
import com.propensi.sikpi.model.Dokumen;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import lombok.Data;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Data
@Setter
public class UserDTO {
    private Long id;
    // private String namaLengkap;
    // private String divisi;
    // private String title;
    // private String username;
    // private String password;
    // private LocalDate birthDate;
    private String nrp;
    private List<Dokumen> listDokumen;
    // private Byte report;
}
