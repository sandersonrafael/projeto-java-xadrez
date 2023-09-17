package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Rainha extends PecaDeXadrez {
  public Rainha(Tabuleiro tabuleiro, Cor cor) {
    super(tabuleiro, cor);
  }

  @Override
  public boolean[][] movimentosPossiveis() {
    Tabuleiro tab = getTabuleiro();
    boolean[][] mat = new boolean[tab.getLinhas()][tab.getColunas()];

    Posicao p = new Posicao(0, 0);

    // verificar acima da peça
    p.setLinhaColuna(posicao.getLinha() - 1, posicao.getColuna());
    while (tab.posicaoExiste(p) && !tab.haUmaPeca(p)) {
      mat[p.getLinha()][p.getColuna()] = true;
      p.setLinha(p.getLinha() - 1);
    }
    if (tab.posicaoExiste(p) && eUmaPecaDoOponente(p)) {
      mat[p.getLinha()][p.getColuna()] = true;
    }

    // verificar à esquerda da peça
    p.setLinhaColuna(posicao.getLinha(), posicao.getColuna() - 1);
    while (tab.posicaoExiste(p) && !tab.haUmaPeca(p)) {
      mat[p.getLinha()][p.getColuna()] = true;
      p.setColuna(p.getColuna() - 1);
    }
    if (tab.posicaoExiste(p) && eUmaPecaDoOponente(p)) {
      mat[p.getLinha()][p.getColuna()] = true;
    }

    // verificar à direita da peça
    p.setLinhaColuna(posicao.getLinha(), posicao.getColuna() + 1);
    while (tab.posicaoExiste(p) && !tab.haUmaPeca(p)) {
      mat[p.getLinha()][p.getColuna()] = true;
      p.setColuna(p.getColuna() + 1);
    }
    if (tab.posicaoExiste(p) && eUmaPecaDoOponente(p)) {
      mat[p.getLinha()][p.getColuna()] = true;
    }

    // verificar abaixo da peça
    p.setLinhaColuna(posicao.getLinha() + 1, posicao.getColuna());
    while (tab.posicaoExiste(p) && !tab.haUmaPeca(p)) {
      mat[p.getLinha()][p.getColuna()] = true;
      p.setLinha(p.getLinha() + 1);
    }
    if (tab.posicaoExiste(p) && eUmaPecaDoOponente(p)) {
      mat[p.getLinha()][p.getColuna()] = true;
    }

    // verificar cima esquerda
    p.setLinhaColuna(posicao.getLinha() - 1, posicao.getColuna() - 1);
    while (tab.posicaoExiste(p) && !tab.haUmaPeca(p)) {
      mat[p.getLinha()][p.getColuna()] = true;
      p.setLinhaColuna(p.getLinha() - 1, p.getColuna() - 1);
    }
    if (tab.posicaoExiste(p) && eUmaPecaDoOponente(p)) {
      mat[p.getLinha()][p.getColuna()] = true;
    }

    // verificar cima direita
    p.setLinhaColuna(posicao.getLinha() - 1, posicao.getColuna() + 1);
    while (tab.posicaoExiste(p) && !tab.haUmaPeca(p)) {
      mat[p.getLinha()][p.getColuna()] = true;
      p.setLinhaColuna(p.getLinha() - 1, p.getColuna() + 1);
    }
    if (tab.posicaoExiste(p) && eUmaPecaDoOponente(p)) {
      mat[p.getLinha()][p.getColuna()] = true;
    }

    // verificar baixo direita
    p.setLinhaColuna(posicao.getLinha() + 1, posicao.getColuna() + 1);
    while (tab.posicaoExiste(p) && !tab.haUmaPeca(p)) {
      mat[p.getLinha()][p.getColuna()] = true;
      p.setLinhaColuna(p.getLinha() + 1, p.getColuna() + 1);
    }
    if (tab.posicaoExiste(p) && eUmaPecaDoOponente(p)) {
      mat[p.getLinha()][p.getColuna()] = true;
    }

    // verificar baixo esquerda
    p.setLinhaColuna(posicao.getLinha() + 1, posicao.getColuna() - 1);
    while (tab.posicaoExiste(p) && !tab.haUmaPeca(p)) {
      mat[p.getLinha()][p.getColuna()] = true;
      p.setLinhaColuna(p.getLinha() + 1, p.getColuna() - 1);
    }
    if (tab.posicaoExiste(p) && eUmaPecaDoOponente(p)) {
      mat[p.getLinha()][p.getColuna()] = true;
    }

    return mat;
  }

  @Override
  public String toString() {
    return "r";
  }
}
