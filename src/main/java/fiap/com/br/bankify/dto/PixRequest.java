package fiap.com.br.bankify.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class PixRequest {
    @NotNull(message = "{pix.contaOrigem.notblank}")
    private Long contaOrigemId;
    
    @NotNull(message = "{pix.contaDestino.notblank}")
    private Long contaDestinoId;
    
    @NotNull
    @Positive(message = "{pix.valor.positive}")
    private BigDecimal valor;
}
