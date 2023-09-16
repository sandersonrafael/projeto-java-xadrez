package xadrez;

import java.util.ArrayList;
import java.util.List;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez {
  private int turno;
  private Cor jogadorAtual;
  private Tabuleiro tabuleiro;

  private List<Peca> pecasNoTabuleiro = new ArrayList<>();
  private List<Peca> pecasCapturadas = new ArrayList<>();

  public PartidaDeXadrez() {
    tabuleiro = new Tabuleiro(8, 8);
    turno = 1;
    jogadorAtual = Cor.BRANCO;
    configuracaoInicial();
  }

  public int getTurno() {
    return turno;
  }

  public Cor getJogadorAtual() {
    return jogadorAtual;
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

  public boolean[][] movimentosPossiveis(PosicaoXadrez posicaoDeOrigem) {
    Posicao posicao = posicaoDeOrigem.posicaoMatriz();
    validarPosicaoDeOrigem(posicao);
    return tabuleiro.peca(posicao).movimentosPossiveis();
  }

  public PecaDeXadrez realizarMovimentoDeXadrez(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
    Posicao origem = posicaoOrigem.posicaoMatriz();
    Posicao destino = posicaoDestino.posicaoMatriz();
    validarPosicaoDeOrigem(origem);
    validarPosicaoDeDestino(origem, destino);
    Peca pecaCapturada = fazerMovimento(origem, destino);
    proximoTurno();
    return (PecaDeXadrez) pecaCapturada;
  }

  private Peca fazerMovimento(Posicao origem, Posicao destino) {
    Peca peca = tabuleiro.removerPeca(origem);
    Peca pecaCapturada = tabuleiro.removerPeca(destino);
    tabuleiro.posicionarPeca(peca, destino);

    if (pecaCapturada != null) {
      pecasNoTabuleiro.remove(pecaCapturada);
      pecasCapturadas.add(pecaCapturada);
    }

    return pecaCapturada;
  }

  private void validarPosicaoDeOrigem(Posicao origem) {
    if (!tabuleiro.haUmaPeca(origem)) throw new XadrezException(
      "Não há peça na posição de origem selecionada"
    );
    if (jogadorAtual != ((PecaDeXadrez) tabuleiro.peca(origem)).getCor()) {
      throw new XadrezException("Peça escolhida não pertence ao jogador da vez");
    }
    if (!tabuleiro.peca(origem).haMovimentosPossiveis()) {
      throw new XadrezException("Não existem movimentos possíveis para a peça selecionada");
    }
  }

  private void validarPosicaoDeDestino(Posicao origem, Posicao destino) {
    if (!tabuleiro.peca(origem).movimentoPossivel(destino)) {
      throw new XadrezException("A peça escolhida não pode se mover para a posição selecionada");
    }
  }

  private void proximoTurno() {
    turno++;
    jogadorAtual = jogadorAtual == Cor.BRANCO ? Cor.PRETO : Cor.BRANCO;
  }

  private void posicionarNovaPeca(char coluna, int linha, PecaDeXadrez peca) {
    tabuleiro.posicionarPeca(peca, new PosicaoXadrez(coluna, linha).posicaoMatriz());
    pecasNoTabuleiro.add(peca);
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
