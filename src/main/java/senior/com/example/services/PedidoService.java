package senior.com.example.services;

import senior.com.example.models.Pedido;
import senior.com.example.models.ProdServico;
import senior.com.example.repositories.ProdServicoRepository;

import java.util.List;
import java.util.UUID;

public class PedidoService {
    private double discountSumValue;
    private double prodServicosSum;

    public double getSumDiscountValue(Pedido pedido, int desconto, ProdServicoRepository prodServicoRepository) {
        discountSumValue = 0;
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

    public double getProdServicosSum(Pedido pedido) {
        prodServicosSum = 0;
        pedido.getProdServicos().forEach(prodServico -> {
            prodServicosSum += prodServico.getPreco();
        });
        return prodServicosSum;
    }


}
