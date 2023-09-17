package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rainha;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez {
  private int turno;
  private Cor jogadorAtual;
  private Tabuleiro tabuleiro;
  private boolean check;
  private boolean checkMate;
  private PecaDeXadrez riscoEnPassant;

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

  public PecaDeXadrez getRiscoEnPassant() {
    return riscoEnPassant;
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

    PecaDeXadrez pecaMovida = (PecaDeXadrez) tabuleiro.peca(destino);

    check = testarCheck(oponente(jogadorAtual)) ? true : false;

    if (testarCheckMate(oponente(jogadorAtual))) {
      checkMate = true;
    } else {
      proximoTurno();
    }

    // movimento especial En Passant
    if (pecaMovida instanceof Peao && (destino.getLinha() == origem.getLinha() - 2) || (destino.getLinha() == origem.getLinha() + 2)) {
      riscoEnPassant = pecaMovida;
    } else {
      riscoEnPassant = null;
    }

    return (PecaDeXadrez) pecaCapturada;
  }

  private Peca fazerMovimento(Posicao origem, Posicao destino) {
    PecaDeXadrez peca = (PecaDeXadrez) tabuleiro.removerPeca(origem);
    peca.aumentarMovimentos();

    Peca pecaCapturada = tabuleiro.removerPeca(destino);
    tabuleiro.posicionarPeca(peca, destino);

    if (pecaCapturada != null) {
      pecasNoTabuleiro.remove(pecaCapturada);
      pecasCapturadas.add(pecaCapturada);
    }

    // movimento especial Roque lado do rei
    if (peca instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
      Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() + 3);
      Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() + 1);
      PecaDeXadrez torre = (PecaDeXadrez) tabuleiro.removerPeca(origemTorre);
      tabuleiro.posicionarPeca(torre, destinoTorre);
      torre.aumentarMovimentos();
    }

    // movimento especial Roque lado do rainha
    if (peca instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
      Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() - 4);
      Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() - 1);
      PecaDeXadrez torre = (PecaDeXadrez) tabuleiro.removerPeca(origemTorre);
      tabuleiro.posicionarPeca(torre, destinoTorre);
      torre.aumentarMovimentos();
    }

    // movimento especial En Passant
    if (peca instanceof Peao) {
      if (origem.getColuna() != destino.getColuna() && pecaCapturada == null) {
        Posicao posicaoDoPeao;
        if (peca.getCor() == Cor.BRANCO) {
          posicaoDoPeao = new Posicao(destino.getLinha() + 1, destino.getColuna());
        } else {
          posicaoDoPeao = new Posicao(destino.getLinha() - 1, destino.getColuna());
        }
        pecaCapturada = tabuleiro.removerPeca(posicaoDoPeao);
        pecasCapturadas.add(pecaCapturada);
        pecasNoTabuleiro.remove(pecaCapturada);
      }
    }

    return pecaCapturada;
  }

  private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
    PecaDeXadrez peca = (PecaDeXadrez) tabuleiro.removerPeca(destino);
    peca.diminuirMovimentos();
    tabuleiro.posicionarPeca(peca, origem);

    if (pecaCapturada != null) {
      tabuleiro.posicionarPeca(pecaCapturada, destino);
      pecasCapturadas.remove(pecaCapturada);
      pecasNoTabuleiro.add(pecaCapturada);
    }

    // movimento especial Roque lado do rei
    if (peca instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
      Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() + 3);
      Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() + 1);
      PecaDeXadrez torre = (PecaDeXadrez) tabuleiro.removerPeca(destinoTorre);
      tabuleiro.posicionarPeca(torre, origemTorre);
      torre.diminuirMovimentos();
    }

    // movimento especial Roque lado do rainha
    if (peca instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
      Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() - 4);
      Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() - 1);
      PecaDeXadrez torre = (PecaDeXadrez) tabuleiro.removerPeca(destinoTorre);
      tabuleiro.posicionarPeca(torre, origemTorre);
      torre.diminuirMovimentos();
    }

    // desfazer movimento especial En Passant
    if (peca instanceof Peao) {
      if (origem.getColuna() != destino.getColuna() && pecaCapturada == riscoEnPassant) {
        PecaDeXadrez peao = (PecaDeXadrez) tabuleiro.removerPeca(destino);
        Posicao posicaoDoPeao;
        if (peca.getCor() == Cor.BRANCO) {
          posicaoDoPeao = new Posicao(3, destino.getColuna());
        } else {
          posicaoDoPeao = new Posicao(4, destino.getColuna());
        }
        tabuleiro.posicionarPeca(peao, posicaoDoPeao);
      }
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
    posicionarNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCO));
    posicionarNovaPeca('b', 1, new Cavalo(tabuleiro, Cor.BRANCO));
    posicionarNovaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
    posicionarNovaPeca('d', 1, new Rainha(tabuleiro, Cor.BRANCO));
    posicionarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO, this));
    posicionarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCO));
    posicionarNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.BRANCO));
    posicionarNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCO));
    posicionarNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCO, this));
    posicionarNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCO, this));
    posicionarNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCO, this));
    posicionarNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCO, this));
    posicionarNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCO, this));
    posicionarNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCO, this));
    posicionarNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCO, this));
    posicionarNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCO, this));

    posicionarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETO));
    posicionarNovaPeca('b', 8, new Cavalo(tabuleiro, Cor.PRETO));
    posicionarNovaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETO));
    posicionarNovaPeca('d', 8, new Rainha(tabuleiro, Cor.PRETO));
    posicionarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETO, this));
    posicionarNovaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETO));
    posicionarNovaPeca('g', 8, new Cavalo(tabuleiro, Cor.PRETO));
    posicionarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETO));
    posicionarNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETO, this));
    posicionarNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETO, this));
    posicionarNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETO, this));
    posicionarNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETO, this));
    posicionarNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETO, this));
    posicionarNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETO, this));
    posicionarNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETO, this));
    posicionarNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETO, this));
  }
}
