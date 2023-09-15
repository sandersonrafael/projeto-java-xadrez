package tabuleiro;

public class Tabuleiro {
  private int linhas;
  private int colunas;
  private Peca[][] pecas;

  private static final String posicaoInvalida = "Posição informada não existe no tabuleiro";

  public Tabuleiro(int linhas, int colunas) {
    if (linhas < 1 || colunas < 1) throw new TabuleiroException(
      "Necessário haver pelo menos uma linha e uma coluna"
    );
    this.linhas = linhas;
    this.colunas = colunas;
    pecas = new Peca[linhas][colunas];
  }

  public int getLinhas() {
    return linhas;
  }

  public int getColunas() {
    return colunas;
  }

  public Peca peca(int linha, int coluna) {
    if (!posicaoExiste(linha, coluna)) throw new TabuleiroException(posicaoInvalida);
    return pecas[linha][coluna];
  }

  public Peca peca(Posicao posicao) {
    if (!posicaoExiste(posicao)) throw new TabuleiroException(posicaoInvalida);
    return pecas[posicao.getLinha()][posicao.getColuna()];
  }

  public void posicionarPeca(Peca peca, Posicao posicao) {
    if (haUmaPeca(posicao)) throw new TabuleiroException(
      "Já existe uma peça na posição " + posicao
    );
    pecas[posicao.getLinha()][posicao.getColuna()] = peca;
    peca.posicao = posicao;
  }

  public boolean posicaoExiste(int linha, int coluna) {
    return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
  }

  public boolean posicaoExiste(Posicao posicao) {
    return posicaoExiste(posicao.getLinha(), posicao.getColuna());
  }

  public boolean haUmaPeca(Posicao posicao) {
    if (!posicaoExiste(posicao)) throw new TabuleiroException(posicaoInvalida);
    return peca(posicao) != null;
  }
}
