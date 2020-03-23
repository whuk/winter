package we.lala.winter.account;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import we.lala.winter.domain.Account;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Account createNew(Account account) {
        account.encodePassword(passwordEncoder);
        return this.accountRepository.save(account);
    }
}
