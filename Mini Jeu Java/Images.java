import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.ArrayList;

public class Images {
  public final static String[] tab = {"Avatar1" , "Avatar2", "Arbre", "Tresor", "Sac", "Pomme","Pills", "Herbe", "Coffre_ouvert", "Coffre_ferme", "Marchand",
    "Chevredelamort", "Bird", "Cheval", "Lion", "Baleine", "Pingouin", "Poule", "Lapin", "Rat", "Tigre", "Singe", "Dialogue", "Cursor", "Case", "Gobelin", "Banderouge",
  "Redcursor", "LivreMagique", "Epee", "Sonic", "Eau", "Eau_vide", "Yoda", "Fin", "Coupewinner", "Coupeperdant"};
  private static ArrayList<Image> tabimage;
  private final static Images pic = new Images();

  private Images(){
  tabimage = new ArrayList<Image>();
  try {
    tabimage.add( ImageIO.read(new File("./Image/link_retouche.png")) );
    tabimage.add( ImageIO.read(new File("./Image/mario_retouche.png")) );
    tabimage.add( ImageIO.read(new File("./Image/arbreblanc_retouche.png")) );
    tabimage.add( ImageIO.read(new File("./Image/argent_retouche.png")) );
    tabimage.add( ImageIO.read(new File("./Image/bag.png")) );
    tabimage.add( ImageIO.read(new File("./Image/pomme_retouche.png")) );
    tabimage.add( ImageIO.read(new File("./Image/pillule_retouche.png")) );
    tabimage.add( ImageIO.read(new File("./Image/herbe.png")) );
    tabimage.add( ImageIO.read(new File("./Image/coffre_ouvert_retouche.png")) );
    tabimage.add( ImageIO.read(new File("./Image/coffre_ferme_retouche.png")) );
    tabimage.add( ImageIO.read(new File("./Image/marchand.png")) );
    tabimage.add( ImageIO.read(new File("./Image/Chevre.png")) );
    tabimage.add( ImageIO.read(new File("./Image/Bird.png")) );
    tabimage.add( ImageIO.read(new File("./Image/Cheval.png")) );
    tabimage.add( ImageIO.read(new File("./Image/Lion.png")) );
    tabimage.add( ImageIO.read(new File("./Image/Baleine.png")) );
    tabimage.add( ImageIO.read(new File("./Image/Pingouin.png")) );
    tabimage.add( ImageIO.read(new File("./Image/Poule.png")) );
    tabimage.add( ImageIO.read(new File("./Image/Lapin.png")) );
    tabimage.add( ImageIO.read(new File("./Image/Rat.png")) );
    tabimage.add( ImageIO.read(new File("./Image/Tigre.png")) );
    tabimage.add( ImageIO.read(new File("./Image/Singe.png")) );
    tabimage.add( ImageIO.read(new File("./Image/dialogue.jpg")));
    tabimage.add( ImageIO.read(new File("./Image/cursor.png")));
    tabimage.add( ImageIO.read(new File("./Image/case.jpg")));
    tabimage.add( ImageIO.read(new File("./Image/gobelin.png")));
    tabimage.add( ImageIO.read(new File("./Image/blood_red_bar.png")));
    tabimage.add( ImageIO.read(new File("./Image/redcursor.png")));
    tabimage.add( ImageIO.read(new File("./Image/livremagique.png")));
    tabimage.add( ImageIO.read(new File("./Image/epee.png")));
    tabimage.add( ImageIO.read(new File("./Image/sonic.png")));
    tabimage.add( ImageIO.read(new File("./Image/eau.png")));
    tabimage.add( ImageIO.read(new File("./Image/eau_vide.png")));
    tabimage.add( ImageIO.read(new File("./Image/yoda.png")));
    tabimage.add( ImageIO.read(new File("./Image/fin.png")));
    tabimage.add( ImageIO.read(new File("./Image/coupegagnant.png")));
    tabimage.add( ImageIO.read(new File("./Image/coupeperdant.png")));
  } catch(IOException exc) {
      System.out.println("Il manque une image");
  }
}

  public static Image getImage(String image){
    for ( int i = 0 ; i < tab.length ; i++)
      if (tab[i] == image)
        return tabimage.get(i);
    return null;
  }
}
