import java.util.Random;
import java.util.Scanner;

public class BattleDinosaurs {
  String[] dinoName = new String[5];
  int[] attackCard = new int[5];
  int[] agilityCard = new int[5];
  int[] defenceCard = new int[5];
  String[] playerDeck = new String[15];
  int[] drawnPlayerDeck = new int[15];
  String[] cpuDeck = new String[15];
  int[] drawnCpuDeck = new int[15];
  int[] playerCards = new int[5];
  int[] cpuCards = new int[5];
  int playerAttackPoint, playerAgilityPoint, playerDefencePoint;
  int cpuAttackPoint, cpuAgilityPoint, cpuDefencePoint;

  Random rnd = new Random();
  Scanner sc = new Scanner(System.in);

  public void startPhase() {
    // Game Status初期化
    System.out.println("PlayerのDraw----------(Hit Enter!)");
    sc.nextLine();
    // playerCards[0]~[2]に0~14の数字から5つの異なる数字をランダムに選んで格納する
    // 同時にplayerDeckの対応する数字の配列に"Done"と格納する
    for (int i = 0; i < playerCards.length; i++) {
      int playerCard = rnd.nextInt(this.playerDeck.length);
      if (this.drawnPlayerDeck[playerCard] == -1) {
        i--;
        continue;
      } else {
        this.playerCards[i] = playerCard;
        this.drawnPlayerDeck[playerCard] = -1;
      }
    }

    System.out.println("CPUのDraw----------");
    for (int i = 0; i < cpuCards.length; i++) {
      int cpuCard = rnd.nextInt(this.cpuDeck.length);
      if (this.drawnCpuDeck[cpuCard] == -1) {
        i--;
        continue;
      } else {
        this.cpuCards[i] = cpuCard;
        this.drawnCpuDeck[cpuCard] = -1;
      }
    }
    // カードの枚数がそれぞれAttack，Agility，Defenceのポイントになる
    // Attack,Quick,Defenceのカード枚数計測
    this.playerAttackPoint = 0;
    this.playerAgilityPoint = 0;
    this.playerDefencePoint = 0;
    for (int i = 0; i < this.playerCards.length; i++) {
      if (this.playerDeck[this.playerCards[i]].contains("Attack")) {
        this.playerAttackPoint++;
      } else if (this.playerDeck[this.playerCards[i]].contains("Agility")) {
        this.playerAgilityPoint++;
      } else if (this.playerDeck[this.playerCards[i]].contains("Defence")) {
        this.playerDefencePoint++;
      }
    }
    System.out.println("Attack Point:" + this.playerAttackPoint);
    System.out.println("Agility Point:" + this.playerAgilityPoint);
    System.out.println("Defence Point:" + this.playerDefencePoint);

    // Agilityが大きいほうが先攻
    // 10*Attack*agility1/agility2
    // 10*Def

  }

