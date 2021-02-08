package senior.com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import senior.com.example.models.ItensPedido;
import senior.com.example.repositories.ItensPedidoRepository;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/itens_pedidos")
public class ItensPedidoController {
    private float sumPreco = 0.0F;
    private Pageable pageSize = PageRequest.ofSize(10);
    private ItensPedidoRepository pedidoRepository;

    @Autowired
    public ItensPedidoController(ItensPedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @GetMapping
    public Iterable<ItensPedido> getAllPedidos() {
        return pedidoRepository.findAll(pageSize);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItensPedido> getPedidoById(@PathVariable(value = "id") UUID id) {
        Optional<ItensPedido> pedido = pedidoRepository.findById(id);
        if(pedido.isPresent()) {
            return new ResponseEntity<ItensPedido>(pedido.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ItensPedido> savePedido(@RequestBody ItensPedido pedido) {
        ItensPedido pedidoCreated = pedidoRepository.save(pedido);
        return new ResponseEntity<ItensPedido>(pedidoCreated, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItensPedido> updatePedido(@RequestBody ItensPedido pedido, @PathVariable("id") UUID id) {
        if (pedidoRepository.existsById(id)) {
            pedido.setId(id);
            ItensPedido pedidoUpdated = pedidoRepository.save(pedido);
            return new ResponseEntity<ItensPedido>(pedidoUpdated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ItensPedido> deletePedido (@PathVariable("id") UUID id) {
        Optional<ItensPedido> pedido = pedidoRepository.findById(id);

        if (pedido.isPresent()) {
            ItensPedido pedidoDeleted = pedido.get();
            pedidoRepository.delete(pedidoDeleted);
            return new ResponseEntity<ItensPedido>(pedidoDeleted, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
