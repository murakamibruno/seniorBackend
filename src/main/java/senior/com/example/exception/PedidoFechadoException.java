package senior.com.example.exception;

import java.util.UUID;

public class PedidoFechadoException extends RuntimeException {
    public PedidoFechadoException(UUID id) {
        super(String.format("Não é possível aplicar desconto no pedido:  " + id + " pois o mesmo se encontra finalizado"));
    }
}
