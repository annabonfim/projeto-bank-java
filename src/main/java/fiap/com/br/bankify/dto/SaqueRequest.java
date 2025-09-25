package fiap.com.br.bankify.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class SaqueRequest {
    @NotNull(message = "{transacao.contaId.notblank}")
    private Long contaId;
    
    @NotNull
    @Positive(message = "{transacao.valor.positive}")
    private BigDecimal valor;
}