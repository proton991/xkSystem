package com.fudan.xk.service;

import com.fudan.xk.VO.UserVO;
import com.fudan.xk.model.Student;
import com.fudan.xk.model.Teacher;
import com.fudan.xk.model.User;

import java.util.List;

public interface UserService {
	/**通过username查找用户信息;*/
	User findByUsername(String username);

	int addUsers(List<User> userList);




//	public UserVO getUser(String username);

//	public void saveUserToRedisByToken(UserVO dbUser, String token);
//
//	public Object getUserFromRedisByToken(String token);

}
