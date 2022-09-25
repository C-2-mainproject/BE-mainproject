package com.wolves.mainproject.util;

import java.util.UUID;

public class UUIDTransformer {
    private UUID uuid;

    public UUIDTransformer(String uuid){
        this.uuid = UUID.fromString(uuid);
    }

    public long uuidToAbsLongInRange(long range){
        return uuidToAbsLong() % range;
    }

    public long uuidToAbsLong(){
        return makeAbsLong(uuid.hashCode());
    }

    public void setUUID(String uuid) {
        this.uuid = UUID.fromString(uuid);
    }

    private long makeAbsLong(long value){
        return Math.abs(value);
    }
}
