package org.sid.ebankingbackend.services;

import org.sid.ebankingbackend.error.BadRequestException;
import org.sid.ebankingbackend.models.AccountStatusStats;
import org.sid.ebankingbackend.models.User;
import org.sid.ebankingbackend.payload.request.EditProfileRequest;
import org.sid.ebankingbackend.payload.request.UpdatePasswordRequest;
import org.sid.ebankingbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Override
    public List<User> listAccounts() {
        return this.userRepository.findAll();
    }

    @Override
    public void banAccount(String userName) {
        User user = this.userRepository.findByUsername(userName).get();
        user.setDisabled(true);
        this.userRepository.save(user);
    }

    @Override
    public void enableAccount(String userName) {
        User user = this.userRepository.findByUsername(userName).get();
        user.setDisabled(false);
        this.userRepository.save(user);
    }

    @Override
    public User getPrincipal(String userName) {
        return this.userRepository.findByUsername(userName).get();
    }

    @Override
    public String editProfile(String userName, EditProfileRequest editProfileRequest) {
        User user = this.userRepository.findByUsername(userName).get();
        this.checkIfEmailAddressExist(editProfileRequest.getEmailAddress());
        user.setFirstName(editProfileRequest.getFirstName());
        user.setLastName(editProfileRequest.getLastName());
        user.setEmail(editProfileRequest.getEmailAddress());
        user.setPhoneNumber(editProfileRequest.getPhoneNumber());
        this.userRepository.save(user);
        return "Profile updated";
    }

    @Override
    public String updatePassword(String userName, UpdatePasswordRequest updatePasswordRequest) {
        if(!updatePasswordRequest.getNewPassword().equals(updatePasswordRequest.getConfirmation()))
            throw new BadRequestException("Confirm your password again");
        User user = this.userRepository.findByUsername(userName).get();
        user.setPassword(encoder.encode(updatePasswordRequest.getNewPassword()));
        this.userRepository.save(user);
        return "Password updated";
    }

    private void checkIfEmailAddressExist(String emailAddress) {
        if (this.userRepository.existsByEmail(emailAddress))
            throw new BadRequestException("Email address already in use");
    }

    @Override
    public AccountStatusStats getAccountStatusStats() {
        return new AccountStatusStats(this.userRepository.getStatusNumber(0),
                this.userRepository.getStatusNumber(1));
    }
}
