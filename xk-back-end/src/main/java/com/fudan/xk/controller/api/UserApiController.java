package com.fudan.xk.controller.api;

import com.fudan.xk.base.controller.BaseApiController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: 99615
 * @Date: 2019/11/8 01:27
 * @Description:
 */
@RestController
public class UserApiController extends BaseApiController {

    @RequestMapping("/admin")
    public String admin(){
        return "admin page";
    }
}
