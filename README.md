O projeto tem como objetivo demonstar como caso de uso uma aplicação que cataloga maquinas de adquirentes de pagamentos. Os adquirentes de pagamento são responsáveis por processar as transações financeiras entre o pagador e a instituições financeiras e bandeiras de cartão. 

A aruitetura base do projeto segue o desenho de solução abaixo, a ideia central além de prover uma api para gerenciar o catalogo de máquinas é realizar a comunicação assíncrona com um consumer através de uma fila em broker de mensagens RabbitMQ, quando eventos de cadastro de uma nova máquina da adquirente é cadastrada no catálogo.


![Projeto Machines Catalog](https://github.com/user-attachments/assets/0c229aac-fbca-4036-8645-9cb460dd4a96)


Para executar o projeto é necessário realizar o clone do repositório no github através do comando **git clone https://github.com/SamuelOFernandes/spring-machines-catalog.git**, acessar a raiz do projeto, abrir o terminal e executar o comando **docker compose up --build**.

Esse comando inicializará os container responsáveis pela criação do banco de dados utilizando MySQL e broker de mensagens RabbitMQ. 

Assim que os containers estiverem em execução a aplicação pode ser inicializada via terminal utilizando o comando **mvn spring-boot:run** ou IDE da sua preferência que tenha suporte a aplicações java.

 É recomendado que antes da iniciar a aplicação seja executado o comando **mvn clean install** para realizar o build do projeto. 

Ao iniciar a aplicação é possível explorar e realizar testes nos endpoints registrados através do Swagger acesando o endereço local : **http://localhost:8080/swagger-ui/index.html.** 

Para explorar a integração com o RabbitMQ e validar a chegada das mensagens no broker e queue configurada acesse o endereço local : **http://localhost:15672** e utilize o usuário **admin** e senha **12345**, para conferir as credenciais basta acessar o properties da aplicação. 

Para realizar o teste end-to-end com o consumer é necessário acessar o projeto : https://github.com/SamuelOFernandes/mq-consumer-machines-registration e executá-lo localmente através dos comandos **mvn clean install** e na sequência **mvn spring-boot:run**.

