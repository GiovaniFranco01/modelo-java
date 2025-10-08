package com.example.myjpa.api.controller;


import com.example.myjpa.api.dtos.ClienteRequestDTO;
import com.example.myjpa.api.dtos.PedidoRequestDTO;
import com.example.myjpa.api.infra.ApiResponse;
import com.example.myjpa.api.model.temp.Cliente;
import com.example.myjpa.api.model.temp.Pedido;
import com.example.myjpa.api.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    // Injeção de dependência via construtor
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> cadastrarCliente(
        // @RequestBody: Pega o corpo JSON e o transforma em ClienteRequestDTO
        // @Valid: Aciona a validação das anotações no DTO (e nos Pedidos)
        @Valid @RequestBody ClienteRequestDTO dto)
    {
        // 1. Lógica de Negócio (Mapeamento e Salvamento)
        // O mapeamento DTO -> Entidade deve ocorrer no Service para manter o Controller limpo.
        Cliente cliente = new Cliente(dto.getNome());
        for (PedidoRequestDTO pedidoRequestDTO: dto.getPedidos()){
            Pedido pedido = new Pedido();
            BeanUtils.copyProperties(pedidoRequestDTO, pedido);
            pedido.setData_pedido(new Date());
            cliente.adicionarPedido(pedido);
        }

        System.out.println("=============");
        System.out.println(cliente);
        Cliente clienteSalvo = clienteService.salvar(cliente);

        // 2. Retorno Padronizado
        ApiResponse<?> responseBody = ApiResponse.success(
            clienteSalvo,
            "Cliente e seus pedidos cadastrados com sucesso!",
            HttpStatus.CREATED
        );

        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }
}
