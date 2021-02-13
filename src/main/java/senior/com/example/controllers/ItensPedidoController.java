package senior.com.example.controllers;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import senior.com.example.criteria.predicates.ItensPedidoPredicateBuilder;
import senior.com.example.criteria.predicates.ProdServicoPredicateBuilder;
import senior.com.example.models.ItensPedido;
import senior.com.example.models.ProdServico;
import senior.com.example.repositories.ItensPedidoRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/itens_pedidos")
public class ItensPedidoController {
    private float sumPreco = 0.0F;
    private Pageable pageSize = PageRequest.ofSize(10);
    private ItensPedidoRepository itensPedidoRepository;

    @Autowired
    public ItensPedidoController(ItensPedidoRepository itensPedidoRepository) {
        this.itensPedidoRepository = itensPedidoRepository;
    }

    @GetMapping
    public Iterable<ItensPedido> getAllPedidos() {
        return itensPedidoRepository.findAll(pageSize);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItensPedido> getPedidoById(@PathVariable(value = "id") UUID id) {
        Optional<ItensPedido> pedido = itensPedidoRepository.findById(id);
        if(pedido.isPresent()) {
            return new ResponseEntity<ItensPedido>(pedido.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/filter")
    @ResponseBody
    public Iterable<ItensPedido> search(@RequestParam(value = "search") String search) {
        ItensPedidoPredicateBuilder builder = new ItensPedidoPredicateBuilder();

        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w.+?)(:|<|>)((\\w+?\\-?)*),");
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }
        }
        BooleanExpression exp = builder.build();
        return itensPedidoRepository.findAll(exp);
    }

    @PostMapping
    public ResponseEntity<ItensPedido> savePedido(@RequestBody ItensPedido pedido) {
        ItensPedido pedidoCreated = itensPedidoRepository.save(pedido);
        return new ResponseEntity<ItensPedido>(pedidoCreated, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItensPedido> updatePedido(@RequestBody ItensPedido pedido, @PathVariable("id") UUID id) {
        if (itensPedidoRepository.existsById(id)) {
            pedido.setId(id);
            ItensPedido pedidoUpdated = itensPedidoRepository.save(pedido);
            return new ResponseEntity<ItensPedido>(pedidoUpdated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ItensPedido> deletePedido (@PathVariable("id") UUID id) {
        Optional<ItensPedido> pedido = itensPedidoRepository.findById(id);

        if (pedido.isPresent()) {
            ItensPedido pedidoDeleted = pedido.get();
            itensPedidoRepository.delete(pedidoDeleted);
            return new ResponseEntity<ItensPedido>(pedidoDeleted, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
