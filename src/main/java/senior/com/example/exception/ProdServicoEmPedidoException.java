package senior.com.example.exception;

import java.util.UUID;

public class ProdServicoEmPedidoException extends RuntimeException {
    public ProdServicoEmPedidoException(UUID id) {
        super(String.format("Produto/Serviço de id: " + id + " está atrelado a um pedido"));
    }
}
