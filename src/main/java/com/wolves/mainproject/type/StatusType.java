package com.wolves.mainproject.type;

public enum StatusType {
    PUBLIC(true), PRIVATE(false), OFFICIAL(true);
    private boolean value;

    StatusType(boolean value){
        this.value = value;
    }

    public static StatusType findByBoolean(boolean status){
        if (status)
            return StatusType.PUBLIC;

        return StatusType.PRIVATE;
    }
}
