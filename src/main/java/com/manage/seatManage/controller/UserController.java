package com.manage.seatManage.controller;
import com.manage.seatManage.Constant.UserConstant;
import com.manage.seatManage.common.BaseResponse;
import com.manage.seatManage.common.ErrorCode;
import com.manage.seatManage.common.ResultUtils;
import com.manage.seatManage.exception.BusinessException;
import com.manage.seatManage.model.domain.User;
import com.manage.seatManage.model.request.UserAddRequest;
import com.manage.seatManage.model.request.UserLoginRequest;
import com.manage.seatManage.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 *
 * @author wyw
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
@Slf4j
public class UserController implements UserConstant {

    @Resource
    private UserService userService;



    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest httpServletRequest) {
        //1.校验
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //2.得到账号密码
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        //3.校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //4.得到结果
        User result = userService.userLogin(userAccount, userPassword, httpServletRequest);

        return ResultUtils.success(result);
    }

    @PostMapping("/update")
    public BaseResponse<Integer> userUpdate(@RequestBody User user, HttpServletRequest httpServletRequest) {
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(httpServletRequest);

        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NO_LOGIN);
        }

        int result = userService.updateUser(user, loginUser);

        return ResultUtils.success(result);

    }

    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest httpServletRequest) {

        Object userObj = httpServletRequest.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR, "用户当前状态获取失败");
        }

        long userId = currentUser.getId();
        User user = userService.getById(userId);


        return ResultUtils.success(user);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> userDelete(@RequestBody long id, HttpServletRequest httpServletRequest) {

        if (!userService.isAdmin(httpServletRequest)) {
            throw new BusinessException(ErrorCode.NO_AUTH, "没有权限");
        }
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "查询的id不存在");
        }
        //已经开启了逻辑删除
        boolean result = userService.removeById(id);

        return ResultUtils.success(result);

    }

    @PostMapping("/add")
    public BaseResponse<Long> userAdd(@RequestBody User user, HttpServletRequest httpServletRequest) {
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(httpServletRequest);
        long userId = userService.userAdd(user,loginUser);

        return ResultUtils.success(userId);

    }

}
