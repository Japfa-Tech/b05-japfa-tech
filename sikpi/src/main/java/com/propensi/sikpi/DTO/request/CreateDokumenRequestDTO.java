package com.propensi.sikpi.DTO.request;

import com.propensi.sikpi.model.UserModel;

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
