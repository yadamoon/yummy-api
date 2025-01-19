package com.bytepulse.yummy.dto.users;

import lombok.Data;

@Data
public class ResetPassword {

    private String email;
    private String otp;
    private String newPassword;

}
