package com.example.myjpa.api.model.temp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @OneToMany(
        mappedBy = "cliente", // Nome do campo na classe 'Pedido' que mapeia esta associação
        cascade = CascadeType.ALL, // Se o Cliente for deletado, seus Pedidos também serão
        orphanRemoval = true,      // Remove Pedidos que forem desvinculados do Cliente
        fetch = FetchType.LAZY     // Carrega os pedidos somente quando solicitado
    )
    private Set<Pedido> pedidos = new HashSet<>();

    public void adicionarPedido(Pedido pedido) {
        this.pedidos.add(pedido);
        pedido.setCliente(this); // Seta a referência de volta para o cliente
    }

    public void removerPedido(Pedido pedido) {
        this.pedidos.remove(pedido);
        pedido.setCliente(null); // Remove a referência
    }

    public static String concatenarValores(List<Pedido> listaDeObjetos) {

        // 1. Cria a Stream a partir da lista
        return listaDeObjetos.stream()

                // 2. Mapeia: Transforma cada objeto (Pedido) no valor de sua propriedade (String)
                .map(e->e.getValor().toString()) // Usa Method Reference, equivalente a .map(item -> item.getValor())

                // 3. Coleta: Junta todas as Strings em uma única String
                .collect(Collectors.joining());
    }

    public Cliente(String nome){
        this.nome=nome;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", pedidos=" + pedidos.size() +
                '}';
    }
}
