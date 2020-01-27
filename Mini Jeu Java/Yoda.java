import java.awt.*;

public class Yoda extends Creature{
  public static final Yoda yoda = new Yoda();

  private Yoda (){
    super("Yoda");
  }

  public double getVitesse(){
    return 30;
  }

  public String toString(Avatar avatar){
    return "Maitre Yoda ralentit de 50% les créatures de " + avatar.getNom() ;
  }


  public void PouvoirdeYoda (){
    Avatar[] players = Jeu.getPlayers();

    if (bff == players[0]){
      players[1].setCoef(0.5);
      Interact.talk(Yoda.yoda.toString(players[1]));
      //System.out.println( Yoda.yoda.toString(ennemi));
    }
    if (bff == players[1]){
      players[0].setCoef(0.5);
      Interact.talk(Yoda.yoda.toString(players[0]));
      //System.out.println( Yoda.yoda.toString(this));
    }
  }

}
