package fiap.com.br.bankify.repository;

import fiap.com.br.bankify.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    
    Optional<Account> findByNumero(String numero);
    Optional<Account> findByCpfTitular(String cpfTitular);
    boolean existsByNumero(String numero);
    boolean existsByCpfTitular(String cpfTitular);
}
