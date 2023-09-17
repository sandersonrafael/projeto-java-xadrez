package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public abstract class PecaDeXadrez extends Peca {
  private Cor cor;
  private int movimentos;

  public PecaDeXadrez(Tabuleiro tabuleiro, Cor cor) {
    super(tabuleiro);
    this.cor = cor;
  }

  public Cor getCor() {
    return cor;
  }

  public int getMovimentos() {
    return movimentos;
  }

  public void aumentarMovimentos() {
    movimentos++;
  }

  public void diminuirMovimentos() {
    movimentos--;
  }

  public PosicaoXadrez getPosicaoXadrez() {
    return PosicaoXadrez.posicaoTabuleiro(posicao);
  }

  protected boolean eUmaPecaDoOponente(Posicao posicao) {
    Tabuleiro tab = getTabuleiro();
    PecaDeXadrez peca = (PecaDeXadrez) tab.peca(posicao);
    return peca != null && peca.getCor() != cor;
  }
}
