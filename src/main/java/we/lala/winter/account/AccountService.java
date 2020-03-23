package we.lala.winter.account;

import org.springframework.stereotype.Service;
import we.lala.winter.domain.Account;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createNew(Account account) {
        account.encodePassword();
        return this.accountRepository.save(account);
    }
}
