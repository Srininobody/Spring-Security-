package com.demo.springSecurityDemo.Repository;

import com.demo.springSecurityDemo.Entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyUserRepository  extends JpaRepository<MyUser ,Long> {
    MyUser findByUsername(String userName);
}
