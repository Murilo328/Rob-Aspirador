package br.com.joaocarloslima.robo.controller;

import br.com.joaocarloslima.robo.model.Simulador;

public record Estado(int x, int y, String direcao, int passos, int batidas,
        int percentualLimpo, String rota, int limite, boolean bateu) {

    public static Estado de(Simulador simulador, boolean bateu) {
        var robo = simulador.getRobo();
        return new Estado(
                robo.getPosicao().x(),
                robo.getPosicao().y(),
                robo.getDirecao().name(),
                robo.getPassos(),
                robo.getBatidas(),
                simulador.getSala().getPercentualLimpo(),
                robo.getNomeDaRota(),
                Simulador.LIMITE_PASSOS,
                bateu);
    }
}
