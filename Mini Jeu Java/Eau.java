import java.awt.*;

public class Eau extends Obstacle{
  private Image image = null;

  public Eau (){
    super("Eau");
    image = Images.getImage("Eau");
  }

  public Eau( int x , int y ){
    super( "Eau",x ,y);
  }

  public void changeImage(){
      image= Images.getImage("Eau_vide");
  }

  public void dessiner (Graphics g){
    int tc = Monde.tailleCase;
    g.drawImage(image,getX()*tc+1, getY()*tc+1, tc-2, tc-2, Monde.world);
  }
}
