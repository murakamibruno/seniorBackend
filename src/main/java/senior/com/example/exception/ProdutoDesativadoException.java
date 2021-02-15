package senior.com.example.exception;

import java.util.UUID;

public class ProdutoDesativadoException extends RuntimeException{
    public ProdutoDesativadoException(String nome) {
        super(String.format("Produto de nome " + nome + " não pode ser inserido em um pedido pois se encontra desativado"));
    }
}
