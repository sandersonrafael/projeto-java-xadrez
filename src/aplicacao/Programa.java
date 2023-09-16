package aplicacao;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezException;

public class Programa {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();

    while (true) {
      try {
        UI.limparTela();
        UI.exibirJogo(partidaDeXadrez);
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
      } catch (XadrezException e) {
        System.out.println(e.getMessage());
        sc.nextLine();
      } catch (InputMismatchException e) {
        System.out.println(e.getMessage());
        sc.nextLine();
      }
    }
  }
}
