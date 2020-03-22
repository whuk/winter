package we.lala.winter.account;

import org.springframework.data.jpa.repository.JpaRepository;
import we.lala.winter.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);
}
