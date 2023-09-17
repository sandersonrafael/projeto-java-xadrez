package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;

public class Rei extends PecaDeXadrez {
  private PartidaDeXadrez partidaDeXadrez;
  public Rei(Tabuleiro tabuleiro, Cor cor, PartidaDeXadrez partidaDeXadrez) {
    super(tabuleiro, cor);
    this.partidaDeXadrez = partidaDeXadrez;
  }

  private boolean podeMover(Posicao posicao) {
    Tabuleiro tab = getTabuleiro();
    PecaDeXadrez p = (PecaDeXadrez) tab.peca(posicao);
    return p == null || p.getCor() != getCor();
  }

  private boolean testarPossibilidadeDeRoque(Posicao posicao) {
    PecaDeXadrez peca = (PecaDeXadrez) getTabuleiro().peca(posicao);
    return peca != null && peca instanceof Torre && peca.getCor() == getCor() && peca.getMovimentos() == 0;
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

    // movimento especial Roque
    if (getMovimentos() == 0 && !partidaDeXadrez.getCheck()) {
      // roque do lado do rei
      Posicao posicaoTorre1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
      if (testarPossibilidadeDeRoque(posicaoTorre1)) {
        Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
        Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);
        if (tab.peca(p1) == null && tab.peca(p2) == null) {
          mat[posicao.getLinha()][posicao.getColuna() + 2] = true;
        }
      }

      // roque do lado da rainha
      Posicao posicaoTorre2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 4);
      if (testarPossibilidadeDeRoque(posicaoTorre2)) {
        Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
        Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
        Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);
        if (tab.peca(p1) == null && tab.peca(p2) == null && tab.peca(p3) == null) {
          mat[posicao.getLinha()][posicao.getColuna() - 2] = true;
        }
      }
    }

    return mat;
  }

  @Override
  public String toString() {
    return "R";
  }
}
