import java.awt.*;

public class LivreMagique extends Acc{
  private Image image=null;

  public LivreMagique(){
    super("LivreMagique");
    image = Images.getImage("LivreMagique");
  }

  public double getPoids(){
    return 10;
  }

  public double getPrix(){
    return 100;
  }

  public void dessiner(Graphics g){
      int tc = Monde.tailleCase;
      g.drawImage(image,getX()*tc+1, getY()*tc+1, tc-2, tc-2, Monde.world);
  }
}
