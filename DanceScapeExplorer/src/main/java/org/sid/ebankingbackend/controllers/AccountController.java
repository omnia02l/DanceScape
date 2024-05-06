package org.sid.ebankingbackend.controllers;

import org.sid.ebankingbackend.models.AccountStatusStats;
import org.sid.ebankingbackend.payload.request.EditProfileRequest;
import org.sid.ebankingbackend.payload.request.UpdatePasswordRequest;
import org.sid.ebankingbackend.payload.response.UserDTO;
import org.sid.ebankingbackend.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountService accountService;

    @GetMapping("/list-accounts")
    private List<UserDTO> listAccounts() {
        return this.accountService.listAccounts().stream().map(UserDTO::new).toList();
    }

    @PostMapping("/ban-account")
    private void banAccounts(@RequestParam String userName) {
        this.accountService.banAccount(userName);
    }

    @PostMapping("/enable-account")
    private void enableAccounts(@RequestParam String userName) {
        this.accountService.enableAccount(userName);
    }

    @GetMapping("/profile")
    private UserDTO getPrincipal(Principal principal){
        return new UserDTO(this.accountService.getPrincipal(principal.getName()));
    }

    @PostMapping("/edit-profile")
    private String editProfile(Principal principal, @RequestBody EditProfileRequest editProfileRequest) {
        return this.accountService.editProfile(principal.getName(), editProfileRequest);
    }

    @PostMapping("/update-password")
    private String updatePassword(Principal principal, @RequestBody UpdatePasswordRequest updatePasswordRequest) {
        return this.accountService.updatePassword(principal.getName(), updatePasswordRequest);
    }

    @GetMapping("/get-status-stats")
    private AccountStatusStats getStatusStats(){
        return this.accountService.getAccountStatusStats();
    }
}
