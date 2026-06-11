package com.example.prac.Service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.prac.dto.LoginRequest;
import com.example.prac.dto.SignupRequest;
import com.example.prac.dto.VerifyOtpRequest;
import com.example.prac.model.Pu;
import com.example.prac.model.User;
import com.example.prac.repository.PuRepository;
import com.example.prac.repository.UserRepository;
import com.example.prac.model.Role;
import com.example.prac.security.JwtService;

@Service
public class AuthService {
    @Autowired
    PasswordEncoder passwordencoder;
   

    @Autowired
    UserRepository userrepo;
    @Autowired
    OtpService otpservice;
    @Autowired
    PuRepository purepo;
    @Autowired
    JwtService jwtservice;

    public Pu signupRequest(SignupRequest sq){
        if(userrepo.findByEmail(sq.getEmail()).isPresent()){
    throw new RuntimeException("Email already registered");
}       
         
         Optional<Pu> existing = purepo.findByEmail(sq.getEmail());

            if(existing.isPresent()){
            purepo.deleteByEmail(sq.getEmail());
        }
         
       
        
        Pu pu=new Pu();
        pu.setUsername(sq.getUsername());
        pu.setEmail(sq.getEmail());
        pu.setPassword(sq.getPassword());
        otpservice.sendOtp(sq.getEmail());
        return purepo.save(pu);

    }
   public String  enterOtp(VerifyOtpRequest verify){
    if(otpservice.verifyOtp(verify.getEmail(),verify.getOtp())==true){
        User user=new User();
        Optional<Pu> data=purepo.findByEmail(verify.getEmail());
        if(!data.isPresent()){
            return "Signup data not found";
}

        Pu detail=data.get();
        user.setUsername(detail.getUsername());
        user.setEmail(detail.getEmail());
        user.setPassword(passwordencoder.encode(detail.getPassword()));
        user.setRole(Role.User);
        user.setVerified(true);
        userrepo.save(user);
        purepo.deleteByEmail(detail.getEmail());
        return "User is saved succesfully";



    }
    return "bhai kuch toh gadbad h otp verify ni hua";
      
   }
    
   public String login(LoginRequest req){
    String a=(req.getPassword());

    Optional<User> b=userrepo.findByEmail(req.getEmail());
    if (!b.isPresent()){
        return "Invalid Detail";
    }


   
   User det=b.get();
   if ((passwordencoder.matches(
            a,
            det.getPassword()))){
     String token =jwtservice.generateToken(det.getEmail());
    return token;

   }
   return "login failed";
   }
  



}