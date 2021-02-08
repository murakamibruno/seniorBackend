package senior.com.example.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="prodservico")
public class ProdServico {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Type(type="pg-uuid")
    private UUID id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "preco", nullable = false)
    private float preco;

    @Column(name = "isProduto", nullable = false)
    private boolean isProduto;

    @Column(name= "isAtivo", nullable = false, columnDefinition = "boolean default true")
    private boolean isAtivo;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="itens_pedido_id")
    private ItensPedido itensPedido;

    @JsonBackReference
    public ItensPedido getItensPedido() {
        return itensPedido;
    }

    public void setItensPedido(ItensPedido itensPedido) {
        this.itensPedido = itensPedido;
    }


    public ProdServico() {
        super();
    }

    public ProdServico(String nome, float preco, boolean isProduto, boolean isAtivo, ItensPedido itensPedido) {
        this.nome = nome;
        this.preco = preco;
        this.isProduto = isProduto;
        this.isAtivo = isAtivo;
        this.itensPedido = itensPedido;
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

    public float getPreco() {
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

