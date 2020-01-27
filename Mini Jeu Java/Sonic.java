import java.awt.*;

public class Sonic extends Creature{

  public static final Sonic sonic = new Sonic();

  private Sonic (){
    super("Sonic");
  }

  public double getVitesse(){
    return 50;
  }

}