  // PlayerとCPUのDeck(15枚)を作るメソッド
  public void createPlayerDeck() {
    int pcNum = 0;
    int dino1 = rnd.nextInt(this.dinoName.length);
    for (int i = 0; i < attackCard[dino1]; i++, pcNum++) {
      this.playerDeck[pcNum] = "Attack:" + dinoName[dino1];
    }
    for (int i = 0; i < agilityCard[dino1]; i++, pcNum++) {
      this.playerDeck[pcNum] = "Agility:" + dinoName[dino1];
    }
    for (int i = 0; i < defenceCard[dino1]; i++, pcNum++) {
      this.playerDeck[pcNum] = "Defence:" + dinoName[dino1];
    }

    int dino2 = rnd.nextInt(this.dinoName.length);
    for (int i = 0; i < attackCard[dino2]; i++, pcNum++) {
      this.playerDeck[pcNum] = "Attack:" + dinoName[dino2];
    }
    for (int i = 0; i < agilityCard[dino2]; i++, pcNum++) {
      this.playerDeck[pcNum] = "Agility:" + dinoName[dino2];
    }
    for (int i = 0; i < defenceCard[dino2]; i++, pcNum++) {
      this.playerDeck[pcNum] = "Defence:" + dinoName[dino2];
    }

    int dino3 = rnd.nextInt(this.dinoName.length);
    for (int i = 0; i < attackCard[dino3]; i++, pcNum++) {
      this.playerDeck[pcNum] = "Attack:" + dinoName[dino3];
    }
    for (int i = 0; i < agilityCard[dino3]; i++, pcNum++) {
      this.playerDeck[pcNum] = "Agility:" + dinoName[dino3];
    }
    for (int i = 0; i < defenceCard[dino3]; i++, pcNum++) {
      this.playerDeck[pcNum] = "Defence:" + dinoName[dino3];
    }
    System.out.println("Playerの恐竜カードDraw！");
    System.out.println(this.dinoName[dino1]);
    System.out.println(this.dinoName[dino2]);
    System.out.println(this.dinoName[dino3]);

    int cpuNum = 0;
    dino1 = rnd.nextInt(this.dinoName.length);
    for (int i = 0; i < attackCard[dino1]; i++, cpuNum++) {
      this.cpuDeck[cpuNum] = "Attack:" + dinoName[dino1];
    }
    for (int i = 0; i < agilityCard[dino1]; i++, cpuNum++) {
      this.cpuDeck[cpuNum] = "Agility:" + dinoName[dino1];
    }
    for (int i = 0; i < defenceCard[dino1]; i++, cpuNum++) {
      this.cpuDeck[cpuNum] = "Defence:" + dinoName[dino1];
    }

    dino2 = rnd.nextInt(this.dinoName.length);
    for (int i = 0; i < attackCard[dino2]; i++, cpuNum++) {
      this.cpuDeck[cpuNum] = "Attack:" + dinoName[dino2];
    }
    for (int i = 0; i < agilityCard[dino2]; i++, cpuNum++) {
      this.cpuDeck[cpuNum] = "Agility:" + dinoName[dino2];
    }
    for (int i = 0; i < defenceCard[dino2]; i++, cpuNum++) {
      this.cpuDeck[cpuNum] = "Defence:" + dinoName[dino2];
    }

    dino3 = rnd.nextInt(this.dinoName.length);
    for (int i = 0; i < attackCard[dino3]; i++, cpuNum++) {
      this.cpuDeck[cpuNum] = "Attack:" + dinoName[dino3];
    }
    for (int i = 0; i < agilityCard[dino3]; i++, cpuNum++) {
      this.cpuDeck[cpuNum] = "Agility:" + dinoName[dino3];
    }
    for (int i = 0; i < defenceCard[dino3]; i++, cpuNum++) {
      this.cpuDeck[cpuNum] = "Defence:" + dinoName[dino3];
    }
    System.out.println("CPUの恐竜カードDraw！");
    System.out.println(this.dinoName[dino1]);
    System.out.println(this.dinoName[dino2]);
    System.out.println(this.dinoName[dino3]);

  }

  public void initLibrary() {
    this.dinoName[0] = "ティラノ";
    this.attackCard[0] = 3;
    this.agilityCard[0] = 1;
    this.defenceCard[0] = 1;

    this.dinoName[1] = "ヴェロキラプトル";
    this.attackCard[1] = 1;
    this.agilityCard[1] = 3;
    this.defenceCard[1] = 1;

    this.dinoName[2] = "トリケラトプス";
    this.attackCard[2] = 1;
    this.agilityCard[2] = 1;
    this.defenceCard[2] = 3;

    this.dinoName[3] = "プテラノドン";
    this.attackCard[3] = 2;
    this.agilityCard[3] = 2;
    this.defenceCard[3] = 1;

    this.dinoName[4] = "ステゴサウルス";
    this.attackCard[4] = 2;
    this.agilityCard[4] = 1;
    this.defenceCard[4] = 2;

  }
}
