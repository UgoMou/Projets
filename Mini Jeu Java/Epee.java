import java.awt.*;

public class Epee extends Acc{
  private Image image = Images.getImage("Epee");

  public Epee () {
    super("Epee");
  }

  public double getPoids(){
    return 15;
  }

  public double getPrix(){
    return 100;
  }

  public void dessiner(Graphics g){
      int tc = Monde.tailleCase;
      g.drawImage(image,getX()*tc+1, getY()*tc+1, tc-2, tc-2, Monde.world);
  }
}
