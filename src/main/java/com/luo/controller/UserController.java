package com.luo.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.luo.model.User;
import com.luo.service.UserService;

@Controller  
public class UserController {  
	
    @Resource  
    private UserService userService;  
      
    @RequestMapping("/")    
    public ModelAndView getIndex(){      
        ModelAndView mav = new ModelAndView("index");   
        User user = userService.selectUserById(1);  
        mav.addObject("user", user);   
        return mav;    
    }
    
    @RequestMapping(value="/test",method=RequestMethod.GET)    
    public String test(Model model,HttpServletRequest request){
    	String path=request.getSession().getServletContext().getContextPath();
    	model.addAttribute("name", path);
        return "index";    
    }    
  
}  
