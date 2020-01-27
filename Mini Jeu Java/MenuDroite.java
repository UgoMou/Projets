import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;


public class MenuDroite extends JPanel{
  private static final long serialVersionUID = 1L;
  private Image imageHerbe = null;
  private Image imageArbre = null;
  private Image imageCase = null;
  private Image imageArgent = null;
  private Image imageSac = null;
  private Image imagePomme = null;
  private Image imagePills = null;
  private Image imageCreature=null;
  private Image imageBande = null;
  private Image imagebanderouge = null;
  private Image imageLivremagique= null;
  private Image imageepee= null;
  private double money;
  private int sac;
  private int pomme;
  private int pillule;
  private int livremagique;
  private int epee;


  public MenuDroite () {
    setPreferredSize(new Dimension(660,0));
    money = 0;
    sac = 0;
    pomme = 0;
    pillule = 0;
    imageHerbe = Images.getImage("Herbe");
    imageArbre = Images.getImage("Arbre");
    imageBande = Images.getImage("Case");
    imagebanderouge = Images.getImage("Banderouge");
    imageCase= Images.getImage("Case");
    imageArgent = Images.getImage("Tresor");
    imageSac = Images.getImage("Sac");
    imagePomme = Images.getImage("Pomme");
    imagePills = Images.getImage("Pills");
    imageLivremagique = Images.getImage("LivreMagique");
    imageepee = Images.getImage("Epee");
  }

  private void compteDifferentAcc(Avatar avatar){
    money = avatar.getMoney();
    sac = 0;
    pomme = 0;
    pillule = 0 ;
    livremagique = 0;
    epee = 0;
    ArrayList<Acc> tab = avatar.getAcc();
    for (Acc acc : tab){
      if (acc instanceof Sac){
        sac++;
        compteDifferentAcc((Sac) acc);
        continue;
      }
      if (acc instanceof Pomme){
        pomme++;
        continue;
      }
      if(acc instanceof Pills){
        pillule++;
        continue;
      }
      if (acc instanceof LivreMagique){
        livremagique++;
        continue;
      }
      if (acc instanceof Epee){
        epee++;
        continue;
      }
    }
  }

  private void compteDifferentAcc(Sac soussac){
    for (Acc acc : soussac.getTab()){
      if (acc instanceof Sac){
        compteDifferentAcc((Sac) acc);
        sac++;
        continue;
      }
      if (acc instanceof Pomme){
        pomme++;
        continue;
      }
      if(acc instanceof Pills){
        pillule++;
        continue;
      }
      if (acc instanceof LivreMagique){
        livremagique++;
        continue;
      }
    }
  }

  private void dessinerCreature( Graphics g, Avatar joueur, int taille, int y){
    ArrayList<Creature> tab=joueur.getAmis();
    int i = 0 ;
    int espace=14 ;
    for ( Creature creature : tab ) {
      imageCreature = creature.getImage();
      g.drawImage(imageCreature,  taille * i + espace  , y , 30 , 30 ,this) ;
      g.drawString(creature.getNom(), taille * i + espace , y + 40);
      espace+=10;
      i++;
    }

  }

