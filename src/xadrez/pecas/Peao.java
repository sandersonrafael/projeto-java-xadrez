package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;

public class Peao extends PecaDeXadrez {
  private PartidaDeXadrez partidaDeXadrez;

  public Peao(Tabuleiro tabuleiro, Cor cor, PartidaDeXadrez partidaDeXadrez) {
    super(tabuleiro, cor);
    this.partidaDeXadrez = partidaDeXadrez;
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

      // movimento especial En Passant branco
      if (posicao.getLinha() == 3) {
        Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
        if (tab.posicaoExiste(esquerda) && eUmaPecaDoOponente(esquerda) && tab.peca(esquerda) == partidaDeXadrez.getRiscoEnPassant()) {
          mat[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
        }

        Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
        if (tab.posicaoExiste(direita) && eUmaPecaDoOponente(direita) && tab.peca(direita) == partidaDeXadrez.getRiscoEnPassant()) {
          mat[direita.getLinha() - 1][direita.getColuna()] = true;
        }
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

      // movimento especial En Passant branco
      if (posicao.getLinha() == 4) {
        Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
        if (tab.posicaoExiste(esquerda) && eUmaPecaDoOponente(esquerda) && tab.peca(esquerda) == partidaDeXadrez.getRiscoEnPassant()) {
          mat[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
        }

        Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
        if (tab.posicaoExiste(direita) && eUmaPecaDoOponente(direita) && tab.peca(direita) == partidaDeXadrez.getRiscoEnPassant()) {
          mat[direita.getLinha() + 1][direita.getColuna()] = true;
        }
      }
    }

    return mat;
  }

  public String toString() {
    return "P";
  }
}
