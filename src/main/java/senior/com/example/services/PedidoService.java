package senior.com.example.services;

import senior.com.example.models.Pedido;
import senior.com.example.models.ProdServico;
import senior.com.example.repositories.ProdServicoRepository;

import java.util.List;
import java.util.UUID;

public class PedidoService {
    private float sumPrice = 0.0F;
    private float discountSumValue = 0.0F;

    public float getSumPrice(Pedido pedido, ProdServicoRepository prodServicoRepository) {
        UUID uuid = pedido.getId();
        List<ProdServico> prodServicoList = prodServicoRepository.findByPedidoId(uuid);
        prodServicoList.forEach(prodServico -> {
            sumPrice += prodServico.getPreco();
        });
        return sumPrice;
    }

    public float getSumDiscountValue(Pedido pedido, int desconto, ProdServicoRepository prodServicoRepository) {
        UUID uuid = pedido.getId();
        double discount = Double.valueOf(desconto)/100;
        List<ProdServico> prodServicoList = prodServicoRepository.findByPedidoId(uuid);
        prodServicoList.forEach(prodServico -> {
            if(prodServico.getIsProduto()) {
                discountSumValue += prodServico.getPreco() * discount;
            }
        });
        return discountSumValue;
    }
}