  public void paintComponent(Graphics g){
    Font font = new Font("Bookman Old Style", Font.ITALIC, 35);
    Font font1 = new Font ("Trajan", Font.ITALIC, 15);
    Font font2 = new Font ("Trajan", Font.ITALIC, 8);
    Font font3 = new Font ("Arial", Font.ITALIC,25);
    g.setFont(font);


    g.drawImage( imageHerbe, 0 ,0 , getWidth(), getHeight(), this) ;

    for ( int i = 0 ; i < 22 ; i++ )
      for ( int j = 0; j < 30 ; j++ )
        g.drawImage(imageArbre,i*30, j*30, 30, 30,this);

    g.drawImage(imagebanderouge,  30 ,20 , 570  ,80 , this) ;
    g.setColor(Color.WHITE);
    g.drawString("LA COURSE DES DIEUX", 114, 72);
    g.setFont(font3);

    g.drawImage(imagebanderouge,  400 , 100 , 200  ,80 , this) ;
    if (Interact.getState() != "fin")
      g.drawString("Tour : "+ (Jeu.getNb_tours()) +"/"+Jeu.getNb_tours_max() , 420, 150);
    else
      g.drawString("FIN" , 420, 150);

    Avatar[] players = Jeu.getPlayers();

    int taille =  50;
    int espace = 10;
    g.drawImage(imageCase,  10 ,330 , 45 ,45 , this) ;
    g.drawImage(players[0].getImage(),  10,330, 40 , 40 ,this) ;
    g.drawImage(imagebanderouge,  73 ,330 , 525  ,45 , this) ;
    g.drawString(players[0].getNom(),170,360);
;

    for (int i = 0; i< 10; i++ ){
      g.drawImage(imageCase,  i * taille + espace, 400, taille, taille, this) ;
      espace += 10;
    }
    g.drawImage(imageArgent,  13 , 404 , 30 , 30 ,this) ;
    g.drawImage(imageSac,  70, 405 , 30 , 30 ,this) ;
    g.drawImage(imagePomme,  130 , 405 , 33 ,30 ,this) ;
    g.drawImage(imagePills,  195, 404 , 15, 35,this) ;
    g.drawImage(imageLivremagique,  250 , 402 , 40, 40,this) ;
    g.drawImage(imageepee,  310 , 402 , 40, 40,this) ;

    compteDifferentAcc(players[0]);

    g.setFont(font1);
    g.drawString("x" + sac, 90 , 445);
    g.drawString("x" + pomme, 150 , 445);
    g.drawString("x" + pillule, 210 , 445);
    g.drawString("x" + livremagique, 270 , 445);
    g.drawString("x" + epee, 330 , 445);
    g.setFont(font2);
    g.drawString(String.format("%.2f", money) + "€", 25 , 442);


    espace = 10;
    for (int i = 0; i < 10; i++ ){
      g.drawImage(imageCase,  i*taille+espace,470, taille ,taille ,this) ;
      espace+=10;
    }
    g.setFont(font2);
    dessinerCreature(g, players[0], taille, 472 );

    g.setFont(font3);
    g.drawImage(imageCase,10 , 630 , taille ,taille ,this) ;
    g.drawImage(players[1].getImage(),  12 , 635 , 45 , 39,this) ;
    g.drawImage(imagebanderouge,  73 ,630 , 525  ,50 , this) ;
    g.drawString(players[1].getNom(),170, 662);



    espace =10;
    for (int i = 0; i< 10; i++ ){
      g.drawImage(imageCase,  i*taille+espace,700, taille , taille , this) ;
      espace+=10;
    }

    g.drawImage(imageArgent,  13 , 704 , 30 , 30 ,this) ;
    g.drawImage(imageSac,  70  ,705 , 30 , 30 ,this) ;
    g.drawImage(imagePomme,  130 , 705 , 33 ,30 ,this) ;
    g.drawImage(imagePills,  195 , 704 , 15, 35,this) ;
    g.drawImage(imageLivremagique,  250 , 702 , 40, 40,this) ;
    g.drawImage(imageepee,  310 , 702 , 40, 40,this) ;

    espace = 10;
    for (int i = 0; i< 10; i++ ){
      g.drawImage(imageCase,  i*taille+espace,770, taille,taille,this) ;
      espace+=10;
    }
    compteDifferentAcc(players[1]);
    g.setFont(font2);
    dessinerCreature(g, players[1], taille, 772);
    g.setFont(font1);
    g.drawString("x" + sac, 90 , 745);
    g.drawString("x" + pomme, 150 , 745);
    g.drawString("x" + pillule, 210 , 745);
    g.drawString("x" + livremagique, 270 , 745);
    g.drawString("x" + epee, 330 , 745);
    g.setFont(font2);
    g.drawString(String.format("%.2f",money)+"€", 25 , 742);
  }

}
