package pl.finances.finances_app.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.finances.finances_app.repositories.AccountRepository;
import pl.finances.finances_app.repositories.entities.AccountEntity;
import java.util.Optional;
//import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Provides business logic for managing users in the system.
 */
@Service
@Transactional
public class UserService {
    private final AccountRepository userRepository;
    private BudgetService budgetService;

    /**
     * Constructs a new UserService with the required repository and service.
     *
     * @param userRepository the repository for user data access
     */
    @Autowired
    public UserService(AccountRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Setter to inject the budget service object when needed.
     *
     * @param budgetService the service for budget data managing
     */
    @Autowired
    public void setBudgetService(@Lazy BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    public AccountEntity getUserAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AccountEntity account = userRepository.findEntityByUsername(username);
        if (account == null) {
            throw new EntityNotFoundException("User not found");
        }
        return account;
    }

    public long getUserAccountId() {
        return getUserAccount().getId();
    }

    public Double getUserSaldo(long id){
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User entity not found")).getSaldo();
    }

    public Optional<AccountEntity> findUserById(long id) {
        return userRepository.findById(id);
    }
}
