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
  int playerAttackPoint, playerAgilityPoint, playerDefencePoint, playerOffencePoint, playerGuardPoint;
  int cpuAttackPoint, cpuAgilityPoint, cpuDefencePoint, cpuOffencePoint, cpuGuardPoint;
  int playerHp = 100;
  int cpuHp = 100;
  String playerSpecialEffect = "";
  String cpuSpecialEffect = "";
  int drawnNum = 0;

  Random rnd = new Random();
  Scanner sc = new Scanner(System.in);

  public void startPhase() {
    // Game Status初期化
    this.playerSpecialEffect = "";
    this.cpuSpecialEffect = "";
    if (this.drawnNum >= 15) {
      System.out.println("Deckがなくなった");
      this.drawnNum = 0;
      for (int i = 0; i < this.drawnPlayerDeck.length; i++) {
        this.drawnPlayerDeck[i] = 0;
        this.drawnCpuDeck[i] = 0;
      }
      this.createDeck();
    }

    System.out.println("Playerの恐竜カードDraw----------(Hit Enter!)");
    sc.nextLine();
    // playerCards[0]~[4]に0~14の数字から5つの異なる数字をランダムに選んで格納する
    // 同時にdrawnPlayerDeckの対応する数字の配列に-1と格納する
    for (int i = 0; i < playerCards.length; i++) {
      int playerCard = rnd.nextInt(this.playerDeck.length);
      if (this.drawnPlayerDeck[playerCard] == -1) {
        i--;
        continue;
      } else {
        this.drawnNum++;// Drawした枚数をカウントアップ
        this.playerCards[i] = playerCard;
        this.drawnPlayerDeck[playerCard] = -1;
      }
    }
    for (int card : this.playerCards) {
      System.out.println(this.playerDeck[card]);
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
    System.out.println("Attack Card:" + this.playerAttackPoint);
    System.out.println("Agility Card:" + this.playerAgilityPoint);
    System.out.println("Defence Card:" + this.playerDefencePoint);

    System.out.println("CPUの恐竜カードDraw----------(Hit Enter!)");
    sc.nextLine();
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

    for (int card : this.cpuCards) {
      System.out.println(this.cpuDeck[card]);
    }

    // カードの枚数がそれぞれAttack，Agility，Defenceのポイントになる
    // Attack,Quick,Defenceのカード枚数計測
    this.cpuAttackPoint = 0;
    this.cpuAgilityPoint = 0;
    this.cpuDefencePoint = 0;
    for (int i = 0; i < this.cpuCards.length; i++) {
      if (this.cpuDeck[this.cpuCards[i]].contains("Attack")) {
        this.cpuAttackPoint++;
      } else if (this.cpuDeck[this.cpuCards[i]].contains("Agility")) {
        this.cpuAgilityPoint++;
      } else if (this.cpuDeck[this.cpuCards[i]].contains("Defence")) {
        this.cpuDefencePoint++;
      }
    }
    System.out.println("Attack Card:" + this.cpuAttackPoint);
    System.out.println("Agility Card:" + this.cpuAgilityPoint);
    System.out.println("Defence Card:" + this.cpuDefencePoint);

    System.out.println("Battle Start!-------(Hit Enter)");
    sc.nextLine();
    // Offence Point/Guard Pointの計算
    // offence=10*Attack*agility1/agility2
    // guard=10*Def
    // agilityが0のときの処理
    if (this.cpuAgilityPoint == 0) {
      if (this.playerAgilityPoint == 0) {
        this.playerOffencePoint = 10 * this.playerAttackPoint;
      } else {
        this.playerOffencePoint = 10 * this.playerAttackPoint * this.playerAgilityPoint;
      }
    } else if (this.playerAgilityPoint == 0) {
      this.cpuOffencePoint = 10 * this.cpuAttackPoint * this.cpuAgilityPoint;
    } else {
      this.playerOffencePoint = 10 * this.playerAttackPoint * this.playerAgilityPoint / this.cpuAgilityPoint;
      this.cpuOffencePoint = 10 * this.cpuAttackPoint * this.cpuAgilityPoint / this.playerAgilityPoint;
    }
    this.playerGuardPoint = 10 * this.playerDefencePoint;
    this.cpuGuardPoint = 10 * this.cpuDefencePoint;

    // Agilityが大きいほうが先攻．等しい場合はPlayerから
    if (this.playerAgilityPoint >= this.cpuAgilityPoint) {
      System.out.println("Playerの攻撃！");
      // カード特殊効果の処理
      if (this.playerAttackPoint >= 3) {
        System.out.println("PlayerのAttack Card3枚による貫通攻撃!");
        this.playerSpecialEffect = "piercing";
      } else if (this.playerAttackPoint >= 2) {
        System.out.println("PlayerのAttack Card2枚による攻撃力倍加！");
        this.playerOffencePoint = this.playerOffencePoint * 2;
      }
      if (this.cpuDefencePoint >= 3) {
        System.out.println("CPUのDefence Card3枚によるカウンター！");
        this.cpuSpecialEffect = "counter";
      } else if (this.cpuDefencePoint >= 2) {
        System.out.println("CPUのDefence Card2枚による防御力倍加！");
        this.cpuGuardPoint = this.cpuGuardPoint * 2;
      }
      // CPUへのダメージ処理
      if (this.playerSpecialEffect.isEmpty() == true) {// specialEffectがない場合，playerOffencepointからcpuGuardPointを引いたものをcpuHpから引く
        if (this.cpuSpecialEffect.isEmpty() == true) {
          int cpuDamage = this.playerOffencePoint - this.cpuGuardPoint;
          if (cpuDamage > 0) {
            System.out.println("CPUは" + cpuDamage + "ポイントのダメージ！");
            this.cpuHp = this.cpuHp - cpuDamage;
          } else {
            System.out.println("CPUはダメージを受けなかった");
          }
        } else if (this.cpuSpecialEffect.contains("counter")) {// CPUがcounterモードの場合，playerOffencePoint分のダメージをplayerHP(php)が受ける
          System.out.println("CPUのカウンター！");
          System.out.println("Playerは" + this.playerOffencePoint + "ポイントのダメージ！");
          this.playerHp = this.playerHp - this.playerOffencePoint;
        }
      } else if (this.playerSpecialEffect.contains("piercing")) {// PlayerがpiercingモードでCPUがcounterモードでない場合，cpuのGuardPointを無視してcpuHpにダメージを与える
        System.out.println("Playerの防御無視攻撃！");
        if (this.cpuSpecialEffect.contains("counter")) {
          System.out.println("CPUのカウンター！");
          System.out.println("Playerは" + this.playerOffencePoint + "ポイントのダメージ！");
          this.playerHp = this.playerHp - this.playerOffencePoint;
        } else {
          System.out.println("CPUは" + this.playerOffencePoint + "ポイントのダメージ！");
          this.cpuHp = this.cpuHp - this.playerOffencePoint;
        }
      }
      if (this.cpuHp > 0) {
        System.out.println("CPUの攻撃！");
        if (this.cpuAttackPoint >= 3) {
          System.out.println("CPUのAttack Card3枚による貫通攻撃!");
          this.cpuSpecialEffect = "piercing";
        } else if (this.cpuAttackPoint >= 2) {
          System.out.println("CPUのAttack Card2枚による攻撃力倍加！");
          this.cpuOffencePoint = this.cpuOffencePoint * 2;
        }

        if (this.playerDefencePoint >= 3) {
          System.out.println("PlayerのDefence Card3枚によるカウンター！");
          this.playerSpecialEffect = "counter";
        } else if (this.playerDefencePoint >= 2) {
          System.out.println("PlayerのDefence Card2枚による防御力倍加！");
          this.playerGuardPoint = this.playerGuardPoint * 2;
        }
        if (this.cpuSpecialEffect.isEmpty() == true) {// specialEffectがない場合，cpuOffencepointからplayerGuardPointを引いたものをplayerHpから引く
          if (this.playerSpecialEffect.isEmpty() == true) {
            int playerDamage = this.cpuOffencePoint - this.playerGuardPoint;
            if (playerDamage >= 0) {
              System.out.println("Playerは" + playerDamage + "ポイントのダメージ！");
              this.playerHp = this.playerHp - playerDamage;
            } else {
              System.out.println("Playerはダメージを受けなかった");
            }
          } else if (this.playerSpecialEffect.contains("counter")) {// CPUがcounterモードの場合，cpuOffencePoint分のダメージをcpuHP(php)が受ける
            System.out.println("Playerのカウンター！");
            System.out.println("CPUは" + this.cpuOffencePoint + "ポイントのダメージ！");
            this.cpuHp = this.cpuHp - this.cpuOffencePoint;
          }
        } else if (this.cpuSpecialEffect.contains("piercing")) {// PlayerがpiercingモードでCPUがcounterモードでない場合，playerのGuardPointを無視してplayerHpにダメージを与える
          System.out.println("CPUの防御無視攻撃！");
          if (this.playerSpecialEffect.contains("counter")) {
            System.out.println("Playerのカウンター！");
            System.out.println("CPUは" + this.cpuOffencePoint + "ポイントのダメージ！");
            this.cpuHp = this.cpuHp - this.cpuOffencePoint;
          } else {
            System.out.println("Playerは" + this.cpuOffencePoint + "ポイントのダメージ！");
            this.playerHp = this.playerHp - this.cpuOffencePoint;
          }
        }
      }
    } else {
      System.out.println("CPUの攻撃！");
      if (this.cpuAttackPoint >= 3) {
        System.out.println("Attack Card3枚による貫通攻撃!");
        this.cpuSpecialEffect = "piercing";
      } else if (this.cpuAttackPoint >= 2) {
        System.out.println("Attack Card2枚による攻撃力倍加！");
        this.cpuOffencePoint = this.cpuOffencePoint * 2;
      }

      if (this.playerDefencePoint >= 3) {
        System.out.println("Defence Card3枚によるカウンター！");
        this.playerSpecialEffect = "counter";
      } else if (this.playerDefencePoint >= 2) {
        System.out.println("Defence Card2枚による防御力倍加！");
        this.playerGuardPoint = this.playerGuardPoint * 2;
      }
      if (this.cpuSpecialEffect.isEmpty() == true) {// specialEffectがない場合，cpuOffencepointからplayerGuardPointを引いたものをplayerHpから引く
        if (this.playerSpecialEffect.isEmpty() == true) {
          int playerDamage = this.cpuOffencePoint - this.playerGuardPoint;
          if (playerDamage >= 0) {
            System.out.println("Playerは" + playerDamage + "ポイントのダメージ！");
            this.playerHp = this.playerHp - playerDamage;
          } else {
            System.out.println("Playerはダメージを受けなかった");
          }
        } else if (this.playerSpecialEffect.contains("counter")) {// CPUがcounterモードの場合，cpuOffencePoint分のダメージをcpuHP(php)が受ける
          System.out.println("Playerのカウンター！");
          System.out.println("CPUは" + this.cpuOffencePoint + "ポイントのダメージ！");
          this.cpuHp = this.cpuHp - this.cpuOffencePoint;
        }
      } else if (this.cpuSpecialEffect.contains("piercing")) {// PlayerがpiercingモードでCPUがcounterモードでない場合，playerのGuardPointを無視してplayerHpにダメージを与える
        System.out.println("CPUの防御無視攻撃！");
        if (this.playerSpecialEffect.contains("counter")) {
          System.out.println("Playerのカウンター！");
          System.out.println("CPUは" + this.cpuOffencePoint + "ポイントのダメージ！");
          this.cpuHp = this.cpuHp - this.cpuOffencePoint;
        } else {
          System.out.println("Playerは" + this.cpuOffencePoint + "ポイントのダメージ！");
          this.playerHp = this.playerHp - this.cpuOffencePoint;
        }
      }
      if (this.playerHp > 0) {
        System.out.println("Playerの攻撃！");
        // カード特殊効果の処理
        if (this.playerAttackPoint >= 3) {
          System.out.println("PlayerのAttack Card3枚による貫通攻撃!");
          this.playerSpecialEffect = "piercing";
        } else if (this.playerAttackPoint >= 2) {
          System.out.println("PlayerのAttack Card2枚による攻撃力倍加！");
          this.playerOffencePoint = this.playerOffencePoint * 2;
        }
        if (this.cpuDefencePoint >= 3) {
          System.out.println("CPUのDefence Card3枚によるカウンター！");
          this.cpuSpecialEffect = "counter";
        } else if (this.cpuDefencePoint >= 2) {
          System.out.println("CPUのDefence Card2枚による防御力倍加！");
          this.cpuGuardPoint = this.cpuGuardPoint * 2;
        }
        // CPUへのダメージ処理
        if (this.playerSpecialEffect.isEmpty() == true) {// specialEffectがない場合，playerOffencepointからcpuGuardPointを引いたものをcpuHpから引く
          if (this.cpuSpecialEffect.isEmpty() == true) {
            int cpuDamage = this.playerOffencePoint - this.cpuGuardPoint;
            if (cpuDamage > 0) {
              System.out.println("CPUは" + cpuDamage + "ポイントのダメージ！");
              this.cpuHp = this.cpuHp - cpuDamage;
            } else {
              System.out.println("CPUはダメージを受けなかった");
            }
          } else if (this.cpuSpecialEffect.contains("counter")) {// CPUがcounterモードの場合，playerOffencePoint分のダメージをplayerHP(php)が受ける
            System.out.println("CPUのカウンター！");
            System.out.println("Playerは" + this.playerOffencePoint + "ポイントのダメージ！");
            this.playerHp = this.playerHp - this.playerOffencePoint;
          }
        } else if (this.playerSpecialEffect.contains("piercing")) {// PlayerがpiercingモードでCPUがcounterモードでない場合，cpuのGuardPointを無視してcpuHpにダメージを与える
          System.out.println("Playerの防御無視攻撃！");
          if (this.cpuSpecialEffect.contains("counter")) {
            System.out.println("CPUのカウンター！");
            System.out.println("Playerは" + this.playerOffencePoint + "ポイントのダメージ！");
            this.playerHp = this.playerHp - this.playerOffencePoint;
          } else {
            System.out.println("CPUは" + this.playerOffencePoint + "ポイントのダメージ！");
            this.cpuHp = this.cpuHp - this.playerOffencePoint;
          }
        }
      }
    }
    System.out.println("PlayerのHPは" + this.playerHp + "ポイント");
    System.out.println("CPUのHPは" + this.cpuHp + "ポイント");
  }

  // PlayerとCPUのDeck(15枚)を作るメソッド
  public void createDeck() {
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
    System.out.println("Playerの恐竜召喚！");
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
    System.out.println("CPUの恐竜召喚！");
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
