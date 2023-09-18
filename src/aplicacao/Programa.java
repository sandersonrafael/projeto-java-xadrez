package aplicacao;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezException;

public class Programa {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();
    List<PecaDeXadrez> pecasCapturadas = new ArrayList<>();

    while (!partidaDeXadrez.getCheckMate()) {
      try {
        UI.limparTela();
        UI.exibirJogo(partidaDeXadrez, pecasCapturadas);

        System.out.println();
        System.out.print("Origem: ");
        PosicaoXadrez origem = UI.lerPosicaoNoXadrez(sc);

        boolean[][] movimentosPossiveis = partidaDeXadrez.movimentosPossiveis(origem);
        UI.limparTela();
        UI.imprimirTabuleiro(partidaDeXadrez.getPecas(), movimentosPossiveis);

        System.out.println();
        System.out.print("Destino: ");
        PosicaoXadrez destino = UI.lerPosicaoNoXadrez(sc);

        PecaDeXadrez pecaCapturada = partidaDeXadrez.realizarMovimentoDeXadrez(origem, destino);
        if (pecaCapturada != null) pecasCapturadas.add(pecaCapturada);

        if (partidaDeXadrez.getPromovida() != null) {
          System.out.print("Informe uma peça para a promoção (B|C|r|T): ");
          String tipo = sc.nextLine();
          while (!tipo.equals("B") && !tipo.equals("C") && !tipo.equals("r") && !tipo.equals("T")) {
            UI.limparTela();
            UI.exibirJogo(partidaDeXadrez, pecasCapturadas);
            System.out.print("Peça informada é inválida. Informe uma peça válida (B|C|r|T): ");
            tipo = sc.nextLine();
          }
          partidaDeXadrez.substituirPecaPromovida(tipo);
        }
      } catch (XadrezException e) {
        System.out.println(e.getMessage());
        sc.nextLine();
      } catch (InputMismatchException e) {
        System.out.println(e.getMessage());
        sc.nextLine();
      }
    }
    UI.limparTela();
    UI.exibirJogo(partidaDeXadrez, pecasCapturadas);
  }
}
