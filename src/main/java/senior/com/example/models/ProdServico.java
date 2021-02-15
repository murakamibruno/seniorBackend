package senior.com.example.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.querydsl.core.annotations.Config;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Currency;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.UUID;

@Entity
@Table(name="prodservico")
public class ProdServico {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Type(type="pg-uuid")
    private UUID id;

    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 a 100 caracteres")
    @NotNull(message = "Nome não pode ser nulo")
    @Column(name = "nome", nullable = false)
    private String nome;

    @Positive(message = "Valor não pode ser 0 ou negativo")
    @Column(name = "preco", nullable = false)
    private double preco;

    @NotNull
    @Column(name = "isProduto", nullable = false)
    private boolean isProduto;

    @NotNull
    @Column(name= "isAtivo", nullable = false, columnDefinition = "boolean default true")
    private boolean isAtivo;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="pedido_id")
    private Pedido pedido;

    @JsonBackReference
    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public ProdServico() {
        super();
    }

    public ProdServico(String nome, float preco, boolean isProduto, boolean isAtivo, Pedido pedido) {
        this.nome = nome;
        this.preco = preco;
        this.isProduto = isProduto;
        this.isAtivo = isAtivo;
        this.pedido = pedido;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public boolean getIsProduto() {
        return isProduto;
    }

    public void setIsProduto(boolean isProduto) {
        this.isProduto = isProduto;
    }

    public boolean getIsAtivo() {
        return isAtivo;
    }

    public void setIsAtivo(boolean isAtivo) {
        this.isAtivo = isAtivo;
    }
}

