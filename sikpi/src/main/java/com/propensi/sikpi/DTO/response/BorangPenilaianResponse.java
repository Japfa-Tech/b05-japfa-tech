package com.propensi.sikpi.DTO.response;

import java.util.List;

import com.propensi.sikpi.model.BorangPenilaian;
import com.propensi.sikpi.model.BorangPenilaianIKI;
import com.propensi.sikpi.model.Pesan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BorangPenilaianResponse {
    private List<Pesan> listPesan;
    private BorangPenilaianIKIResponseDTO borangPenilaianIKI;
    private BorangPenilaianIKUResponseDTO borangPenilaianIKU;
    private BorangPenilaianNormaResponseDTO borangPenilaianNorma;
    private Long idUserLogin;
}
