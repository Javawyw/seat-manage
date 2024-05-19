package com.manage.seatManage.service;

import com.manage.seatManage.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.manage.seatManage.model.request.UserAddRequest;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author lenovo
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2024-05-16 23:47:59
*/
public interface UserService extends IService<User> {
    /**
     * 用户登录
     *
     * @param userAccount  登录账号
     * @param userPassword 登录密码
     * @return 返回user信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest httpServletRequest);
    /**
     * 修改信息
     *
     */
    int updateUser(User user, User loginUser);


    /**
     * 得到当前登陆的用户
     */
    User getLoginUser(HttpServletRequest httpServletRequest);

    /**
     * 是否为管理员
     */
    boolean isAdmin(HttpServletRequest httpServletRequest);

    /**
     * 是否为管理员
     * @param loginUser
     * @return
     */
    boolean isAdmin(User loginUser);

    /**
     * 添加用户信息
     * @param user
     * @param
     * @return
     */
    long userAdd(User user, User  loginUser);
}
