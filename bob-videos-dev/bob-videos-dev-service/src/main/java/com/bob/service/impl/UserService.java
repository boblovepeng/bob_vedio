package com.bob.service.impl;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bob.mapper.UsersMapper;
import com.bob.pojo.Users;
import com.bob.service.IUserService;

@Service
public class UserService implements IUserService{
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    Sid sid;
    @Transactional(propagation=Propagation.SUPPORTS)
	@Override
	public boolean queryUsernameExist(String username) {
		Users user=new Users();
		user.setUsername(username);
		Users result=usersMapper.selectOne(user);
		return !(result==null);
	}
    @Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void saveUser(Users user) {
		user.setId(sid.nextShort());
		usersMapper.insert(user);
		
	}
	@Override
	public Users queryUserForLogin(String username, String password) {
		Users user=new Users();
		user.setUsername(username);
		user.setPassword(password);
		return usersMapper.selectOne(user);
	}

}
