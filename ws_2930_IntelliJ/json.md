#### Corpo da requisição para inserir registro

```json
{
    "valor": 150.32,
    "nome": "Mozart",
    "numeroDoCartao": "35214578",
    "codigo": "654",
    "validade": "12/29",
    "pedidoId": 2,  
    "formaDePagamentoId": 2
}
```

##### Somente os campos obrigatórios
```json

{
    "valor": 250.38,
    "pedidoId": 2,  
    "formaDePagamentoId": 2
}

```

#### Corpo da requisição para alterar/update um registro
```json

  {
    "valor": 1250.00,
    "nome": "Nicodemus",
    "numeroDoCartao": "123456987",
    "validade": "12/30",
    "codigo": "123",
    "status": "CRIADO",
    "pedidoId": 1,
    "formaDePagamentoId": 2
    }
```