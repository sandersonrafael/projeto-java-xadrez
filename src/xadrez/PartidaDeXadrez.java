package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez {
  private int turno;
  private Cor jogadorAtual;
  private Tabuleiro tabuleiro;
  private boolean check;
  private boolean checkMate;

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

  public boolean getCheck() {
    return check;
  }

  public boolean getCheckMate() {
    return checkMate;
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

    if (testarCheck(jogadorAtual)) {
      desfazerMovimento(origem, destino, pecaCapturada);
      throw new XadrezException("Você não pode se colocar em check");
    }

    check = testarCheck(oponente(jogadorAtual)) ? true : false;

    if (testarCheckMate(oponente(jogadorAtual))) {
      checkMate = true;
    } else {
      proximoTurno();
    }

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

  private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
    Peca p = tabuleiro.removerPeca(destino);
    tabuleiro.posicionarPeca(p, origem);

    if (pecaCapturada != null) {
      tabuleiro.posicionarPeca(pecaCapturada, destino);
      pecasCapturadas.remove(pecaCapturada);
      pecasNoTabuleiro.add(pecaCapturada);
    }
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

  private Cor oponente(Cor cor) {
    return cor == Cor.BRANCO ? Cor.PRETO : Cor.BRANCO;
  }

  private PecaDeXadrez rei(Cor cor) {
    List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez) x).getCor() == cor).collect(Collectors.toList());
    for (Peca p : lista) {
      if (p instanceof Rei) {
        return (PecaDeXadrez) p;
      }
    }
    throw new IllegalStateException("Não existe o rei da cor " + cor + " no tabuleiro");
  }

  private boolean testarCheck(Cor cor) {
    Posicao posicaoDoRei = rei(cor).getPosicaoXadrez().posicaoMatriz();
    List<Peca> pecasDoOponente = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez) x).getCor() == oponente(cor)).collect(Collectors.toList());
    for (Peca peca : pecasDoOponente) {
      boolean[][] mat = peca.movimentosPossiveis();
      if (mat[posicaoDoRei.getLinha()][posicaoDoRei.getColuna()]) {
        return true;
      }
    }
    return false;
  }

  private boolean testarCheckMate(Cor cor) {
    if (!testarCheck(cor)) return false;
    List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez) x).getCor() == cor).collect(Collectors.toList());
    for (Peca peca : lista) {
      boolean[][] mat = peca.movimentosPossiveis();
      for (int i = 0; i < tabuleiro.getLinhas(); i++) {
        for (int j = 0; j < tabuleiro.getColunas(); j++) {
          if (mat[i][j]) {
            Posicao origem = ((PecaDeXadrez) peca).getPosicaoXadrez().posicaoMatriz();
            Posicao destino = new Posicao(i, j);
            Peca pecaCapturada = fazerMovimento(origem, destino);
            boolean testarCheck = testarCheck(cor);
            desfazerMovimento(origem, destino, pecaCapturada);
            if (!testarCheck) return false;
          }
        }
      }
    }
    return true;
  }

  private void posicionarNovaPeca(char coluna, int linha, PecaDeXadrez peca) {
    tabuleiro.posicionarPeca(peca, new PosicaoXadrez(coluna, linha).posicaoMatriz());
    pecasNoTabuleiro.add(peca);
  }

  private void configuracaoInicial() {
    posicionarNovaPeca('h', 7, new Torre(tabuleiro, Cor.BRANCO));
    posicionarNovaPeca('d', 1, new Torre(tabuleiro, Cor.BRANCO));
    posicionarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO));

    posicionarNovaPeca('b', 8, new Torre(tabuleiro, Cor.PRETO));
    posicionarNovaPeca('a', 8, new Rei(tabuleiro, Cor.PRETO));
  }
}
