package com.bob.controller;

import java.util.UUID;

import com.bob.service.impl.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bob.pojo.Users;
import com.bob.pojo.vo.UsersVO;
import com.bob.service.IUserService;
import com.bob.utils.MD5Utils;
import com.bob.utils.ResponseJSONResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@RestController
@Api(value="用户注册登录的接口",tags= {"注册和登录的controller"})
public class RegistAndLoginController extends BasicController{
    @Autowired
    private UserService userService;
    
    @PostMapping("/regist")
    @ApiOperation(value="用户注册",notes="用户注册的接口")
	public ResponseJSONResult regist(@RequestBody Users user) throws Exception {
		//判断用户名和密码是不是空
		if(StringUtils.isBlank(user.getUsername())||StringUtils.isBlank(user.getPassword())) {
			return ResponseJSONResult.errorMsg("用户名和密码不能为空");
		}
		//判断用户名是否存在
		boolean usernameIsExist=userService.queryUsernameExist(user.getUsername());
		
		//保存用户信息
		if(!usernameIsExist) {
			user.setNickname(user.getUsername());
			user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
			user.setFansCounts(0);
			user.setFollowCounts(0);
			user.setReceiveLikeCounts(0);
			userService.saveUser(user);
		}else {
			return ResponseJSONResult.errorMsg("用户名已经存在");
		}
		user.setPassword("");
		UsersVO userVo=setUserRedisSession(user);
		return ResponseJSONResult.ok(userVo);
	}
    
    @PostMapping("/login")
    @ApiOperation(value="用户登录的接口",notes="用户登录的接口")
    public ResponseJSONResult login(@RequestBody Users user) throws Exception {
    	String username=user.getUsername();
    	String password=user.getPassword();
		//判断用户名和密码是不是空
		if(StringUtils.isBlank(username)||StringUtils.isBlank(password)) {
			return ResponseJSONResult.errorMsg("用户名和密码不能为空");
		}
		//判断用户是否存在
		Users userResult=userService.queryUserForLogin(username,MD5Utils.getMD5Str(password));
    	if(userResult!=null) {
    		userResult.setPassword("");
    		UsersVO userVo=setUserRedisSession(userResult);
    		return ResponseJSONResult.ok(userVo);
    	}else {
    		return ResponseJSONResult.errorMsg("用户名或者密码错误");
    	}
		
    }
    public UsersVO setUserRedisSession(Users userModel) {
    	String uniqueToken=UUID.randomUUID().toString();
        redis.set(USER_REDIS_SESSION+":"+userModel.getId(), uniqueToken, 60*30*1000);
        UsersVO userVo=new UsersVO();
        BeanUtils.copyProperties(userModel, userVo);
        userVo.setUserToken(uniqueToken);
        return userVo;
    }
}
