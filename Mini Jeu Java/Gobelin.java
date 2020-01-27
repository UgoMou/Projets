import java.awt.*;

public class Gobelin extends Personnage{
  public static final Image image = Images.getImage("Gobelin");
  public static final Gobelin gobelin = new Gobelin();

  private Gobelin(){
    super("Gobelin");
  }

  public Image getImage(){
    return image;
  }

  public void dessiner(Graphics g){
      int tc = Monde.tailleCase;
      g.drawImage(image,getX()*tc+1, getY()*tc+1, tc-2, tc-2, Monde.world);
  }

}
