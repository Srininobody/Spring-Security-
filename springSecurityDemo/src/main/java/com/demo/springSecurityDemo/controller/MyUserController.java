package com.demo.springSecurityDemo.controller;

import com.demo.springSecurityDemo.Entity.MyUser;
import com.demo.springSecurityDemo.Repository.MyUserRepository;
import com.demo.springSecurityDemo.Service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
public class MyUserController {

    @Autowired
   private MyUserRepository myUserRepository;
    @Autowired
    private MyUserService myUserService;

    @PostMapping("/registeUser")
    public MyUser register(@RequestBody MyUser user)
    {
        System.out.println("inside the /registeUser controller ");
     // return    myUserRepository.save(user);

        return myUserService.register(user);
    }
    @PostMapping("/login")
    public String login(@RequestBody MyUser user)
    {
       return myUserService.verrify(user);


    }

    @GetMapping("/getUser")
    public List<MyUser> getAll()
    {
        return  myUserRepository.findAll();
    }
}
