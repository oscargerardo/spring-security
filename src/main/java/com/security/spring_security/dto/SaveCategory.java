package com.security.spring_security.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SaveCategory implements Serializable {

    static final long serialVersionUID = 587466547862354687L;

    @NotBlank
    private String name;

}
