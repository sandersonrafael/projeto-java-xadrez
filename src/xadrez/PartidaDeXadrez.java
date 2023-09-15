package xadrez;

import tabuleiro.Peca;
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

  public PecaDeXadrez realizarMovimentoDeXadrez(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
    Posicao origem = posicaoOrigem.posicaoMatriz();
    Posicao destino = posicaoDestino.posicaoMatriz();
    validarPosicaoDeOrigem(origem);
    Peca pecaCapturada = fazerMovimento(origem, destino);
    return (PecaDeXadrez) pecaCapturada;
  }

  private Peca fazerMovimento(Posicao origem, Posicao destino) {
    Peca peca = tabuleiro.removerPeca(origem);
    Peca pecaCapturada = tabuleiro.removerPeca(destino);
    tabuleiro.posicionarPeca(peca, destino);
    return pecaCapturada;
  }

  private void validarPosicaoDeOrigem(Posicao origem) {
    if (!tabuleiro.haUmaPeca(origem)) throw new XadrezException(
      "Não existe uma peça na posição de origem selecionada"
    );
  }


  private void posicionarNovaPeca(char coluna, int linha, PecaDeXadrez peca) {
    tabuleiro.posicionarPeca(peca, new PosicaoXadrez(coluna, linha).posicaoMatriz());
  }

  private void configuracaoInicial() {
    posicionarNovaPeca('c', 1, new Torre(tabuleiro, Cor.BRANCO));
    posicionarNovaPeca('c', 2, new Torre(tabuleiro, Cor.BRANCO));
    posicionarNovaPeca('d', 2, new Torre(tabuleiro, Cor.BRANCO));
    posicionarNovaPeca('e', 2, new Torre(tabuleiro, Cor.BRANCO));
    posicionarNovaPeca('e', 1, new Torre(tabuleiro, Cor.BRANCO));
    posicionarNovaPeca('d', 1, new Rei(tabuleiro, Cor.BRANCO));

    posicionarNovaPeca('c', 7, new Torre(tabuleiro, Cor.PRETO));
    posicionarNovaPeca('c', 8, new Torre(tabuleiro, Cor.PRETO));
    posicionarNovaPeca('d', 7, new Torre(tabuleiro, Cor.PRETO));
    posicionarNovaPeca('e', 7, new Torre(tabuleiro, Cor.PRETO));
    posicionarNovaPeca('e', 8, new Torre(tabuleiro, Cor.PRETO));
    posicionarNovaPeca('d', 8, new Rei(tabuleiro, Cor.PRETO));
  }
}
