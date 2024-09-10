package com.security.spring_security.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterUser implements Serializable {

    private Long id;

    private String userName;

    private String name;

    private String rol;

    private String jwt;
}
