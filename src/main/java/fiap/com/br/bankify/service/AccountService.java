package fiap.com.br.bankify.service;

import fiap.com.br.bankify.dto.DepositoRequest;
import fiap.com.br.bankify.dto.PixRequest;
import fiap.com.br.bankify.dto.SaqueRequest;
import fiap.com.br.bankify.model.Account;
import fiap.com.br.bankify.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {
    
    private final AccountRepository accountRepository;
    
    public Account criarConta(Account conta) {
        if (accountRepository.existsByNumero(conta.getNumero())) {
            throw new RuntimeException("Número da conta já existe");
        }
        
        if (accountRepository.existsByCpfTitular(conta.getCpfTitular())) {
            throw new RuntimeException("CPF já cadastrado");
        }
        
        return accountRepository.save(conta);
    }
    
    @Transactional(readOnly = true)
    public List<Account> listarTodasContas() {
        return accountRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Optional<Account> buscarContaPorId(Long id) {
        return accountRepository.findById(id);
    }
    
    public void encerrarConta(Long id) {
        Account conta = accountRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        
        conta.setAtiva(false);
        accountRepository.save(conta);
    }
    
    public Account depositar(DepositoRequest request) {
        Account conta = accountRepository.findById(request.getContaId())
            .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        
        if (!conta.getAtiva()) {
            throw new RuntimeException("Conta inativa");
        }
        
        BigDecimal novoSaldo = conta.getSaldo().add(request.getValor());
        conta.setSaldo(novoSaldo);
        
        return accountRepository.save(conta);
    }
    
    public Account sacar(SaqueRequest request) {
        Account conta = accountRepository.findById(request.getContaId())
            .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        
        if (!conta.getAtiva()) {
            throw new RuntimeException("Conta inativa");
        }
        
        if (conta.getSaldo().compareTo(request.getValor()) < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }
        
        BigDecimal novoSaldo = conta.getSaldo().subtract(request.getValor());
        conta.setSaldo(novoSaldo);
        
        return accountRepository.save(conta);
    }
    
    public Account pix(PixRequest request) {
        Account contaOrigem = accountRepository.findById(request.getContaOrigemId())
            .orElseThrow(() -> new RuntimeException("Conta de origem não encontrada"));
        
        Account contaDestino = accountRepository.findById(request.getContaDestinoId())
            .orElseThrow(() -> new RuntimeException("Conta de destino não encontrada"));
        
        if (!contaOrigem.getAtiva()) {
            throw new RuntimeException("Conta de origem inativa");
        }
        
        if (!contaDestino.getAtiva()) {
            throw new RuntimeException("Conta de destino inativa");
        }
        
        if (contaOrigem.getSaldo().compareTo(request.getValor()) < 0) {
            throw new RuntimeException("Saldo insuficiente na conta de origem");
        }
        
        BigDecimal novoSaldoOrigem = contaOrigem.getSaldo().subtract(request.getValor());
        BigDecimal novoSaldoDestino = contaDestino.getSaldo().add(request.getValor());
        
        contaOrigem.setSaldo(novoSaldoOrigem);
        contaDestino.setSaldo(novoSaldoDestino);
        
        accountRepository.save(contaOrigem);
        accountRepository.save(contaDestino);
        
        return contaOrigem; 
    }
}