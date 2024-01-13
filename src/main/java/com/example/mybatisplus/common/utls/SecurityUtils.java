package com.example.mybatisplus.common.utls;

import com.example.mybatisplus.model.domain.Admin;
import com.example.mybatisplus.model.domain.Worker;
import com.example.mybatisplus.model.dto.UserInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityUtils {
    /**
     * 获取当前用户
     *
     * @return
     */
    public static Admin getCurrentUserInfo() {
        Admin userInfo = SessionUtils.getCurrentUserInfo();
        //模拟登录
        if (userInfo == null) {
            userInfo = new Admin();
            userInfo.setLoginName("模拟");
        }

        return userInfo;
    }

    public static UserInfoDTO getUserInfo() {
        //从session获取当前用户
        Worker userInfo = SessionUtils.getCurrentWorkerInfo();
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        //模拟登录
        if (userInfo == null) {
            userInfo = new Worker();
            userInfo.setName("模拟用户");
            userInfoDTO.setId(1);
            userInfoDTO.setName("模拟用户");
            userInfoDTO.setUserType(1);
        }else{
            userInfoDTO.setId(userInfo.getId());
            userInfoDTO.setName(userInfo.getName());
            userInfoDTO.setUserType(userInfo.getType());
        }
        return userInfoDTO;
    }
}
