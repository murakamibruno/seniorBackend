package senior.com.example.exception;

import java.util.UUID;

public class PedidoNotFound extends RuntimeException{
    public PedidoNotFound(UUID id) {
        super(String.format("Pedido com id: " + id + " não encontrada"));
    }
}
