# 🏦 Bankify - Sistema de Gestão Bancária Digital

## 📋 Sobre o Projeto

O **Bankify** é uma API REST desenvolvida em Spring Boot para gestão de um banco digital, permitindo operações bancárias básicas como criação de contas, depósitos, saques e transferências PIX.

## 🎯 Objetivo

Criar uma API REST completa para gestão de um banco digital usando Spring Boot, implementando todas as funcionalidades bancárias essenciais com validação de dados e tratamento de erros.

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.5.5**
- **Spring Web**
- **Spring Data JPA**
- **H2 Database** (banco em memória)
- **Bean Validation (Hibernate Validator)**
- **Lombok**
- **Maven**

## 📁 Estrutura do Projeto

```
src/
├── main/
│   ├── java/fiap/com/br/bankify/
│   │   ├── controller/
│   │   │   ├── AccountController.java      # Endpoints da API
│   │   │   ├── HomeController.java         # Página inicial
│   │   │   └── GlobalExceptionHandler.java # Tratamento de erros
│   │   ├── dto/
│   │   │   ├── DepositoRequest.java        # DTO para depósitos
│   │   │   ├── SaqueRequest.java           # DTO para saques
│   │   │   └── PixRequest.java             # DTO para PIX
│   │   ├── model/
│   │   │   └── Account.java                # Entidade Conta
│   │   ├── repository/
│   │   │   └── AccountRepository.java      # Repositório JPA
│   │   ├── service/
│   │   │   └── AccountService.java         # Lógica de negócio
│   │   └── BankifyApplication.java         # Classe principal
│   └── resources/
│       ├── application.properties          # Configurações
│       ├── ValidationMessages_pt_br.properties # Mensagens em português
│       └── ValidationMessages_en.properties    # Mensagens em inglês
└── test/
    └── java/fiap/com/br/bankify/
        └── BankifyApplicationTests.java    # Testes básicos
```

## 🛠️ Como Executar

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6 ou superior

### Passos para Execução

1. **Clone o repositório:**
```bash
git clone <url-do-repositorio>
cd bankify
```

2. **Execute o projeto:**
```bash
mvn spring-boot:run
```

3. **Acesse a aplicação:**
- API: `http://localhost:8080`
- H2 Console: `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:bankify`
  - Usuário: `sa`
  - Senha: (deixe em branco)

## 📚 Documentação da API

### Endpoints Disponíveis

#### 🏠 Página Inicial
- **GET** `/` - Informações do projeto e desenvolvedor

#### 👤 Gestão de Contas
- **POST** `/api/contas` - Criar nova conta
- **GET** `/api/contas` - Listar todas as contas
- **GET** `/api/contas/{id}` - Buscar conta por ID
- **DELETE** `/api/contas/{id}` - Encerrar conta

#### 💰 Operações Financeiras
- **POST** `/api/contas/deposito` - Realizar depósito
- **POST** `/api/contas/saque` - Realizar saque
- **POST** `/api/contas/pix` - Transferência PIX

### Exemplos de Uso

#### 1. Criar uma Conta
```bash
curl -X POST http://localhost:8080/api/contas \
  -H "Content-Type: application/json" \
  -d '{
    "numero": "12345",
    "agencia": "001",
    "nomeTitular": "João Silva",
    "cpfTitular": "12345678901",
    "dataAbertura": "2024-01-01",
    "saldo": 1000.00,
    "tipo": "CORRENTE"
  }'
```

#### 2. Realizar Depósito
```bash
curl -X POST http://localhost:8080/api/contas/deposito \
  -H "Content-Type: application/json" \
  -d '{
    "contaId": 1,
    "valor": 500.00
  }'
```

#### 3. Realizar Saque
```bash
curl -X POST http://localhost:8080/api/contas/saque \
  -H "Content-Type: application/json" \
  -d '{
    "contaId": 1,
    "valor": 200.00
  }'
```

#### 4. Transferência PIX
```bash
curl -X POST http://localhost:8080/api/contas/pix \
  -H "Content-Type: application/json" \
  -d '{
    "contaOrigemId": 1,
    "contaDestinoId": 2,
    "valor": 300.00
  }'
```

## 🔍 Validações Implementadas

### Campos Obrigatórios
- **Nome do titular**: Não pode ser vazio
- **CPF do titular**: Não pode ser vazio e deve ser único
- **Número da conta**: Não pode ser vazio e deve ser único
- **Agência**: Não pode ser vazia
- **Data de abertura**: Não pode ser no futuro
- **Saldo inicial**: Não pode ser negativo
- **Tipo da conta**: Deve ser CORRENTE, POUPANCA ou SALARIO

### Validações de Negócio
- **Depósitos**: Valor deve ser positivo
- **Saques**: Valor deve ser positivo e não pode exceder o saldo
- **PIX**: Contas devem existir e estar ativas, saldo suficiente na origem

## 📊 Modelo de Dados

### Entidade Account
```java
- id: Long (ID único)
- numero: String (Número da conta - único)
- agencia: String (Agência)
- nomeTitular: String (Nome do titular)
- cpfTitular: String (CPF do titular - único)
- dataAbertura: LocalDate (Data de abertura)
- saldo: BigDecimal (Saldo atual)
- ativa: Boolean (Status da conta)
- tipo: TipoConta (CORRENTE, POUPANCA, SALARIO)
```

## 🔧 Configurações

### application.properties
```properties
# Banco de dados H2
spring.datasource.url=jdbc:h2:mem:bankify
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Tratamento de erros
server.error.include-message=always
```

## 🎨 Funcionalidades Implementadas

### ✅ Requisitos Funcionais
- [x] Criação do projeto Spring Boot
- [x] Endpoint inicial com informações do projeto
- [x] Cadastro de contas com todos os campos obrigatórios
- [x] Validação completa de dados
- [x] Busca de contas (listar todas e por ID)
- [x] Encerramento de contas
- [x] Operações de depósito
- [x] Operações de saque
- [x] Transferências PIX

### ✅ Recursos Adicionais
- [x] Logging detalhado em todos os endpoints
- [x] Tratamento de erros com mensagens personalizadas
- [x] Validação com mensagens em português e inglês
- [x] Transações para garantir consistência dos dados
- [x] DTOs para operações financeiras
- [x] Arquitetura em camadas (Controller, Service, Repository)

## 🚨 Tratamento de Erros

A API retorna erros estruturados com:
- **Status HTTP apropriado** (400, 404, 500)
- **Mensagens de erro em português**
- **Detalhes específicos do problema**

### Exemplo de Erro de Validação
```json
{
  "nomeTitular": "nome do titular é obrigatório",
  "cpfTitular": "CPF do titular é obrigatório",
  "saldo": "saldo inicial não pode ser negativo"
}
```

## 👨‍💻 Desenvolvedor

**Anna Beatriz de Araujo Bonfim**

## 📝 Licença

Este projeto foi desenvolvido como parte do curso de Java Advanced da FIAP.

---

## 🎯 Pontuação do Projeto

Este projeto atende a **100% dos requisitos** especificados:

- ✅ **Criação do Projeto (20%)**
- ✅ **Cadastro de Conta (20%)**
- ✅ **Validação (20%)**
- ✅ **Buscas (10%)**
- ✅ **Encerrar Conta (10%)**
- ✅ **Depósito (10%)**
- ✅ **Saque (10%)**
- ✅ **PIX (20%)**

**Total: 100%** 🎉
