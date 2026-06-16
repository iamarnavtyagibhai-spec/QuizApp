package com.example.prac.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.prac.model.Otp;
import com.example.prac.repository.OtpRepository;

@Service
public class OtpService {
    @Autowired
    EmailService emails;
    @Autowired
    OtpRepository otprepo;


     // generate otp
    public int generateotp(){
        Random random=new Random();
        return 100000+random.nextInt(900000);


    }
    public Otp saveOtp(String email){
        Otp otp=new Otp();
        otp.setEmail(email);
        otp.setOtp(generateotp());
        otp.setExpirytime(LocalDateTime.now().plusMinutes(5));
        return otprepo.save(otp);



    }
    public String sendOtp(String email){
        Otp otp=saveOtp(email);
    

        


        emails.sendOtp(email, String.valueOf(otp.getOtp()));
        return "Otp is send ab mauj kaat";
    }

    public boolean verifyOtp(String email,int otp){
        Optional<Otp> o=otprepo.findByEmail(email);
        if (!o.isPresent()){
            return false;
        }
        Otp savedOtp=o.get();
        if (LocalDateTime.now().isAfter(savedOtp.getExpirytime())){
              otprepo.deleteByEmail(email);
                return false;
                
            }
        
   


        if( otp==savedOtp.getOtp()){
            otprepo.deleteByEmail(email);
            return true;
        }
        return false;
    }
    

}