package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Peao extends PecaDeXadrez {
  public Peao(Tabuleiro tabuleiro, Cor cor) {
    super(tabuleiro, cor);
  }

  @Override
  public boolean[][] movimentosPossiveis() {
    Tabuleiro tab = getTabuleiro();
    boolean[][] mat = new boolean[tab.getLinhas()][tab.getColunas()];

    Posicao p = new Posicao(0, 0);

    if (getCor() == Cor.BRANCO) {
      p.setLinhaColuna(posicao.getLinha() - 1, posicao.getColuna());
      if (tab.posicaoExiste(p) && !tab.haUmaPeca(p)) {
        mat[p.getLinha()][p.getColuna()] = true;
      }

      p.setLinhaColuna(posicao.getLinha() - 2, posicao.getColuna());
      Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
      if (
        tab.posicaoExiste(p)
        && !tab.haUmaPeca(p)
        && getMovimentos() == 0
        && tab.posicaoExiste(p2)
        && !tab.haUmaPeca(p2)
      ) {
        mat[p.getLinha()][p.getColuna()] = true;
      }

      p.setLinhaColuna(posicao.getLinha() - 1, posicao.getColuna() - 1);
      if (tab.posicaoExiste(p) && eUmaPecaDoOponente(p)) {
        mat[p.getLinha()][p.getColuna()] = true;
      }

      p.setLinhaColuna(posicao.getLinha() - 1, posicao.getColuna() + 1);
      if (tab.posicaoExiste(p) && eUmaPecaDoOponente(p)) {
        mat[p.getLinha()][p.getColuna()] = true;
      }
    } else {
      p.setLinhaColuna(posicao.getLinha() + 1, posicao.getColuna());
      if (tab.posicaoExiste(p) && !tab.haUmaPeca(p)) {
        mat[p.getLinha()][p.getColuna()] = true;
      }

      p.setLinhaColuna(posicao.getLinha() + 2, posicao.getColuna());
      Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
      if (
        tab.posicaoExiste(p)
        && !tab.haUmaPeca(p)
        && getMovimentos() == 0
        && tab.posicaoExiste(p2)
        && !tab.haUmaPeca(p2)
      ) {
        mat[p.getLinha()][p.getColuna()] = true;
      }

      p.setLinhaColuna(posicao.getLinha() + 1, posicao.getColuna() - 1);
      if (tab.posicaoExiste(p) && eUmaPecaDoOponente(p)) {
        mat[p.getLinha()][p.getColuna()] = true;
      }

      p.setLinhaColuna(posicao.getLinha() + 1, posicao.getColuna() + 1);
      if (tab.posicaoExiste(p) && eUmaPecaDoOponente(p)) {
        mat[p.getLinha()][p.getColuna()] = true;
      }
    }

    return mat;
  }

  public String toString() {
    return "P";
  }
}
