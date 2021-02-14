package senior.com.example.exception;

import java.util.UUID;

public class ProdServicoNotFound extends RuntimeException {
    public ProdServicoNotFound(UUID id) {
        super(String.format("Cidade com id: " + id + " não encontrada"));
    }
}
