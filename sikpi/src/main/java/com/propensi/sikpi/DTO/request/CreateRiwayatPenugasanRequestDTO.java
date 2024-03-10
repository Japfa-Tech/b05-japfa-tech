package com.propensi.sikpi.DTO.request;

import com.propensi.sikpi.model.UserModel;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Data;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class CreateRiwayatPenugasanRequestDTO {
    
    private String penugasan;
    private UserModel idUser;
    private byte[] dokumen;
    private String dokumenBase64;
}