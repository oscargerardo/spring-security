package com.security.spring_security.persistence.util;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum RoleEnum {

    ADMINISTRATOR(Arrays.asList(

        RolPermissionEnum.READ_ALL_PRODUCTS,
        RolPermissionEnum.READ_ONE_PRODUCT,
        RolPermissionEnum.CREATE_ONE_PRODUCT,
        RolPermissionEnum.UPDATE_ONE_PRODUCT,
        RolPermissionEnum.DISABLE_ONE_PRODUCT,

        RolPermissionEnum.READ_ALL_CATEGORIES,
        RolPermissionEnum.READ_ONE_CATEGORY,
        RolPermissionEnum.CREATE_ONE_CATEGORY,
        RolPermissionEnum.UPDATE_ONE_CATEGORY,
        RolPermissionEnum.DISABLE_ONE_CATEGORY,

        RolPermissionEnum.READ_MY_PROFILE

    )),
    ASSISTANT_ADMINISTRATOR(Arrays.asList(

            RolPermissionEnum.READ_ALL_PRODUCTS,
            RolPermissionEnum.READ_ONE_PRODUCT,
            RolPermissionEnum.UPDATE_ONE_PRODUCT,

            RolPermissionEnum.READ_ALL_CATEGORIES,
            RolPermissionEnum.READ_ONE_CATEGORY,
            RolPermissionEnum.UPDATE_ONE_CATEGORY,

            RolPermissionEnum.READ_MY_PROFILE

    )),

    CUSTOMER(List.of(

            RolPermissionEnum.READ_MY_PROFILE

    ));

    private List<RolPermissionEnum> permissions;

    RoleEnum(List<RolPermissionEnum> permission){
        this.permissions = permission;
    }

}
