package com.fudan.xk;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fudan.xk.model.User;
import com.fudan.xk.service.UserService;
import redis.clients.jedis.Jedis;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

	@Autowired
	public UserService userService;
	

	
	
	@Test
	public void test() {
		User user = new User("andy", "123456");

	}

	@Test
	public void testGetUser() {

	}
	
//	@Test
//	public void testPassword() {
//		UserVO user = userService.getUser("andy");
//		String password = MD5Util.inputToDb("123456", user.getDbflag());
//		Assert.assertEquals(password, user.getPassword());
//	}
	
	@Test
	public void testPutRedis() {
		User user = new User("andy","123456");
		Jedis jedis = new Jedis();
//		jedis.set(user.getUsername(), "token");
//		jedis.set("token", user.getUsername());
		jedis.del("token");
	}
}
