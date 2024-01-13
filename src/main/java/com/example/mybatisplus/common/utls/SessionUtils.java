package com.example.mybatisplus.common.utls;

import com.example.mybatisplus.model.domain.Admin;
import com.example.mybatisplus.model.domain.Worker;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

public class SessionUtils {
    private static final String USERKEY = "sessionUser";
    private static final String WORKERKEY = "sessionWoker";
    private static final String CODEKEY = "codeSession";
    private static final String TIMEKEY = "timeSession";

    public static HttpSession session() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true); // true == allow create
    }

    public static Admin getCurrentUserInfo() {
        return (Admin) session().getAttribute(USERKEY);
    }

    public static void saveCurrentUserInfo(Admin admin) {
        session().setAttribute(USERKEY, admin);
    }
    public static void saveCurrentWorkerInfo(Worker worker) {
        session().setAttribute(WORKERKEY, worker);
    }
    public static Worker getCurrentWorkerInfo() {
        return (Worker) session().getAttribute(WORKERKEY);
    }
    public static void saveSendTime(Long timeStamp) {
        session().setAttribute(TIMEKEY, timeStamp);
        //System.out.println(session().getAttribute(TIMEKEY));
    }
    public static long getSendTime() {
        return (long) session().getAttribute(TIMEKEY);
    }
    public static void saveCode(String code){
        session().setAttribute(CODEKEY, code);
    }
    public static String getCode(){
        return (String) session().getAttribute(CODEKEY);
    }
}
