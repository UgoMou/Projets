import java.awt.*;
import javax.swing.*;


public class MenuGauche extends JPanel{
  private static final long serialVersionUID = 1L;
  private Image imageHerbe = null;
  private Image imageArbre = null;
  private Image imageCase = null;
  private Image imageArgent = null;
  private Image imageSac = null;
  private Image imagePomme = null;
  private Image imagePills = null;
  private Image imageLivremagique= null;
  private Image imageepee= null;
  private Image imagemarchand= null;
  private Image imageGobelin= null;
  private Image imageCreature=null;
  private Image imageSonic= null;
  private Image imageYoda= null;
  private Image imageChevre= null;

  public MenuGauche () {
    setPreferredSize(new Dimension(300,0));
    imageHerbe = Images.getImage("Herbe");
    imageArbre = Images.getImage("Arbre");
    imageCase= Images.getImage("Case");
    imageArgent = Images.getImage("Tresor");
    imageSac = Images.getImage("Sac");
    imagePomme = Images.getImage("Pomme");
    imagePills = Images.getImage("Pills");
    imageLivremagique = Images.getImage("LivreMagique");
    imageepee = Images.getImage("Epee");
    imagemarchand = Images.getImage("Marchand");
    imageGobelin = Images.getImage("Gobelin");
    imageCreature = Images.getImage("Poule");
    imageSonic = Images.getImage("Sonic");
    imageYoda = Images.getImage("Yoda");
    imageChevre = Images.getImage("Chevredelamort");
  }


  public void paintComponent(Graphics g){
    Font font1 = new Font ("Trajan", Font.ITALIC, 10);
    Font font2 = new Font ("Trajan", Font.ITALIC, 8);
    g.setFont(font1);
    g.setColor(Color.WHITE);

    int taille = 50;
    int espace = 100;
    g.drawImage( imageHerbe, 0 ,0 , getWidth(), getHeight(), this) ;
    for ( int i = 0 ; i < 10 ; i++ )
      for ( int j = 0; j < 30 ; j++ )
        g.drawImage(imageArbre,i*30, j*30, 30, 30,this);

    for (int i = 0; i< 12; i++ ){
      g.drawImage(imageCase, 20 ,i*taille+espace , taille , taille , this) ;
      g.drawImage(imageCase, 80 ,i*taille+espace , 220 , taille , this) ;
      espace += 10;
      if (i==0){
        g.drawImage(imageArgent, 30  , i * taille + espace, 30, 30,this) ;
        g.drawString("Monnaie pour acheter des ", 110 , i * taille + espace + 10);
        g.drawString("accessoires au marchand", 110 , i * taille + espace + 25);
      }
      if (i==1){
        g.drawImage(imageSac, 28 , i*taille+espace , 30 , 30 ,this) ;
        g.drawString("Sac permettant de donner ", 110 , i * taille + espace + 10);
        g.drawString("plusieurs accessoires", 110 , i*taille+espace+25);
      }
      if (i==2){
        g.drawImage(imagePomme,  28 ,  i*taille+espace  , 33 ,30 ,this) ;
        g.drawString("Accessoire permettant de  ", 110 , i*taille+espace+10);
        g.drawString("devenir ami avec une créature", 110 , i*taille+espace+25);
      }
      if (i==3){
        g.drawImage(imagePills, 36 , i*taille+espace-2 , 15, 35,this) ;
        g.drawString("Accessoire permettant de  ", 110 , i*taille+espace+10);
        g.drawString("booster une créature", 110 , i*taille+espace+25);
      }
      if (i==4){
        g.drawImage(imageLivremagique, 25   , i*taille+espace-4 , 40, 40,this) ;
        g.drawString("Accessoire permettant de devenir ", 105 , i*taille+espace+10);
        g.drawString("ami avec une créature légendaire", 105 , i*taille+espace+25);
      }
      if (i==5){
          g.drawImage(imageepee,  25 , i*taille+espace-7 , 40, 40,this) ;
          g.drawString("Accessoire permettant de  tuer", 110 , i*taille+espace+10);
          g.drawString("le premier ami de l'adversaire", 110 , i*taille+espace+25);
        }
      if (i==6){
          g.drawImage(imagemarchand,  25 , i*taille+espace-7 , 40, 40,this) ;
          g.drawString("Marchand vendant divers et ", 110 , i*taille+espace+10);
          g.drawString("variés accessoires", 110 , i*taille+espace+25);
      }
      if (i==7){
          g.drawImage(imageGobelin,  25 , i*taille+espace-7 , 40, 40,this) ;
          g.drawString("Gobelin est un personnage caché  ", 110 , i*taille+espace+10);
          g.drawString("dans un arbre qui volent tous vos items", 105 , i*taille+espace+25);
      }
      if (i==8){
          g.drawImage(imageCreature,  25 , i*taille+espace-7 , 40, 40,this) ;
          g.drawString("Créature avec qui vous devez nouer  ", 105 , i*taille+espace+10);
          g.drawString("amitié (Elle n'a qu'un seul maitre)", 105 , i*taille+espace+25);
      }
      if (i==9){
          g.setFont(font2);
          g.drawImage(imageSonic,  25 , i*taille+espace-7 , 40, 40,this) ;
          g.drawString("Créature légendaire, caché dans un arbre, qui se", 95 , i*taille+espace+10);
          g.drawString("manifeste que quand vous possédez un LivreMagique", 83 , i*taille+espace+25);
      }
      if (i==10){
          g.setFont(font2);
          g.drawImage(imageYoda,  25 , i*taille+espace-7 , 40, 40,this) ;
          g.drawString("Créature légendaire, caché dans un arbre, qui se", 95 , i*taille+espace+10);
          g.drawString("manifeste que quand vous possédez un LivreMagique", 83 , i*taille+espace+25);
      }
      if (i==11){
          g.setFont(font2);
          g.drawImage(imageChevre,  25 , i*taille+espace-7 , 40, 40,this) ;
          g.drawString("Créature légendaire, caché dans un arbre", 90 , i*taille+espace+10);
          g.drawString("Si vous finissez avec la chevre, vous avez gagné", 90 , i*taille+espace+25);
      }
    }
  }
}
