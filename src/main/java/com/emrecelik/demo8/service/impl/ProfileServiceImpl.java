package com.emrecelik.demo8.service.impl;

import com.emrecelik.demo8.io.ProfileRequest;
import com.emrecelik.demo8.io.ProfileResponse;
import com.emrecelik.demo8.model.UserModel;
import com.emrecelik.demo8.repo.UserRepository;
import com.emrecelik.demo8.service.EmailService;
import com.emrecelik.demo8.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    public ProfileResponse createProfile(ProfileRequest profileRequest) {
        UserModel newUser = convertUserToModel(profileRequest);

        if (!userRepository.existsByEmail(newUser.getEmail())) {
            newUser = userRepository.save(newUser);
            return createToProfileResponse(newUser);
        }

        throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
    }

    @Override
    public ProfileResponse getProfile(String email) {
        UserModel existingUser = userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User not found" + email));

    return createToProfileResponse(existingUser);

    }

    @Override
    public void sendResetOtp(String email) {
        UserModel existingUser = userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User not found" + email));
        String otp = String.valueOf(ThreadLocalRandom.current().nextInt(100000,1000000));
        long expiryTime = System.currentTimeMillis() + 5 * 60 * 1000;
        existingUser.setResetOtp(otp);
        existingUser.setResetOtpExpireAt(expiryTime);
        userRepository.save(existingUser);

        try {
            emailService.sendResetOtpEmail(email, otp);
        }catch (Exception e){
            throw new RuntimeException("Unable to send email");
        }
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
                .password(passwordEncoder.encode(request.getPassword()))
                .isVerified(false)
                .resetOtpExpireAt(0L)
                .verifyOtp(null)
                .verifyOtpExpireAt(0L)
                .resetOtp(null)
                .build();
    }
}
