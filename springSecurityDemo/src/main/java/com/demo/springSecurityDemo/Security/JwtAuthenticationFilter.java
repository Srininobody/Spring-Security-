package com.demo.springSecurityDemo.Security;

import com.demo.springSecurityDemo.Service.JwtServices;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtServices jwtServices;

    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final  String authHeader = request.getHeader("Authorization");
        System.out.println("authHeader = "+authHeader);
        if(authHeader == null || !authHeader.startsWith("Bearer"))
        {

            filterChain.doFilter(request,response);
            return;
        }
        final  String jwt = authHeader.substring(7);
        System.out.println("String jwt = "+jwt);
        final  String userName = jwtServices.extractUserName(jwt);
        System.out.println("jwt user name = "+userName);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(userName !=  null && authentication == null)
        {
            System.out.println("Inside  if(userName !=  null && authentication == null)");
            //Authenticate
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

            if (jwtServices.isTokenValid(jwt,userDetails))
            {
                System.out.println("inside    if (jwtServices.isTokenValid(jwt,userDetails))");
                UsernamePasswordAuthenticationToken authenticationToken =  new
                        UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }
        }
        filterChain.doFilter(request,response);

    }
}
