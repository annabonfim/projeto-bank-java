package fiap.com.br.bankify.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "contas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    @NotBlank(message = "{conta.numero.notblank}")
    private String numero;
    
    @Column(nullable = false)
    @NotBlank(message = "{conta.agencia.notblank}")
    private String agencia;
    
    @Column(name = "nome_titular", nullable = false)
    @NotBlank(message = "{conta.nomeTitular.notblank}")
    private String nomeTitular;
    
    @Column(name = "cpf_titular", nullable = false, unique = true)
    @NotBlank(message = "{conta.cpfTitular.notblank}")
    private String cpfTitular;
    
    @Column(name = "data_abertura", nullable = false)
    @NotNull
    @PastOrPresent(message = "{conta.dataAbertura.pastorpresent}")
    private LocalDate dataAbertura;
    
    @Column(nullable = false, precision = 15, scale = 2)
    @NotNull
    @DecimalMin(value = "0.0", inclusive = true, message = "{conta.saldoInicial.positiveorzero}")
    private BigDecimal saldo;
    
    @Column(nullable = false)
    @Builder.Default
    private Boolean ativa = true;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "{conta.tipo.notblank}")
    private TipoConta tipo;
    
    public enum TipoConta {
        CORRENTE, POUPANCA, SALARIO
    }
}