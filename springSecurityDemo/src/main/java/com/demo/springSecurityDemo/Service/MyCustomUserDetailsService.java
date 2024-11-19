package com.demo.springSecurityDemo.Service;

import com.demo.springSecurityDemo.Entity.MyUser;
import com.demo.springSecurityDemo.Repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Objects;
@Component
public class MyCustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MyUserRepository myUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       MyUser user = myUserRepository.findByUsername(username);
       if(Objects.isNull(user))
       {
        System.out.println("User Not Available");
        throw  new UsernameNotFoundException("user not found");
       }
        return new MyCustomUserDetails(user);
    }
}
