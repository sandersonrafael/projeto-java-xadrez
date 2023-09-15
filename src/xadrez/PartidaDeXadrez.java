package xadrez;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez {
  private Tabuleiro tabuleiro;

  public PartidaDeXadrez() {
    tabuleiro = new Tabuleiro(8, 8);
    configuracaoInicial();
  }

  public PecaDeXadrez[][] getPecas() {
    PecaDeXadrez[][] matriz = new PecaDeXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
    for (int i = 0; i < tabuleiro.getLinhas(); i++) {
      for (int j = 0; j < tabuleiro.getColunas(); j++) {
        matriz[i][j] = (PecaDeXadrez) tabuleiro.peca(i, j);
      }
    }
    return matriz;
  }

  private void configuracaoInicial() {
    tabuleiro.posicionarPeca(new Torre(tabuleiro, Cor.BRANCO), new Posicao(2, 1));
    tabuleiro.posicionarPeca(new Rei(tabuleiro, Cor.PRETO), new Posicao(0, 4));
    tabuleiro.posicionarPeca(new Rei(tabuleiro, Cor.BRANCO), new Posicao(7, 4));

  }
}
