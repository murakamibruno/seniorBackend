package senior.com.example.models;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="pedido")
public class Pedido {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Type(type="pg-uuid")
    private UUID id;

    @OneToOne(cascade = CascadeType.MERGE)
    private ItensPedido itensPedido;

    private float precoPedido;

    public Pedido() {

    }

    public Pedido(UUID id, ItensPedido itensPedido, float precoPedido) {
        this.id = id;
        this.itensPedido = itensPedido;
        this.precoPedido = precoPedido;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ItensPedido getItensPedido() {
        return itensPedido;
    }

    public void setItensPedido(ItensPedido itensPedido) {
        this.itensPedido = itensPedido;
    }

    public float getPrecoPedido() {
        return precoPedido;
    }

    public void setPrecoPedido(float precoPedido) {
        this.precoPedido = precoPedido;
    }
}
