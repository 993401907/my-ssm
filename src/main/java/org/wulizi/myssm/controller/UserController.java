package org.wulizi.myssm.controller;

import org.wulizi.myssm.annotations.Autowired;
import org.wulizi.myssm.annotations.Controller;
import org.wulizi.myssm.annotations.RequestMapping;
import org.wulizi.myssm.entity.User;
import org.wulizi.myssm.service.UserService;

import java.util.List;

/**
 * @author wulizi
 */
@Controller
@RequestMapping("/hello")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/getall")
    public List<User> getUser() {
        return userService.getUserList();
    }
}
