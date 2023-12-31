package com.ning.api.user;

import com.ning.constant.ServiceNameConstants;
import com.ning.factory.user.RemoteUserFallbackFactory;
import com.ning.model.Result;
import com.ning.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户服务远程调用接口
 * 获取用户详细信息
 */
@FeignClient(contextId = "remoteUserService", path = "/system", value = ServiceNameConstants.USER_SERVICE, fallbackFactory = RemoteUserFallbackFactory.class)
public interface RemoteUserService {

    @PostMapping(value = "/user/selectUserByUsername")
    Result<User> selectUserByUsername(@RequestParam("username") String username);

}
