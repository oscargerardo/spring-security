package com.security.spring_security.persistence.util;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum Role {

    ROLE_ADMINISTRATOR (Arrays.asList(

        RolPermission.READ_ALL_PRODUCTS,
        RolPermission.READ_ONE_PRODUCT,
        RolPermission.CREATE_ONE_PRODUCT,
        RolPermission.UPDATE_ONE_PRODUCT,
        RolPermission.DISABLE_ONE_PRODUCT,

        RolPermission.READ_ALL_CATEGORIES,
        RolPermission.READ_ONE_CATEGORY,
        RolPermission.CREATE_ONE_CATEGORY,
        RolPermission.UPDATE_ONE_CATEGORY,
        RolPermission.DISABLE_ONE_CATEGORY,

        RolPermission.READ_MY_PROFILE

    )),
    ROLE_ASSISTANT_ADMINISTRATOR (Arrays.asList(

            RolPermission.READ_ALL_PRODUCTS,
            RolPermission.READ_ONE_PRODUCT,
            RolPermission.DISABLE_ONE_PRODUCT,

            RolPermission.READ_ALL_CATEGORIES,
            RolPermission.READ_ONE_CATEGORY,
            RolPermission.DISABLE_ONE_CATEGORY,

            RolPermission.READ_MY_PROFILE

    )),

    ROLE_CUSTOMER(List.of(

            RolPermission.READ_MY_PROFILE

    ));

    private List<RolPermission> permissions;

    Role(List<RolPermission> permission){
        this.permissions = permission;
    }

}
