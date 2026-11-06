package br.com.joaocarloslima.robo.model;

public class ZigueZague implements Rota {
    private Direcao horizontal = Direcao.DIREITA;
    private Direcao vertical = Direcao.BAIXO;
    private boolean mudandoDeLinha = false;

    @Override
    public String getNome() {
        return "Zigue-zague";
    }

    @Override
    public Direcao proximaDirecao(Sala sala, Posicao atual) {
        if (mudandoDeLinha) {
            mudandoDeLinha = false;
            return horizontal;
        }

        if (sala.livre(atual.mover(horizontal))) {
            return horizontal;
        }

        horizontal = inverter(horizontal);
        if (!sala.livre(atual.mover(vertical))) {
            vertical = inverter(vertical);
        }
        mudandoDeLinha = true;
        return vertical;
    }

    private Direcao inverter(Direcao direcao) {
        return switch (direcao) {
            case DIREITA -> Direcao.ESQUERDA;
            case ESQUERDA -> Direcao.DIREITA;
            case BAIXO -> Direcao.CIMA;
            case CIMA -> Direcao.BAIXO;
        };
    }
}
