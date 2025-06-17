# 📱 sample-grpc-compose

Aplicativo Android desenvolvido com **Jetpack Compose** que consome serviços gRPC fornecidos por um servidor Go. 
A comunicação é feita via streaming, utilizando o protocolo gRPC com Protobuf.

Este projeto faz parte de um ecossistema que inclui o backend
[sample-grpc](https://github.com/mariofelesdossantosjunior/sample-grpc).

---

## 📚 Diagrama

![image](https://github.com/user-attachments/assets/c09b9e66-332d-4570-8ec6-9e02386314b7)

---

## 📌 Funcionalidades

- UI moderna usando Jetpack Compose
- Comunicação com backend gRPC (Go)
- Suporte a chamadas **streaming gRPC**
- Geração automática de stubs a partir de arquivos `.proto`
- Arquitetura limpa e modularizada

---

## 📁 Estrutura do Projeto

```

sample-grpc-compose/
├── app/                     # Código principal do app Android
│   ├── proto/               # Arquivos .proto e stubs gerados
│   ├── ui/                  # Telas Jetpack Compose
│   └── MainActivity.kt      # Tela principal / Lógica de consumo gRPC
├── build.gradle             # Configuração global
└── settings.gradle

````

---

## 🚀 Como Executar o Projeto

### 1. Requisitos

- Android Studio Giraffe ou superior
- JDK 17+
- Emulador Android ou dispositivo real
- Servidor gRPC rodando (ex: [sample-grpc](https://github.com/mariofelesdossantosjunior/sample-grpc))

### 2. Clonar o repositório

```bash
git clone https://github.com/mariofelesdossantosjunior/sample-grpc-compose.git
cd sample-grpc-compose
````

### 3. Rodar o servidor backend

Certifique-se de que o servidor gRPC esteja rodando em `localhost:50051`.
Caso use emulador Android, substitua por `10.0.2.2` na configuração do canal gRPC.

### 4. Abrir e executar no Android Studio

1. Abra a pasta no Android Studio.
2. Aguarde a sincronização Gradle.
3. Execute o app no emulador ou dispositivo.

---

## 🔧 Tecnologias Utilizadas

| Camada      | Tecnologia              |
| ----------- | ----------------------- |
| UI          | Jetpack Compose         |
| Backend     | gRPC com Protobuf       |
| Comunicação | gRPC Kotlin             |
| Build       | Gradle + Kotlin DSL     |

---

## 📡 Comunicação gRPC

A comunicação entre app e servidor segue o contrato definido em `.proto`.
Exemplo de definição:

```proto
service CategoryService {
  rpc ListCategoriesStream (Blank) returns (stream Category);
}
```

Na camada de dados (`data/`), o cliente se conecta usando `ManagedChannel` e consome dados em tempo real via `Flow`.

---

## 🧪 Testes

* Rode o app e veja a UI listar categorias enviadas via gRPC streaming.
* Use logs para inspecionar comunicação com o servidor.

---

## ▶️ Aplicação em execução


https://github.com/user-attachments/assets/2fe7bd1f-13d2-4d5a-830f-fc63adccd2bb

---

## 🔄 Integração com Backend

> O backend utilizado é [sample-grpc](https://github.com/mariofelesdossantosjunior/sample-grpc)

* Certifique-se de que as versões do `.proto` estão sincronizadas.
* Gere novamente os stubs caso haja alterações no contrato.

---

## 📚 Recursos úteis

* [Guia oficial gRPC Kotlin](https://github.com/grpc/grpc-kotlin)
* [Jetpack Compose documentation](https://developer.android.com/jetpack/compose)
* [gRPC + Android Tutorial](https://developer.android.com/topic/libraries/architecture/grpc)

---

## 📬 Contribuições

Contribuições são bem-vindas!
Abra uma issue ou PR com sugestões de melhorias ou correções.

---

## 📝 Licença

Este projeto é distribuído sob a licença MIT.
Consulte o arquivo `LICENSE` para mais detalhes.

```
