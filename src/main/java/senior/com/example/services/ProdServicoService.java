package senior.com.example.services;

import senior.com.example.models.ProdServico;
import senior.com.example.repositories.PedidoRepository;
import senior.com.example.repositories.ProdServicoRepository;

import java.util.List;

public class ProdServicoService {
    double sumPrice;

    public void setSumPedido(ProdServico prodServico, ProdServicoRepository prodServicoRepository, PedidoRepository pedidoRepository) {
        sumPrice = 0;
        if(prodServico.getPedido() != null) {
            List<ProdServico> prodServicoList = prodServicoRepository.findByPedidoId(prodServico.getPedido().getId());
            prodServicoList.forEach(prodServicoEach -> {
                sumPrice = sumPrice + prodServicoEach.getPreco();
            });
            prodServico.getPedido().setPrecoPedido(sumPrice);
            pedidoRepository.save(prodServico.getPedido());
        }
    }

    public boolean isProductDeactivated(ProdServico prodServico) {
        if (prodServico.getPedido() != null && prodServico.getIsProduto() && !prodServico.getIsAtivo()) {
            return true;
        } else {
            return false;
        }
    }
}
