package fiap.com.br.bankify.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    
    @GetMapping("/")
    public String home() {
        return "Bankify - Sistema de Gestão Bancária Digital\n" +
               "Desenvolvido por: Anna Beatriz de Araujo Bonfim\n" +
               "API REST para operações bancárias";
    }
}
