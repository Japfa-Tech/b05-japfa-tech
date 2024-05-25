package com.propensi.sikpi.DTO;

import org.mapstruct.Mapper;


import com.propensi.sikpi.DTO.request.CreateDokumenRequestDTO;
import com.propensi.sikpi.model.Dokumen;


@Mapper(componentModel = "spring")
public interface DokumenMapper {
    Dokumen createDokumenRequestDTOToDokumen(CreateDokumenRequestDTO createDokumenRequestDTO);
}
