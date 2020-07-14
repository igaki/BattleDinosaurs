import java.util.Random;
import java.util.Scanner;

public class BattleDinosaurs {
  String[] dn = new String[5];
  int[] atc = new int[5];
  int[] agc = new int[5];
  int[] dc = new int[5];
  String[] pd = new String[15];
  int[] drawnPlayerDeck = new int[15];
  String[] cd = new String[15];
  int[] drawnCpuDeck = new int[15];
  int[] pcs = new int[5];
  int[] ccs = new int[5];
  int playerAttackPoint, playerAgilityPoint, playerDefencePoint, playerOffencePoint, playerGuardPoint;
  int cpuAttackPoint, cpuAgilityPoint, cpuDefencePoint, cpuOffencePoint, cpuGuardPoint;
  int php = 100;
  int chp = 100;
  String ps = "";
  String cs = "";
  int drawnNum = 0;

  Random rnd = new Random();
  Scanner sc = new Scanner(System.in);

  public void startPhase() {
    // Game Status初期化
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
    for (int i = 0; i < pcs.length; i++) {
      int playerCard = rnd.nextInt(this.pd.length);
      if (this.drawnPlayerDeck[playerCard] == -1) {
        i--;
        continue;
      } else {
        this.drawnNum++;// Drawした枚数をカウントアップ
        this.pcs[i] = playerCard;
        this.drawnPlayerDeck[playerCard] = -1;
      }
    }
    for (int card : this.pcs) {
      System.out.println(this.pd[card]);
    }

    // カードの枚数がそれぞれAttack，Agility，Defenceのポイントになる
    // Attack,Quick,Defenceのカード枚数計測
    this.playerAttackPoint = 0;
    this.playerAgilityPoint = 0;
    this.playerDefencePoint = 0;
    for (int i = 0; i < this.pcs.length; i++) {
      if (this.pd[this.pcs[i]].contains("Attack")) {
        this.playerAttackPoint++;
      } else if (this.pd[this.pcs[i]].contains("Agility")) {
        this.playerAgilityPoint++;
      } else if (this.pd[this.pcs[i]].contains("Defence")) {
        this.playerDefencePoint++;
      }
    }
    System.out.println("Attack Card:" + this.playerAttackPoint);
    System.out.println("Agility Card:" + this.playerAgilityPoint);
    System.out.println("Defence Card:" + this.playerDefencePoint);

    System.out.println("CPUの恐竜カードDraw----------(Hit Enter!)");
    sc.nextLine();
    for (int i = 0; i < ccs.length; i++) {
      int cpuCard = rnd.nextInt(this.cd.length);
      if (this.drawnCpuDeck[cpuCard] == -1) {
        i--;
        continue;
      } else {
        this.ccs[i] = cpuCard;
        this.drawnCpuDeck[cpuCard] = -1;
      }
    }

    for (int card : this.ccs) {
      System.out.println(this.cd[card]);
    }

    // カードの枚数がそれぞれAttack，Agility，Defenceのポイントになる
    // Attack,Quick,Defenceのカード枚数計測
    this.cpuAttackPoint = 0;
    this.cpuAgilityPoint = 0;
    this.cpuDefencePoint = 0;
    for (int i = 0; i < this.ccs.length; i++) {
      if (this.cd[this.ccs[i]].contains("Attack")) {
        this.cpuAttackPoint++;
      } else if (this.cd[this.ccs[i]].contains("Agility")) {
        this.cpuAgilityPoint++;
      } else if (this.cd[this.ccs[i]].contains("Defence")) {
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
    if (this.cpuAgilityPoint != 0 && this.playerAgilityPoint != 0) {
      this.playerOffencePoint = 10 * this.playerAttackPoint * this.playerAgilityPoint / this.cpuAgilityPoint;
      this.cpuOffencePoint = 10 * this.cpuAttackPoint * this.cpuAgilityPoint / this.playerAgilityPoint;
    } else if (this.cpuAgilityPoint == 0 && this.playerAgilityPoint == 0) {
      this.playerOffencePoint = 10 * this.playerAttackPoint;
      this.cpuOffencePoint = 10 * this.cpuAttackPoint;// agilityが0のときは倍率が1倍になる
    } else if (this.cpuAgilityPoint == 0) {
      this.playerOffencePoint = 10 * this.playerAttackPoint * this.playerAgilityPoint;
      this.cpuOffencePoint = 10 * this.cpuAttackPoint;// agilityが0のときは倍率が1倍になる
    } else if (this.playerAgilityPoint == 0) {
      this.playerOffencePoint = 10 * this.playerAttackPoint;
      this.cpuOffencePoint = 10 * this.cpuAttackPoint * this.cpuAgilityPoint;
    }
    this.playerGuardPoint = 10 * this.playerDefencePoint;
    this.cpuGuardPoint = 10 * this.cpuDefencePoint;

    // Agilityが大きいほうが先攻．等しい場合はPlayerから
    if (this.playerAgilityPoint >= this.cpuAgilityPoint) {
      System.out.println("Playerの攻撃！");
      // カード特殊効果の処理
      this.ps = "";// 初期化
      this.cs = "";

      if (this.playerAttackPoint >= 3) {
        System.out.println("PlayerのAttack Card3枚による貫通攻撃!");
        this.ps = "piercing";
      } else if (this.playerAttackPoint >= 2) {
        System.out.println("PlayerのAttack Card2枚による攻撃力倍加！");
        this.playerOffencePoint = this.playerOffencePoint * 2;
      }
      if (this.cpuDefencePoint >= 3) {
        System.out.println("CPUのDefence Card3枚によるカウンター！");
        this.cs = "counter";
      } else if (this.cpuDefencePoint >= 2) {
        System.out.println("CPUのDefence Card2枚による防御力倍加！");
        this.cpuGuardPoint = this.cpuGuardPoint * 2;
      }
      // CPUへのダメージ処理
      if (this.ps.isEmpty() == true) {// specialEffectがない場合，playerOffencepointからcpuGuardPointを引いたものをcpuHpから引く
        if (this.cs.isEmpty() == true) {
          int cpuDamage = this.playerOffencePoint - this.cpuGuardPoint;
          if (cpuDamage > 0) {
            System.out.println("CPUは" + cpuDamage + "ポイントのダメージ！");
            this.chp = this.chp - cpuDamage;
          } else {
            System.out.println("CPUはダメージを受けなかった");
          }
        } else if (this.cs.contains("counter")) {// CPUがcounterモードの場合，playerOffencePoint分のダメージをplayerHP(php)が受ける
          System.out.println("CPUのカウンター！");
          System.out.println("Playerは" + this.playerOffencePoint + "ポイントのダメージ！");
          this.php = this.php - this.playerOffencePoint;
        }
      } else if (this.ps.contains("piercing")) {// PlayerがpiercingモードでCPUがcounterモードでない場合，cpuのGuardPointを無視してcpuHpにダメージを与える
        System.out.println("Playerの防御無視攻撃！");
        if (this.cs.contains("counter")) {
          System.out.println("CPUのカウンター！");
          System.out.println("Playerは" + this.playerOffencePoint + "ポイントのダメージ！");
          this.php = this.php - this.playerOffencePoint;
        } else {
          System.out.println("CPUは" + this.playerOffencePoint + "ポイントのダメージ！");
          this.chp = this.chp - this.playerOffencePoint;
        }
      }
      if (this.chp > 0) {
        System.out.println("CPUの攻撃！");
        // カード特殊効果の処理
        this.ps = "";// 初期化
        this.cs = "";
        if (this.cpuAttackPoint >= 3) {
          System.out.println("CPUのAttack Card3枚による貫通攻撃!");
          this.cs = "piercing";
        } else if (this.cpuAttackPoint >= 2) {
          System.out.println("CPUのAttack Card2枚による攻撃力倍加！");
          this.cpuOffencePoint = this.cpuOffencePoint * 2;
        }

        if (this.playerDefencePoint >= 3) {
          System.out.println("PlayerのDefence Card3枚によるカウンター！");
          this.ps = "counter";
        } else if (this.playerDefencePoint >= 2) {
          System.out.println("PlayerのDefence Card2枚による防御力倍加！");
          this.playerGuardPoint = this.playerGuardPoint * 2;
        }
        if (this.cs.isEmpty() == true) {// specialEffectがない場合，cpuOffencepointからplayerGuardPointを引いたものをplayerHpから引く
          if (this.ps.isEmpty() == true) {
            int playerDamage = this.cpuOffencePoint - this.playerGuardPoint;
            if (playerDamage >= 0) {
              System.out.println("Playerは" + playerDamage + "ポイントのダメージ！");
              this.php = this.php - playerDamage;
            } else {
              System.out.println("Playerはダメージを受けなかった");
            }
          } else if (this.ps.contains("counter")) {// counterモードの場合，OffencePoint分のダメージを攻撃側が受ける
            System.out.println("Playerのカウンター！");
            System.out.println("CPUは" + this.cpuOffencePoint + "ポイントのダメージ！");
            this.chp = this.chp - this.cpuOffencePoint;
          }
        } else if (this.cs.contains("piercing")) {// PlayerがpiercingモードでCPUがcounterモードでない場合，playerのGuardPointを無視してplayerHpにダメージを与える
          System.out.println("CPUの防御無視攻撃！");
          if (this.ps.contains("counter")) {
            System.out.println("Playerのカウンター！");
            System.out.println("CPUは" + this.cpuOffencePoint + "ポイントのダメージ！");
            this.chp = this.chp - this.cpuOffencePoint;
          } else {
            System.out.println("Playerは" + this.cpuOffencePoint + "ポイントのダメージ！");
            this.php = this.php - this.cpuOffencePoint;
          }
        }
      }
    } else {
      System.out.println("CPUの攻撃！");
      // カード特殊効果の処理
      this.ps = "";// 初期化
      this.cs = "";

      if (this.cpuAttackPoint >= 3) {
        System.out.println("CPUのAttack Card3枚による貫通攻撃!");
        this.cs = "piercing";
      } else if (this.cpuAttackPoint >= 2) {
        System.out.println("CPUのAttack Card2枚による攻撃力倍加！");
        this.cpuOffencePoint = this.cpuOffencePoint * 2;
      }

      if (this.playerDefencePoint >= 3) {
        System.out.println("PlayerのDefence Card3枚によるカウンター！");
        this.ps = "counter";
      } else if (this.playerDefencePoint >= 2) {
        System.out.println("PlayerのDefence Card2枚による防御力倍加！");
        this.playerGuardPoint = this.playerGuardPoint * 2;
      }
      if (this.cs.isEmpty() == true) {// specialEffectがない場合，cpuOffencepointからplayerGuardPointを引いたものをplayerHpから引く
        if (this.ps.isEmpty() == true) {
          int playerDamage = this.cpuOffencePoint - this.playerGuardPoint;
          if (playerDamage >= 0) {
            System.out.println("Playerは" + playerDamage + "ポイントのダメージ！");
            this.php = this.php - playerDamage;
          } else {
            System.out.println("Playerはダメージを受けなかった");
          }
        } else if (this.ps.contains("counter")) {// CPUがcounterモードの場合，cpuOffencePoint分のダメージをcpuHP(php)が受ける
          System.out.println("Playerのカウンター！");
          System.out.println("CPUは" + this.cpuOffencePoint + "ポイントのダメージ！");
          this.chp = this.chp - this.cpuOffencePoint;
        }
      } else if (this.cs.contains("piercing")) {// PlayerがpiercingモードでCPUがcounterモードでない場合，playerのGuardPointを無視してplayerHpにダメージを与える
        System.out.println("CPUの防御無視攻撃！");
        if (this.ps.contains("counter")) {
          System.out.println("Playerのカウンター！");
          System.out.println("CPUは" + this.cpuOffencePoint + "ポイントのダメージ！");
          this.chp = this.chp - this.cpuOffencePoint;
        } else {
          System.out.println("Playerは" + this.cpuOffencePoint + "ポイントのダメージ！");
          this.php = this.php - this.cpuOffencePoint;
        }
      }
      if (this.php > 0) {
        System.out.println("Playerの攻撃！");
        // カード特殊効果の処理
        this.ps = "";// 初期化
        this.cs = "";
        if (this.playerAttackPoint >= 3) {
          System.out.println("PlayerのAttack Card3枚による貫通攻撃!");
          this.ps = "piercing";
        } else if (this.playerAttackPoint >= 2) {
          System.out.println("PlayerのAttack Card2枚による攻撃力倍加！");
          this.playerOffencePoint = this.playerOffencePoint * 2;
        }
        if (this.cpuDefencePoint >= 3) {
          System.out.println("CPUのDefence Card3枚によるカウンター！");
          this.cs = "counter";
        } else if (this.cpuDefencePoint >= 2) {
          System.out.println("CPUのDefence Card2枚による防御力倍加！");
          this.cpuGuardPoint = this.cpuGuardPoint * 2;
        }
        // CPUへのダメージ処理
        if (this.ps.isEmpty() == true) {// specialEffectがない場合，playerOffencepointからcpuGuardPointを引いたものをcpuHpから引く
          if (this.cs.isEmpty() == true) {
            int cpuDamage = this.playerOffencePoint - this.cpuGuardPoint;
            if (cpuDamage > 0) {
              System.out.println("CPUは" + cpuDamage + "ポイントのダメージ！");
              this.chp = this.chp - cpuDamage;
            } else {
              System.out.println("CPUはダメージを受けなかった");
            }
          } else if (this.cs.contains("counter")) {// CPUがcounterモードの場合，playerOffencePoint分のダメージをplayerHP(php)が受ける
            System.out.println("CPUのカウンター！");
            System.out.println("Playerは" + this.playerOffencePoint + "ポイントのダメージ！");
            this.php = this.php - this.playerOffencePoint;
          }
        } else if (this.ps.contains("piercing")) {// PlayerがpiercingモードでCPUがcounterモードでない場合，cpuのGuardPointを無視してcpuHpにダメージを与える
          System.out.println("Playerの防御無視攻撃！");
          if (this.cs.contains("counter")) {
            System.out.println("CPUのカウンター！");
            System.out.println("Playerは" + this.playerOffencePoint + "ポイントのダメージ！");
            this.php = this.php - this.playerOffencePoint;
          } else {
            System.out.println("CPUは" + this.playerOffencePoint + "ポイントのダメージ！");
            this.chp = this.chp - this.playerOffencePoint;
          }
        }
      }
    }
    System.out.println("PlayerのHPは" + this.php + "ポイント");
    System.out.println("CPUのHPは" + this.chp + "ポイント");
    System.out.println("------------Phase End--------------------------");
  }

  // PlayerとCPUのDeck(15枚)を作るメソッド
  public void createDeck() {
    int pcNum = 0;
    int dino1 = rnd.nextInt(this.dn.length);
    for (int i = 0; i < atc[dino1]; i++, pcNum++) {
      this.pd[pcNum] = "Attack:" + dn[dino1];
    }
    for (int i = 0; i < agc[dino1]; i++, pcNum++) {
      this.pd[pcNum] = "Agility:" + dn[dino1];
    }
    for (int i = 0; i < dc[dino1]; i++, pcNum++) {
      this.pd[pcNum] = "Defence:" + dn[dino1];
    }

    int dino2 = rnd.nextInt(this.dn.length);
    for (int i = 0; i < atc[dino2]; i++, pcNum++) {
      this.pd[pcNum] = "Attack:" + dn[dino2];
    }
    for (int i = 0; i < agc[dino2]; i++, pcNum++) {
      this.pd[pcNum] = "Agility:" + dn[dino2];
    }
    for (int i = 0; i < dc[dino2]; i++, pcNum++) {
      this.pd[pcNum] = "Defence:" + dn[dino2];
    }

    int dino3 = rnd.nextInt(this.dn.length);
    for (int i = 0; i < atc[dino3]; i++, pcNum++) {
      this.pd[pcNum] = "Attack:" + dn[dino3];
    }
    for (int i = 0; i < agc[dino3]; i++, pcNum++) {
      this.pd[pcNum] = "Agility:" + dn[dino3];
    }
    for (int i = 0; i < dc[dino3]; i++, pcNum++) {
      this.pd[pcNum] = "Defence:" + dn[dino3];
    }
    System.out.println("Playerの恐竜召喚！");
    System.out.println(this.dn[dino1]);
    System.out.println(this.dn[dino2]);
    System.out.println(this.dn[dino3]);

    int cpuNum = 0;
    dino1 = rnd.nextInt(this.dn.length);
    for (int i = 0; i < atc[dino1]; i++, cpuNum++) {
      this.cd[cpuNum] = "Attack:" + dn[dino1];
    }
    for (int i = 0; i < agc[dino1]; i++, cpuNum++) {
      this.cd[cpuNum] = "Agility:" + dn[dino1];
    }
    for (int i = 0; i < dc[dino1]; i++, cpuNum++) {
      this.cd[cpuNum] = "Defence:" + dn[dino1];
    }

    dino2 = rnd.nextInt(this.dn.length);
    for (int i = 0; i < atc[dino2]; i++, cpuNum++) {
      this.cd[cpuNum] = "Attack:" + dn[dino2];
    }
    for (int i = 0; i < agc[dino2]; i++, cpuNum++) {
      this.cd[cpuNum] = "Agility:" + dn[dino2];
    }
    for (int i = 0; i < dc[dino2]; i++, cpuNum++) {
      this.cd[cpuNum] = "Defence:" + dn[dino2];
    }

    dino3 = rnd.nextInt(this.dn.length);
    for (int i = 0; i < atc[dino3]; i++, cpuNum++) {
      this.cd[cpuNum] = "Attack:" + dn[dino3];
    }
    for (int i = 0; i < agc[dino3]; i++, cpuNum++) {
      this.cd[cpuNum] = "Agility:" + dn[dino3];
    }
    for (int i = 0; i < dc[dino3]; i++, cpuNum++) {
      this.cd[cpuNum] = "Defence:" + dn[dino3];
    }
    System.out.println("CPUの恐竜召喚！");
    System.out.println(this.dn[dino1]);
    System.out.println(this.dn[dino2]);
    System.out.println(this.dn[dino3]);

  }

  public void initLibrary() {
    this.dn[0] = "ティラノ";
    this.atc[0] = 3;
    this.agc[0] = 1;
    this.dc[0] = 1;

    this.dn[1] = "ヴェロキラプトル";
    this.atc[1] = 1;
    this.agc[1] = 3;
    this.dc[1] = 1;

    this.dn[2] = "トリケラトプス";
    this.atc[2] = 1;
    this.agc[2] = 1;
    this.dc[2] = 3;

    this.dn[3] = "プテラノドン";
    this.atc[3] = 2;
    this.agc[3] = 2;
    this.dc[3] = 1;

    this.dn[4] = "ステゴサウルス";
    this.atc[4] = 2;
    this.agc[4] = 1;
    this.dc[4] = 2;

  }

  public String[] getDinoName() {
    return dn;
  }

  public void setDinoName(String[] dinoName) {
    this.dn = dinoName;
  }

  public int[] getAttackCard() {
    return atc;
  }

  public void setAttackCard(int[] attackCard) {
    this.atc = attackCard;
  }

  public int[] getAgilityCard() {
    return agc;
  }

  public void setAgilityCard(int[] agilityCard) {
    this.agc = agilityCard;
  }

  public int[] getDefenceCard() {
    return dc;
  }

  public void setDefenceCard(int[] defenceCard) {
    this.dc = defenceCard;
  }

  public String[] getPlayerDeck() {
    return pd;
  }

  public void setPlayerDeck(String[] playerDeck) {
    this.pd = playerDeck;
  }

  public int[] getDrawnPlayerDeck() {
    return drawnPlayerDeck;
  }

  public void setDrawnPlayerDeck(int[] drawnPlayerDeck) {
    this.drawnPlayerDeck = drawnPlayerDeck;
  }

  public String[] getCpuDeck() {
    return cd;
  }

  public void setCpuDeck(String[] cpuDeck) {
    this.cd = cpuDeck;
  }

  public int[] getDrawnCpuDeck() {
    return drawnCpuDeck;
  }

  public void setDrawnCpuDeck(int[] drawnCpuDeck) {
    this.drawnCpuDeck = drawnCpuDeck;
  }

  public int[] getPlayerCards() {
    return pcs;
  }

  public void setPlayerCards(int[] playerCards) {
    this.pcs = playerCards;
  }

  public int[] getCpuCards() {
    return ccs;
  }

  public void setCpuCards(int[] cpuCards) {
    this.ccs = cpuCards;
  }

  public int getPlayerAttackPoint() {
    return playerAttackPoint;
  }

  public void setPlayerAttackPoint(int playerAttackPoint) {
    this.playerAttackPoint = playerAttackPoint;
  }

  public int getPlayerAgilityPoint() {
    return playerAgilityPoint;
  }

  public void setPlayerAgilityPoint(int playerAgilityPoint) {
    this.playerAgilityPoint = playerAgilityPoint;
  }

  public int getPlayerDefencePoint() {
    return playerDefencePoint;
  }

  public void setPlayerDefencePoint(int playerDefencePoint) {
    this.playerDefencePoint = playerDefencePoint;
  }

  public int getPlayerOffencePoint() {
    return playerOffencePoint;
  }

  public void setPlayerOffencePoint(int playerOffencePoint) {
    this.playerOffencePoint = playerOffencePoint;
  }

  public int getPlayerGuardPoint() {
    return playerGuardPoint;
  }

  public void setPlayerGuardPoint(int playerGuardPoint) {
    this.playerGuardPoint = playerGuardPoint;
  }

  public int getCpuAttackPoint() {
    return cpuAttackPoint;
  }

  public void setCpuAttackPoint(int cpuAttackPoint) {
    this.cpuAttackPoint = cpuAttackPoint;
  }

  public int getCpuAgilityPoint() {
    return cpuAgilityPoint;
  }

  public void setCpuAgilityPoint(int cpuAgilityPoint) {
    this.cpuAgilityPoint = cpuAgilityPoint;
  }

  public int getCpuDefencePoint() {
    return cpuDefencePoint;
  }

  public void setCpuDefencePoint(int cpuDefencePoint) {
    this.cpuDefencePoint = cpuDefencePoint;
  }

  public int getCpuOffencePoint() {
    return cpuOffencePoint;
  }

  public void setCpuOffencePoint(int cpuOffencePoint) {
    this.cpuOffencePoint = cpuOffencePoint;
  }

  public int getCpuGuardPoint() {
    return cpuGuardPoint;
  }

  public void setCpuGuardPoint(int cpuGuardPoint) {
    this.cpuGuardPoint = cpuGuardPoint;
  }

  public int getPlayerHp() {
    return php;
  }

  public void setPlayerHp(int playerHp) {
    this.php = playerHp;
  }

  public int getCpuHp() {
    return chp;
  }

  public void setCpuHp(int cpuHp) {
    this.chp = cpuHp;
  }

  public String getPlayerSpecialEffect() {
    return ps;
  }

  public void setPlayerSpecialEffect(String playerSpecialEffect) {
    this.ps = playerSpecialEffect;
  }

  public String getCpuSpecialEffect() {
    return cs;
  }

  public void setCpuSpecialEffect(String cpuSpecialEffect) {
    this.cs = cpuSpecialEffect;
  }

  public int getDrawnNum() {
    return drawnNum;
  }

  public void setDrawnNum(int drawnNum) {
    this.drawnNum = drawnNum;
  }

  public Random getRnd() {
    return rnd;
  }

  public void setRnd(Random rnd) {
    this.rnd = rnd;
  }

  public Scanner getSc() {
    return sc;
  }

  public void setSc(Scanner sc) {
    this.sc = sc;
  }
}
