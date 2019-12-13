package com.fudan.xk.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.fudan.xk.util.Md5TokenGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import org.springframework.web.bind.annotation.*;
import com.fudan.xk.base.controller.BaseApiController;
import com.fudan.xk.base.result.Result;
import com.fudan.xk.base.result.ResultCode;
import com.fudan.xk.model.User;
import com.fudan.xk.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class LoginApiController extends BaseApiController {

    private static Logger log = LoggerFactory.getLogger(LoginApiController.class);

    @Autowired
    Md5TokenGenerator tokenGenerator;
    @Autowired
    public UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    @RequestBody String user, BindingResult bindingResult,
    public Result<Object> login(@RequestParam("username")String username, @RequestParam("password") String password){
        JSONObject result = new JSONObject();
        log.info("username=" + username+ ";password=" + password);
        User user = userService.findByUsername(username);
        if(user!=null && user.getPassword().equals(password)){
            Jedis jedis = new Jedis();
            String token = tokenGenerator.generate(username, password);
            jedis.set(username, token);
            jedis.set(token, username);
            result.put("token", token);
            return Result.success(result);
        }

//            return Result.success(sysRoleService.selectRoleByUserId(user1.getUid()).getRole());
        return Result.failure(ResultCode.USER_LOGIN_ERROR);

    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Result<Object> getInfo(@RequestParam("token")String token){
        List<String> roles = new ArrayList<>();
        String avatar = "https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png";
        JSONObject result = new JSONObject();
        Jedis jedis = new Jedis();
        String username = jedis.get(token);
        if(username != null){
            User user = userService.findByUsername(username);
            String role = user.getRole();
            roles.add(role);
            result.put("name", username);
            result.put("avatar", avatar);
            result.put("roles", roles);
            return Result.success(result);
        }
        else
            return Result.failure(ResultCode.UNAVAILABLE_TOKEN);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Result<Object> logout(@RequestParam("token")String token){
        Jedis jedis = new Jedis();
        String username = jedis.get(token);
        if(username != null){
            jedis.del(token);
            jedis.del(username);
            return Result.success();
        }
        return Result.failure();
    }

}
