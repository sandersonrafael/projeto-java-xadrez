package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Cavalo extends PecaDeXadrez {
  public Cavalo(Tabuleiro tabuleiro, Cor cor) {
    super(tabuleiro, cor);
  }

  private boolean podeMover(Posicao posicao) {
    Tabuleiro tab = getTabuleiro();
    PecaDeXadrez p = (PecaDeXadrez) tab.peca(posicao);
    return p == null || p.getCor() != getCor();
  }

  @Override
  public boolean[][] movimentosPossiveis() {
    Tabuleiro tab = getTabuleiro();
    boolean[][] mat = new boolean[tab.getLinhas()][tab.getColunas()];

    Posicao p = new Posicao(0, 0);

    p.setLinhaColuna(posicao.getLinha() - 1, posicao.getColuna() - 2);
    if (tab.posicaoExiste(p) && podeMover(p)) {
      mat[p.getLinha()][p.getColuna()] = true;
    }

    p.setLinhaColuna(posicao.getLinha() - 2, posicao.getColuna() - 1);
    if (tab.posicaoExiste(p) && podeMover(p)) {
      mat[p.getLinha()][p.getColuna()] = true;
    }

    p.setLinhaColuna(posicao.getLinha() - 2, posicao.getColuna() + 1);
    if (tab.posicaoExiste(p) && podeMover(p)) {
      mat[p.getLinha()][p.getColuna()] = true;
    }

    p.setLinhaColuna(posicao.getLinha() - 1, posicao.getColuna() + 2);
    if (tab.posicaoExiste(p) && podeMover(p)) {
      mat[p.getLinha()][p.getColuna()] = true;
    }

    p.setLinhaColuna(posicao.getLinha() + 1, posicao.getColuna() + 2);
    if (tab.posicaoExiste(p) && podeMover(p)) {
      mat[p.getLinha()][p.getColuna()] = true;
    }

    p.setLinhaColuna(posicao.getLinha() + 2, posicao.getColuna() + 1);
    if (tab.posicaoExiste(p) && podeMover(p)) {
      mat[p.getLinha()][p.getColuna()] = true;
    }

    p.setLinhaColuna(posicao.getLinha() + 2, posicao.getColuna() - 1);
    if (tab.posicaoExiste(p) && podeMover(p)) {
      mat[p.getLinha()][p.getColuna()] = true;
    }

    p.setLinhaColuna(posicao.getLinha() + 1, posicao.getColuna() - 2);
    if (tab.posicaoExiste(p) && podeMover(p)) {
      mat[p.getLinha()][p.getColuna()] = true;
    }

    return mat;
  }

  @Override
  public String toString() {
    return "C";
  }
}
