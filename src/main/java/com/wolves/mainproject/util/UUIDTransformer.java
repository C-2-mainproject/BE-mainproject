package com.wolves.mainproject.util;

import com.wolves.mainproject.exception.Common.CommonInvalidInputValue;
import com.wolves.mainproject.exception.wordStorage.OfficialWordStorageNotFoundException;

import java.util.UUID;

public class UUIDTransformer {
    private UUID uuid;

    public UUIDTransformer(String uuid){
        setUUID(uuid);
    }

    public long uuidToAbsLongInRange(long range){
        if (range == 0){
            throw new OfficialWordStorageNotFoundException();
        }
        return uuidToAbsLong() % range;
    }

    public long uuidToAbsLong(){
        return makeAbsLong(uuid.hashCode());
    }

    public int uuidToAbsIntInRange(int range){
        return (int) uuidToAbsLongInRange(range);
    }

    public void setUUID(String uuid) {
        try{
            this.uuid = UUID.fromString(uuid);
        }
        catch (Exception e){
            throw new CommonInvalidInputValue();
        }
    }

    private long makeAbsLong(long value){
        return Math.abs(value);
    }
}
