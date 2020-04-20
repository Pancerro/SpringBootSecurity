package com.example.jpasecuritymodii.service;

import com.example.jpasecuritymodii.model.AppUser;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
     void addNewUser(AppUser appUser, HttpServletRequest request);
     void verifyToken(String token);
}
