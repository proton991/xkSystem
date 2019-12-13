package com.fudan.xk.service.impl;

import javax.transaction.Transactional;

import com.fudan.xk.model.Student;
import com.fudan.xk.model.Teacher;
import com.fudan.xk.repository.StudentRepository;
import com.fudan.xk.repository.TeacherRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fudan.xk.VO.UserVO;
import com.fudan.xk.model.User;
import com.fudan.xk.repository.UserRpository;
import com.fudan.xk.service.UserService;
import redis.clients.jedis.Jedis;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRpository userRpository;

 	@Override
	public User findByUsername(String username) {
		return userRpository.findByUsername(username);
	}

	@Override
	public int addUsers(List<User> userList){
		return userRpository.saveAll(userList).size();
	}

//	@Override
//	public UserVO getUser(String username) {
//		UserVO userVO = new UserVO();
//		User user = userRedis.get("username");
//		if(user == null){
//			user = userRpository.findByUsername(username);
//			if(user != null){
//				userRedis.put(user.getUsername(), user, -1);
//			}else{
//				return null;
//			}
//		}
//		BeanUtils.copyProperties(user, userVO);
//
//		return userVO;
//	}

//	@Override
//	public void saveUserToRedisByToken(UserVO dbUser, String token) {
//		User user = new User();
//		BeanUtils.copyProperties(dbUser, user);
//		userRedis.put(token, user, 3600);
//	}
//
//	@Override
//	public Object getUserFromRedisByToken(String token) {
//		return userRedis.get(token);
//	}

}
