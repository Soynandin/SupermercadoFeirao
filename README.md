# SupermercadoFeirao
 
*BR BR BR* Este projeto é um sistema de vendas e gerenciamento de estoque desenvolvido em Java, utilizando Hibernate com JPA para a persistência de dados e conexão com um banco de dados local. O sistema segue a estrutura de um projeto Maven, com a interface gráfica construída em Java Swing, e inclui alguns recursos sonoros para aprimorar a experiência do usuário.

O sistema opera em três modos distintos: Modo Aberto, Modo Fechado e Modo Administrador, cada um oferecendo funcionalidades específicas para diferentes contextos de uso.

Há também três tipos de usuários no sistema: Atendente, Gerente e Cliente, cada um com diferentes níveis de acesso e permissões.

As especificações detalhadas e a descrição completa do sistema estão disponíveis no arquivo "Descrição do Sistema" na pasta "Descrição".

*Observações* - Para que o sistema funcione corretamente, certifique-se préviamente:
- Verifique se sua máquina possui JRE - versão 17.
- Execute a DDL do Banco de dados 'SupermercadoFeirao' disponível no arquivo "Banco Supermercado Feirao", na pasta "Descrição".
(Este Sistema possui o drive do MYSQL, caso você não utilize é preciso instalar o drive do seu Banco em 'Pom.xml')
- Altere os valores de Usuário e Senha do seu Banco de Dados local no arquivo "Persistence.xml" localizado em: src\main\resources\META-INF\persistence.xml.

------------------------------------------------------------------------------

*US US US* This project is a sales and inventory management system developed in Java, using Hibernate with JPA for data persistence and connecting to a local database. The system follows a Maven project structure, with the graphical interface built using Java Swing, and includes some sound features to enhance the user experience.

The system operates in three distinct modes: Open Mode, Closed Mode, and Administrator Mode, each offering specific functionalities for different usage contexts.

There are also three types of users in the system: Attendant, Manager, and Customer, each with different levels of access and permissions.

The detailed specifications and complete description of the system are available in the 'Descrição do Sistema' file in the 'Descrição' folder.

*Notes* - To ensure the system functions correctly, please make sure to:
- Verify that your machine has JRE - version 17.
- Execute the DDL for the 'SupermercadoFeirao' database available in the file "Banco Supermercado Feirao" located in the "Descrição" folder.
(This system includes the MYSQL driver; if you do not use it, you need to install the driver for your database in 'Pom.xml')
- Update the User and Password values for your local database in the "Persistence.xml" file located at: src\main\resources\META-INF\persistence.xml.