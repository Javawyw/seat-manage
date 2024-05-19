package com.manage.seatManage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.manage.seatManage.common.ErrorCode;
import com.manage.seatManage.exception.BusinessException;
import com.manage.seatManage.mapper.UserMapper;
import com.manage.seatManage.model.domain.User;
import com.manage.seatManage.model.request.UserAddRequest;
import com.manage.seatManage.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.manage.seatManage.Constant.UserConstant.ADMIN_ROLE;
import static com.manage.seatManage.Constant.UserConstant.USER_LOGIN_STATE;

/**
* @author lenovo
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2024-05-16 23:47:59
*/
@Service
@Log4j2
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 登录
     *
     * @param userAccount        登录账号
     * @param userPassword       登录密码
     * @param httpServletRequest
     * @return
     */
    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest httpServletRequest) {
        //1.校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号和密码不能为空");
        }
        //账号不小于4位和不大于10位
        if (userAccount.length() < 4 || userAccount.length() > 10) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号长度不正确");
        }
        //密码不小于8位
        if (userPassword.length() < 8 || userPassword.length() > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码长度不正确");
        }

        //账户不能含有特殊字符
        String validPattern = "[ _`~!@#$%^&*()+=|{}':;,\\[\\].<>/?！￥…（）—【】‘；：”“’。，、？]|\n|\r|\t";
        //正则表达式
        Pattern pattern = Pattern.compile(validPattern);
        Matcher matcher = pattern.matcher(userAccount);
        boolean result = matcher.find();

        if (result) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "存在特殊字符");
        }

        //2.查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", userPassword);
        User user = userMapper.selectOne(queryWrapper);

        //用户不存在
        if (user == null) {
            log.info("user login failed,userAccount can not match");
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        //3.记录用户的登录态
        httpServletRequest.getSession().setAttribute(USER_LOGIN_STATE, user);

        return user;
    }

    /**
     * 修改用户信息
     *
     * @param user      要修改的用户
     * @param loginUser 当前登录的用户
     * @return
     */
    @Override
    public int updateUser(User user, User loginUser) {
        //1校验
        long userId = user.getId();
        if (userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //2如果不是管理员，只能自己修改自己
        //3只有管理员才可以修改学生信息
        if (!isAdmin(loginUser) && userId != loginUser.getId()) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }

        User oldUser = userMapper.selectById(userId);
        //4没有该用户
        if (oldUser == null) {
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR);
        }

        return userMapper.updateById(user);
    }

    /**
     * 获取当前用户信息
     *
     * @param httpServletRequest
     * @return
     */
    @Override
    public User getLoginUser(HttpServletRequest httpServletRequest) {
        //校验
        if (httpServletRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //得到当前用户通过session
        Object userObj = httpServletRequest.getSession().getAttribute(USER_LOGIN_STATE);
        if (userObj == null) {
            throw new BusinessException(ErrorCode.NO_LOGIN);
        }
        User currentUser = (User) userObj;
        return currentUser;
    }

    @Override
    public boolean isAdmin(HttpServletRequest httpServletRequest) {
        if (httpServletRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Object userObj = httpServletRequest.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        //如果用户为空，且不是管理员
        if (user == null || user.getUserRole() != ADMIN_ROLE) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isAdmin(User loginUser) {
        if (loginUser == null || loginUser.getUserRole() != ADMIN_ROLE) {
            return false;
        }
        return true;
    }

    @Override
    public long userAdd(User user, User loginUser) {
        //1.请求参数是否为空
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //2.是否登录
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NO_LOGIN);
        }
        //3.是否为管理员
        if (!isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        //保存信息
        boolean saveResult = this.save(user);
        //返回id值
        Long newUserId = user.getId();
        return newUserId;


    }

}




