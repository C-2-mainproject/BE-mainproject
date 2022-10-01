package com.wolves.mainproject.handler.interceptor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThreadLock{
    private String roomId;
    private boolean isLocked = true;
    private boolean timeEnd = true;

    public synchronized void lock()
            throws InterruptedException{
        while(isLocked){
            wait(5000);
            if (timeEnd){
                MyInterceptor.threadLocks.remove(this);
                throw new RuntimeException("에러문!!");
            }
        }
        isLocked = true;
    }

    public synchronized void unlock(){
        isLocked = false;
        timeEnd = false;
        notify();
    }

    public boolean unlockIfMatchedRoomId(String roomId){
        if (this.roomId.equals(roomId)){
            unlock();
            return true;
        }
        return false;
    }
}