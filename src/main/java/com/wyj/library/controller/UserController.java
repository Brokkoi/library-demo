package com.wyj.library.controller;

import com.wyj.library.model.User;
import com.wyj.library.model.UserExample;
import com.wyj.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    UserExample example;

    @RequestMapping(value="/login")
    public ModelAndView login(@RequestParam("loginName") String loginName,
                              @RequestParam("password") String password,
                              HttpSession session,
                              ModelAndView mav){
        // 调用业务逻辑组件判断用户是否可以登录
        List<User> user = userService.checkUser(loginName, password);
        System.out.println(loginName);
        if(user.size()>0){
            // 将用户保存到HttpSession当中
            System.out.println("HttpSession");
            session.setAttribute("loginUser", loginName);
            // 客户端跳转到main页面
            mav.setViewName("redirect:/main");
        }else{
            // 设置登录失败提示信息
            System.out.println("设置登录失败提示信息");
            mav.addObject("loginErrMsg", "登录名或密码错误!请重新输入!");
            // 服务器内部跳转到登录页面
            mav.setViewName("loginForm");
        }
        return mav;
    }

    @GetMapping("/list")
    public String toUserList(Model model){
        List<User> allUser = userService.getAllUser(example);
        int total = userService.selectCount();
        
        model.addAttribute("list",allUser);
        model.addAttribute("utotal",total);
        System.out.println("数据总量为----"+total);
        return "user/user_list";
    }

    @GetMapping("/userSearch")
    public Map<String, Object> userSearch(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                          @RequestParam(value = "limit",defaultValue = "5")Integer limit
                                          ) throws Exception{
        Map<String , Object> map = new HashMap<>();
        User user = new User();
        List<User> allUser = userService.getAllUser(example);
//        int total = userService.selectCount();
//        List<User> userlist = userService.searchUserList(page,limit,user);
        map.put("code",0);
        map.put("msg","");
        map.put("count",allUser.size());
        map.put("data",allUser);
        return map;
    }

    @GetMapping("/add")
    public String toUserAdd(){
        return "user/user_add";
    }

    @PostMapping("/add")
    public String addUser(User user){
        userService.insertUser(user);
        System.out.println("添加管理员信息"+user);
        return "user/user_list";

    }

    @GetMapping("/edit")
    public String toEdit(Integer id){
        return "user/user_edit";
    }

    @GetMapping("/edit/{id}")
    public String toEditUser(@PathVariable("id") Integer id,Model model){
        User oneUser = userService.getOneUser(id);
        System.out.println(oneUser);
        model.addAttribute("user",oneUser);
        return "user/user_edit";
    }


    @PutMapping("/edit")
    public String updateUser(User user){
        System.out.println("修改前的数据"+user);
        userService.updateUser(user);

        return "redirect:/user/list";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id){
        System.out.println(id);
        if(id!=null){
            userService.deleteUser(id);
        }
        return "redirect:/user/list";
    }

//    @GetMapping("/delete/{id}")
//    public String deleteUser(@PathVariable("id")Integer id){
//        System.out.println("删除数据的id值"+id);
//        userService.deleteUser(id);
//        return "redirect:/user/list";
//    }
//    @GetMapping("/hello")
//        public String hello(){
//        return "hello";
//        }

//    @RequestMapping("/test")
//    public String test(){
//        return "welcome";
//    }
}
