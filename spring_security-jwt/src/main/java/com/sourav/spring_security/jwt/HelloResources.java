package com.sourav.spring_security.jwt;

import com.sourav.spring_security.jwt.models.AuthenticationRequest;
import com.sourav.spring_security.jwt.models.AuthenticationResponse;
import com.sourav.spring_security.jwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloResources {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailService userDetailService;
    @Autowired
    private JwtUtil jwtTokenUtil;
    @RequestMapping("/hello")
    public String home() {
        return "<H1>Hello World!<H1>";
    }

    @RequestMapping(value = "/authenticate",method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)throws Exception{
          try{
              authenticationManager.authenticate(
                      new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                              authenticationRequest.getPassword()));
          }catch (BadCredentialsException e){
              throw new Exception("Incorrect Username Password",e);
          }
          final UserDetails userDetails=userDetailService.loadUserByUsername(authenticationRequest.getUsername());
          final String jwt=jwtTokenUtil.generateToken(userDetails);
          AuthenticationResponse response=new AuthenticationResponse(jwt);
          return ResponseEntity.ok(response);
    }
}
