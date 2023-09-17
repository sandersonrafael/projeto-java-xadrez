package aplicacao;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import xadrez.Cor;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.PosicaoXadrez;

public class UI {
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String ANSI_WHITE = "\u001B[37m";

  public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
  public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
  public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
  public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
  public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
  public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
  public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
  public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

  public static void limparTela() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  public static PosicaoXadrez lerPosicaoNoXadrez(Scanner sc) {
    try {
      String entradaDoUsuario = sc.nextLine();
      char coluna = entradaDoUsuario.charAt(0);
      int linha = Integer.parseInt(entradaDoUsuario.substring(1));
      return new PosicaoXadrez(coluna, linha);
    } catch (RuntimeException e) {
      throw new InputMismatchException("Erro na leitura da posição. Valores válidos são de a1 até h8");
    }
  }

  public static void exibirJogo(PartidaDeXadrez partidaDeXadrez, List<PecaDeXadrez> pecasCapturadas) {
    imprimirTabuleiro(partidaDeXadrez.getPecas());
    System.out.println();
    exibirPecasCapturadas(pecasCapturadas);
    System.out.println();
    System.out.println("Turno : " + partidaDeXadrez.getTurno());

    if (!partidaDeXadrez.getCheckMate()) {
      System.out.println("Aguardando jogador: " + partidaDeXadrez.getJogadorAtual());
      if (partidaDeXadrez.getCheck()) {
        System.out.println("Usuário em Xeque!");
      }
    } else {
      System.out.println("Xeque Mate!");
      System.out.println("O jogador " + partidaDeXadrez.getJogadorAtual() + " venceu o jogo!");
    }
  }

  public static void imprimirTabuleiro(PecaDeXadrez[][] pecas) {
    for (int i = 0; i < pecas.length; i++) {
      System.out.print((8 - i) + " ");
      for (int j = 0; j < pecas[0].length; j++) {
        imprimirPeca(pecas[i][j], false);
      }
      System.out.println();
    }
    System.out.println("  a b c d e f g h");
  }

  public static void imprimirTabuleiro(PecaDeXadrez[][] pecas, boolean[][] movimentosPossiveis) {
    for (int i = 0; i < pecas.length; i++) {
      System.out.print((8 - i) + " ");
      for (int j = 0; j < pecas[0].length; j++) {
        imprimirPeca(pecas[i][j], movimentosPossiveis[i][j]);
      }
      System.out.println();
    }
    System.out.println("  a b c d e f g h");
  }

  private static void imprimirPeca(PecaDeXadrez peca, boolean corDeFundo) {
    if (corDeFundo) {
      System.out.print(ANSI_CYAN_BACKGROUND);
    }
    if (peca == null) {
      System.out.print("-" + ANSI_RESET);
    } else if (peca.getCor() == Cor.BRANCO) {
      System.out.print(ANSI_WHITE + peca + ANSI_RESET);
    } else {
      System.out.print(ANSI_YELLOW + peca + ANSI_RESET);
    }
    System.out.print(" ");
  }

  private static void exibirPecasCapturadas(List<PecaDeXadrez> pecasCapturadas) {
    List<PecaDeXadrez> branco = pecasCapturadas.stream().filter(x -> x.getCor() == Cor.BRANCO).collect(Collectors.toList());
    List<PecaDeXadrez> preto = pecasCapturadas.stream().filter(x -> x.getCor() == Cor.PRETO).collect(Collectors.toList());
    System.out.println("Peças capturadas:");
    System.out.print("Brancas: ");
    System.out.print(ANSI_WHITE);
    System.out.println(Arrays.toString(branco.toArray()));
    System.out.print(ANSI_RESET);

    System.out.print("Pretas: ");
    System.out.print(ANSI_YELLOW);
    System.out.println(Arrays.toString(preto.toArray()));
    System.out.print(ANSI_RESET);
  }
}
