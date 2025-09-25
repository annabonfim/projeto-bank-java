package fiap.com.br.bankify.controller;

import fiap.com.br.bankify.dto.DepositoRequest;
import fiap.com.br.bankify.dto.PixRequest;
import fiap.com.br.bankify.dto.SaqueRequest;
import fiap.com.br.bankify.model.Account;
import fiap.com.br.bankify.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contas")
@RequiredArgsConstructor
@Slf4j
public class AccountController {
    
    private final AccountService accountService;
    
    @PostMapping
    public ResponseEntity<Account> criarConta(@Valid @RequestBody Account conta) {
        log.info("POST /api/contas - Criando nova conta para titular: {}", conta.getNomeTitular());
        try {
            Account novaConta = accountService.criarConta(conta);
            log.info("Conta criada com sucesso - ID: {}, Número: {}", novaConta.getId(), novaConta.getNumero());
            return ResponseEntity.status(HttpStatus.CREATED).body(novaConta);
        } catch (RuntimeException e) {
            log.error("Erro ao criar conta: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Account>> listarContas() {
        log.info("GET /api/contas - Listando todas as contas");
        List<Account> contas = accountService.listarTodasContas();
        log.info("Encontradas {} contas", contas.size());
        return ResponseEntity.ok(contas);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Account> buscarConta(@PathVariable Long id) {
        log.info("GET /api/contas/{} - Buscando conta por ID", id);
        Optional<Account> conta = accountService.buscarContaPorId(id);
        if (conta.isPresent()) {
            log.info("Conta encontrada - ID: {}, Titular: {}", conta.get().getId(), conta.get().getNomeTitular());
            return ResponseEntity.ok(conta.get());
        } else {
            log.warn("Conta não encontrada - ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> encerrarConta(@PathVariable Long id) {
        log.info("DELETE /api/contas/{} - Encerrando conta", id);
        try {
            accountService.encerrarConta(id);
            log.info("Conta encerrada com sucesso - ID: {}", id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            log.error("Erro ao encerrar conta - ID: {}, Erro: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/deposito")
    public ResponseEntity<Account> depositar(@Valid @RequestBody DepositoRequest request) {
        log.info("POST /api/contas/deposito - Depósito de {} na conta ID: {}", request.getValor(), request.getContaId());
        try {
            Account contaAtualizada = accountService.depositar(request);
            log.info("Depósito realizado com sucesso - Conta ID: {}, Novo saldo: {}", contaAtualizada.getId(), contaAtualizada.getSaldo());
            return ResponseEntity.ok(contaAtualizada);
        } catch (RuntimeException e) {
            log.error("Erro no depósito - Conta ID: {}, Erro: {}", request.getContaId(), e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/saque")
    public ResponseEntity<Account> sacar(@Valid @RequestBody SaqueRequest request) {
        log.info("POST /api/contas/saque - Saque de {} da conta ID: {}", request.getValor(), request.getContaId());
        try {
            Account contaAtualizada = accountService.sacar(request);
            log.info("Saque realizado com sucesso - Conta ID: {}, Novo saldo: {}", contaAtualizada.getId(), contaAtualizada.getSaldo());
            return ResponseEntity.ok(contaAtualizada);
        } catch (RuntimeException e) {
            log.error("Erro no saque - Conta ID: {}, Erro: {}", request.getContaId(), e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/pix")
    public ResponseEntity<Account> pix(@Valid @RequestBody PixRequest request) {
        log.info("POST /api/contas/pix - PIX de {} da conta {} para conta {}", request.getValor(), request.getContaOrigemId(), request.getContaDestinoId());
        try {
            Account contaOrigemAtualizada = accountService.pix(request);
            log.info("PIX realizado com sucesso - Conta origem ID: {}, Novo saldo: {}", contaOrigemAtualizada.getId(), contaOrigemAtualizada.getSaldo());
            return ResponseEntity.ok(contaOrigemAtualizada);
        } catch (RuntimeException e) {
            log.error("Erro no PIX - Conta origem: {}, Conta destino: {}, Erro: {}", request.getContaOrigemId(), request.getContaDestinoId(), e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
    

