package com.bob.service;

import com.bob.pojo.Users;

public interface IUserService {
   public  boolean queryUsernameExist(String username);
   public  void saveUser(Users user);
   public Users queryUserForLogin(String username,String password);
}
