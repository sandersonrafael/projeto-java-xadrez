package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Rei extends PecaDeXadrez {
  public Rei(Tabuleiro tabuleiro, Cor cor) {
    super(tabuleiro, cor);
  }

  @Override
  public String toString() {
    return "R";
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

    // cima
    p.setLinhaColuna(posicao.getLinha() - 1, posicao.getColuna());
    if (tab.posicaoExiste(p) && podeMover(p)) {
      mat[p.getLinha()][p.getColuna()] = true;
    }
    // baixo
    p.setLinhaColuna(posicao.getLinha() + 1, posicao.getColuna());
    if (tab.posicaoExiste(p) && podeMover(p)) {
      mat[p.getLinha()][p.getColuna()] = true;
    }
    // esquerda
    p.setLinhaColuna(posicao.getLinha(), posicao.getColuna() - 1);
    if (tab.posicaoExiste(p) && podeMover(p)) {
      mat[p.getLinha()][p.getColuna()] = true;
    }
    // direita
    p.setLinhaColuna(posicao.getLinha(), posicao.getColuna() + 1);
    if (tab.posicaoExiste(p) && podeMover(p)) {
      mat[p.getLinha()][p.getColuna()] = true;
    }
    // cima esquerda
    p.setLinhaColuna(posicao.getLinha() - 1, posicao.getColuna() - 1);
    if (tab.posicaoExiste(p) && podeMover(p)) {
      mat[p.getLinha()][p.getColuna()] = true;
    }
    // baixo direita
    p.setLinhaColuna(posicao.getLinha() + 1, posicao.getColuna() + 1);
    if (tab.posicaoExiste(p) && podeMover(p)) {
      mat[p.getLinha()][p.getColuna()] = true;
    }
    // baixo esquerda
    p.setLinhaColuna(posicao.getLinha() + 1, posicao.getColuna() - 1);
    if (tab.posicaoExiste(p) && podeMover(p)) {
      mat[p.getLinha()][p.getColuna()] = true;
    }
    // cima direita
    p.setLinhaColuna(posicao.getLinha() - 1, posicao.getColuna() + 1);
    if (tab.posicaoExiste(p) && podeMover(p)) {
      mat[p.getLinha()][p.getColuna()] = true;
    }

    return mat;
  }
}
