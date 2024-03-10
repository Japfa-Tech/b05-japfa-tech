package com.propensi.sikpi.DTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.propensi.sikpi.DTO.request.CreateUserRequestDTO;
import com.propensi.sikpi.DTO.request.UpdateUserRequestDTO;
import com.propensi.sikpi.DTO.request.UserDTO;
import com.propensi.sikpi.DTO.response.CreateUserResponseDTO;
import com.propensi.sikpi.model.UserModel;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "role", ignore = true)
    UserModel createUserRequestDTOToUserModel(CreateUserRequestDTO createUserRequestDTO);

    @Mapping(target = "role", ignore = true)
    CreateUserResponseDTO createUserResponseDTOToUserModel(UserModel userModel); 

    UserModel userDTOToUser(UserDTO userDTO);

    UserDTO userToUpdateUserRequestDTO(UserModel user);

    UserDTO userToUserDTO(UserModel user);
}
