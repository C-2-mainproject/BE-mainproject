package com.wolves.mainproject.handler.interceptor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Component
public class MyInterceptor implements HandlerInterceptor {
    public static List<ThreadLock> threadLocks;

    public MyInterceptor(){
        threadLocks = new ArrayList<>();
    }
    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response,
            Object obj) throws Exception {

        if (isRoomIdRequest(request)){
            String roomId = request.getParameter("roomId");
            boolean hasRoomId = checkThreadLocks(roomId);
            if (!hasRoomId){
                setThreadLock(roomId);
            }
        }

        return true;
    }

    private boolean isRoomIdRequest(HttpServletRequest request){
        return request.getParameter("roomId") != null;
    }

    // return true if thread was locked
    private boolean checkThreadLocks(String roomId){
        for (int i = 0; i < threadLocks.size(); i++) {
            if (threadLocks.get(i).unlockIfMatchedRoomId(roomId)){
                threadLocks.remove(threadLocks.get(i));
                return true;
            }
        }
        return false;
    }

    private void setThreadLock(String roomId) throws InterruptedException {
        ThreadLock threadLock = new ThreadLock();
        threadLock.setRoomId(roomId);
        threadLocks.add(threadLock);
        threadLock.lock();
    }

}