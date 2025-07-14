package com.emrecelik.demo8.service.impl;

import com.emrecelik.demo8.io.ProfileRequest;
import com.emrecelik.demo8.io.ProfileResponse;
import com.emrecelik.demo8.model.UserModel;
import com.emrecelik.demo8.repo.UserRepository;
import com.emrecelik.demo8.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;

    @Override
    public ProfileResponse createProfile(ProfileRequest profileRequest) {
        UserModel newUser = convertUserToModel(profileRequest);

        if (!userRepository.existsByEmail(newUser.getEmail())) {
            newUser = userRepository.save(newUser);
            return createToProfileResponse(newUser);
        }

        throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
    }

    private ProfileResponse createToProfileResponse(UserModel newUser) {
        return ProfileResponse.builder()
                .name(newUser.getName())
                .email(newUser.getEmail())
                .userId(newUser.getUserId())
                .userId(newUser.getUserId())
                .isVerified(newUser.getIsVerified())
                .build();
    }

    private UserModel convertUserToModel(ProfileRequest request) {
        return UserModel.builder()
                .email(request.getEmail())
                .userId(UUID.randomUUID().toString())
                .name(request.getName())
                .password(request.getPassword())
                .isVerified(false)
                .resetOtpExpireAt(0L)
                .verifyOtp(null)
                .verifyOtpExpireAt(0L)
                .resetOtp(null)
                .build();
    }
}
