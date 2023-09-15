package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public abstract class PecaDeXadrez extends Peca {
  private Cor cor;

  public PecaDeXadrez(Tabuleiro tabuleiro, Cor cor) {
    super(tabuleiro);
    this.cor = cor;
  }

  public Cor getCor() {
    return cor;
  }

  protected boolean eUmaPecaDoOponente(Posicao posicao) {
    Tabuleiro tab = getTabuleiro();
    PecaDeXadrez peca = (PecaDeXadrez) tab.peca(posicao);
    return peca != null && peca.getCor() != cor;
  }
}
