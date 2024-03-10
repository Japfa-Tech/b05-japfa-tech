package com.propensi.sikpi.DTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.propensi.sikpi.DTO.request.CreateRiwayatJabatanRequestDTO;
import com.propensi.sikpi.DTO.request.UpdateUserRequestDTO;
import com.propensi.sikpi.DTO.request.UserDTO;
import com.propensi.sikpi.model.RiwayatJabatan;
import com.propensi.sikpi.model.UserModel;

@Mapper(componentModel = "spring")
public interface RiwayatJabatanMapper {
    RiwayatJabatan createRiwayatJabatanRequestDTOToRiwayatJabatan(CreateRiwayatJabatanRequestDTO createRiwayatJabatanRequestDTO);
}
