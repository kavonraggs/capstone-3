package org.yearup.controllers;

import org.springframework.web.bind.annotation.*;
import org.yearup.data.ProfileDao;
import org.yearup.data.UserDao;
import org.yearup.models.Profile;

import java.security.Principal;

@RestController
@RequestMapping("/profile")
public class ProfileController
{
    private ProfileDao profileDao;
    private UserDao userDao;

    public ProfileController(ProfileDao profileDao, UserDao userDao) {
        this.profileDao = profileDao;
        this.userDao = userDao;
    }

    public Profile create(Profile profile) {
        return profileDao.create(profile);
    }

    @GetMapping
    public Profile getByUserId(Principal principal) {
        int userId =  userDao.getByUserName(principal.getName()).getId();
        return profileDao.getByUserId(userId);
    }

    @PutMapping
    public Profile updateProfile(@RequestBody Profile profile,
                                 Principal principal) {
        int userId =  userDao.getByUserName(principal.getName()).getId();
        profile.setUserId(userId);
        return profileDao.updateProfile(profile);
    }



}
