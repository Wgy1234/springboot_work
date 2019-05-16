package com.qf.controller;

import com.qf.entity.User;
import com.qf.service.IUserService;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @user lao王
 * @date 2019/5/16 17:30
 * @varsion 1.0
 */
@Controller
@RequestMapping("/userController")
public class UserController {

    @Autowired
    private IUserService userService;


    @RequestMapping(value="/getRegister")
    public String getRegister(){

        return "register";
    }

    @RequestMapping(value="/sendCode")
    public String sendCode(String email,HttpServletRequest request,ModelMap map){

        //随机产生四位数的验证码
        String ran= (int) ((Math.random()*9000)+1000)+"";

        //将验证码发送至邮箱
        HtmlEmail htmlEmail = new HtmlEmail();


        try {

            htmlEmail.setHostName("smtp.163.com");
            htmlEmail.setCharset("utf-8");
            htmlEmail.addTo(email);   //收件地址
            htmlEmail.setFrom("15079410769@163.com","aa");
            htmlEmail.setAuthentication("15079410769@163.com", "lw980707");
            htmlEmail.setSubject("老王通信");
            htmlEmail.setMsg("尊敬的用户,你本次的注册验证码为:"+ran);
            htmlEmail.send();
            map.put("msg", "验证码已发送");
        } catch (EmailException e) {
            e.printStackTrace();
            map.put("msg", "验证码发送失败");
        }

        //验证码放入session中
        request.getSession().setAttribute("ran", ran);

        return "register";
    }

    @RequestMapping(value="/register")
    public String register(User user,HttpServletRequest request){
        String ran = (String) request.getSession().getAttribute("ran");
        String code = request.getParameter("code");
        if(code.equals(ran)){
            userService.addUser(user);
            return "index";
        }else{
            return "register";
        }
    }

    @RequestMapping(value="/getEmailByUsername")
    public String getEmailByUsername(String username,ModelMap map){
        User user = userService.getEmailByUsername(username);
        if(user == null){
            map.put("message", "你输入的用户名无效");
            return "serchPwd";
        }else{
            // 将改密发送至邮箱
            HtmlEmail htmlEmail = new HtmlEmail();

            try {

                htmlEmail.setHostName("smtp.163.com");    //邮箱协议
                htmlEmail.setCharset("utf-8");   //邮件编码
                htmlEmail.addTo(user.getEmail()); // 收件地址
                htmlEmail.setFrom("15079410769@163.com", "aa");   //发送地址，发件人
                htmlEmail.setAuthentication("15079410769@163.com", "lw980707");  //邮箱地址,授权码
                htmlEmail.setSubject("老王通信");    //邮件显示标题
                String password = user.getPassword();
                String url = "http://localhost:8080/userController/getUserPassword/" + user.getId();
                htmlEmail.setMsg("您当前的密码为:" + password + "<br>" + "继续修改密码请点击:" + "<a href=" + url + ">" + url + "</a>");
                htmlEmail.send();
            } catch (EmailException e) {
                e.printStackTrace();
            }
            String email = user.getEmail().replace(user.getEmail().subSequence(3, 9), "*****");
            map.put("msg", "邮件已发送至邮箱" + email + ",请注意查收");

            return "showMsg";
        }
    }

    @RequestMapping("/getUserPassword/{id}")
    public String getUserPassword(@PathVariable Integer id, ModelMap map){
        User user = userService.getUserById(id);
        map.put("user", user);
        return "changePwd";
    }

    @RequestMapping("/changePassword")
    public String updatePassword(Integer id,String password){
        userService.updatePasswordById(id,password);
        return "index";
    }

    @RequestMapping("/login")
    public String login(String username, String password, HttpServletRequest request){
        User user = userService.login(username,password);

        if(user != null){
            request.getSession().setAttribute("user", user);
            return "main";
        }else{
            return "index";
        }
    }

    @RequestMapping("/toSerchPwd")
    public String toSerchPwd(){
        return "serchPwd";
    }

    @RequestMapping("/exit")
    public String exit(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return "index";
    }
}

