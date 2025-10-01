# Desenvolvimento Mobile – App02 - WalletApp - Requisitos da aplicação

Prompt: Siga os requisitos abaixo para desenvolver o aplicativo solicitado.

Uma FinTech, startup da área de finanças, solicitou o desenvolvimento de um aplicativo simples para Android, que auxilie o usuário em sua vida financeira. O objetivo do aplicativo é permitir o cadastro de todos os gastos (débitos) e ganhos (créditos) financeiros do usuário, auxiliando na classificação dos gastos e na organização da vida financeira.

## Requisitos do Aplicativo

### Activity Principal - Dashboard

A activity principal deve apresentar um painel de botões distribuídos de maneira equilibrada na interface, permitindo ao usuário executar as seguintes ações:

- **Cadastro**: Cadastrar novas transações financeiras.
- **Extrato**: Visualizar todas as operações cadastradas.
- **Sair**: Fechar o aplicativo.

### Activity Cadastro de Operações

Permite ao usuário cadastrar novas transações financeiras:

- Marcar a classificação da operação (**débito** ou **crédito**)
- Informar uma descrição curta
- Informar o valor

Os registros devem ser gravados de maneira permanente em um banco de dados, garantindo que as transações não sejam perdidas ao fechar o app.

### Activity Extrato

Exibe uma lista com todas as operações financeiras cadastradas. Cada item da lista deve conter:

- Uma imagem representando o tipo da transação (débito ou crédito)
- Descrição da transação
- Valor da transação

Não é necessário capturar o clique na célula.

A view deve permitir ao usuário filtrar as transações:

- Ver **todas** as transações
- Ver **apenas débitos**
- Ver **apenas créditos**

Além disso, deve mostrar o **saldo da carteira**, independente do filtro aplicado.

### Sair

Fecha o aplicativo.