package com.propensi.sikpi.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgetPasswordDTO {
    private String username;
    private String password;
    private String confirmPassword;

}
