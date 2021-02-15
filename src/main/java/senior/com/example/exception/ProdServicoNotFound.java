package senior.com.example.exception;

import java.util.UUID;

public class ProdServicoNotFound extends RuntimeException {
    public ProdServicoNotFound(UUID id) {
        super(String.format("Produto/Servico com id: " + id + " não encontrado"));
    }
}
