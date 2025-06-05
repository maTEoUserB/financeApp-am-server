package pl.finances.finances_app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.finances.finances_app.repositories.entities.AccountEntity;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    AccountEntity save(AccountEntity user);
    boolean existsById(Long id);
    Optional<AccountEntity> findById(Long id);
    Optional<AccountEntity> findByUsername(String username);
    void deleteById(Long id);
    boolean existsByUsername(String username);
}
