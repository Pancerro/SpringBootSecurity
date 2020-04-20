package com.example.jpasecuritymodii.service;
import com.example.jpasecuritymodii.model.AppUser;
import com.example.jpasecuritymodii.model.VerificationToken;
import com.example.jpasecuritymodii.repo.AppUserRepo;
import com.example.jpasecuritymodii.repo.VerifictionTokeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private AppUserRepo appUserRepo;
    private PasswordEncoder passwordEncoder;
    private VerifictionTokeRepo verifictionTokeRepo;
    private MailSenderService mailSenderService;

    @Autowired
    public UserServiceImpl(AppUserRepo appUserRepo, PasswordEncoder passwordEncoder, VerifictionTokeRepo verifictionTokeRepo, MailSenderService mailSenderService) {
        this.appUserRepo = appUserRepo;
        this.passwordEncoder = passwordEncoder;
        this.verifictionTokeRepo = verifictionTokeRepo;
        this.mailSenderService = mailSenderService;
    }


    public void addNewUser(AppUser appUser, HttpServletRequest request) {
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUserRepo.save(appUser);

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(appUser, token);
        verifictionTokeRepo.save(verificationToken);

        String url = "http://" + request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath() +
                "/verifyToken?token=" + token;

        try {
            mailSenderService.sendMail(appUser.getUsername(), "Link weryfikacyjny", url, false);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


public void verifyToken(String token){
            AppUser appUser=verifictionTokeRepo.findByValue(token).getAppUser();
            appUser.setEnabled(true);
            appUserRepo.save(appUser);
        }
    }


