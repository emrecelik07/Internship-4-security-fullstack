package com.emrecelik.demo8.service;

import com.emrecelik.demo8.io.ProfileRequest;
import com.emrecelik.demo8.io.ProfileResponse;
import org.springframework.context.annotation.Profile;

public interface ProfileService {

    public ProfileResponse createProfile(ProfileRequest profileRequest);
}
