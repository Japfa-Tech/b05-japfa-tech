package com.propensi.sikpi.DTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.propensi.sikpi.DTO.request.CreateRiwayatPenugasanRequestDTO;
import com.propensi.sikpi.DTO.request.UpdateUserRequestDTO;
import com.propensi.sikpi.DTO.request.UserDTO;
import com.propensi.sikpi.model.RiwayatPenugasan;
import com.propensi.sikpi.model.UserModel;

@Mapper(componentModel = "spring")
public interface RiwayatPenugasanMapper {
    RiwayatPenugasan createRiwayatPenugasanRequestDTOToRiwayatPenugasan(CreateRiwayatPenugasanRequestDTO createRiwayatPenugasanRequestDTO);
}
