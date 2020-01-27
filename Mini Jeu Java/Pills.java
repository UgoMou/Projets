import java.awt.*;

public class Pills extends Acc implements Mangeable {
    private double poids;
    private Image image=null;

    public Pills(){
        super("Pills");
        poids = Math.random();
        image = Images.getImage("Pills");
      }

    public double getPoids(){
        return poids;
    }

    public double getPrix(){
        return 10 * poids + 8;
    }

    public String toString(){
        return super.toString();
      }

    public void dessiner ( Graphics g){
      int tc = Monde.tailleCase;
      g.drawImage(image,getX()*tc+1, getY()*tc+1, tc-2, tc-2, Monde.world);
    }
}
