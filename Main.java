public class Main {

  public static void main(String[] args) {
    BattleDinosaurs bdgame = new BattleDinosaurs();
    bdgame.initLibrary();
    bdgame.createDeck();
    while (true) {
      bdgame.startPhase();
      if (bdgame.getPlayerHp() <= 0 && bdgame.getCpuHp() <= 0) {
        System.out.println("引き分け!");
      } else if (bdgame.getPlayerHp() <= 0) {
        System.out.println("CPUの勝ち！");
      } else if (bdgame.getCpuHp() <= 0) {
        System.out.println("Playerの勝ち！");
      } else {
        continue;
      }
      break;
    }
  }
}
