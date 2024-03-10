package com.propensi.sikpi.DTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.propensi.sikpi.DTO.request.CreateDokumenRequestDTO;
import com.propensi.sikpi.DTO.request.UpdateUserRequestDTO;
import com.propensi.sikpi.DTO.request.UserDTO;
import com.propensi.sikpi.model.Dokumen;
import com.propensi.sikpi.model.UserModel;

@Mapper(componentModel = "spring")
public interface DokumenMapper {
    Dokumen createDokumenRequestDTOToDokumen(CreateDokumenRequestDTO createDokumenRequestDTO);
}
