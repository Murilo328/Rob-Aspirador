package br.com.joaocarloslima.robo.model;

public record Posicao(int x, int y) {

    public Posicao mover(Direcao direcao) {
        return new Posicao(x + direcao.getDx(), y + direcao.getDy());
    }
}
