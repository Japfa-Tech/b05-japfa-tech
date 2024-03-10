package com.propensi.sikpi.DTO.request;

import com.propensi.sikpi.model.UserModel;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Data
@Setter
public class CreateDokumenRequestDTO {

    private String namaDokumen;
    private UserModel idUser;
    private byte[] dokumen;
    private String dokumenBase64;
}
