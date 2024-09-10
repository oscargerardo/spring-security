package com.security.spring_security.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class SaveUser implements Serializable {

    @Size(min = 4)
    private String name;

    @Size(min = 4)
    private String userName;

    @Size(min = 8)
    private String password;

    @Size(min = 8)
    private String repitedPassword;

}
