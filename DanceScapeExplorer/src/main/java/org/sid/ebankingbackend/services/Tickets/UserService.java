package org.sid.ebankingbackend.services.Tickets;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.sid.ebankingbackend.models.User;
import org.sid.ebankingbackend.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class UserService {

 UserRepository userRepository;

    @Transactional
    public void updateDiscountCodeForUser(Long userId, String discountCode) {
        User user=userRepository.findById(userId).get();
        user.setDiscount(discountCode);
        userRepository.save(user);
    }
    @Transactional
    public void resetDiscountCodes() {
        // Récupérer tous les utilisateurs
        List<User> users = userRepository.findAll();

        // Réinitialiser le code de réduction pour chaque utilisateur
        for (User user : users) {
            user.setDiscount(null);
        }

        // Sauvegarder les utilisateurs mis à jour
        userRepository.saveAll(users);
    }
    public boolean validateDiscountCode(String discountCode) {
        Optional<User> userWithDiscount = userRepository.findByDiscount(discountCode);
        boolean isValid = userWithDiscount.isPresent();
        log.info("Validation du code de réduction '{}' : {}", discountCode, isValid);
        return isValid;
    }
    public String getUserName(Long userId) {
        return userRepository.findById(userId)
                .map(User::getUsername)  // Transformation de User en String (nom de l'utilisateur)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé avec l'ID : " + userId));
    }

    public User getUser(Long id){
        return userRepository.findById(id).get();
    }

}


