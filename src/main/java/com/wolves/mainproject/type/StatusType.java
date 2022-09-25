package com.wolves.mainproject.type;

import com.wolves.mainproject.domain.user.User;

public enum StatusType {
    PUBLIC(true), PRIVATE(false), OFFICIAL(true);
    private boolean value;

    StatusType(boolean value){
        this.value = value;
    }

    public static StatusType getStatusByUser(boolean status, User user){
        if (status && user.getRole().equals(RoleType.ROLE_ADMIN))
            return StatusType.OFFICIAL;

        return getStatus(status);
    }

    public boolean getValue(){
        return value;
    }

    public static StatusType getStatus(boolean status){
        if (status)
            return StatusType.PUBLIC;

        return StatusType.PRIVATE;
    }
}
