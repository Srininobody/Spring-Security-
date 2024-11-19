package com.demo.springSecurityDemo.Service;

import com.demo.springSecurityDemo.Entity.MyUser;
import com.demo.springSecurityDemo.Repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MyUserService {
    @Autowired
    private MyUserRepository myUserRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private  JwtServices jwtServices;

    public MyUser register(MyUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return myUserRepository.save(user);
    }

    public String verrify(MyUser user) {

      Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
      //  MyUser user1 =  myUserRepository.findByUsername(user.getUsername());
        if(authentication.isAuthenticated())
        {
            return jwtServices.generateToken(user);
        }
        return "Failure";
    }
}
