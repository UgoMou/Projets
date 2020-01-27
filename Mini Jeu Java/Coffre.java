import java.awt.*;
import java.util.ArrayList;

public class Coffre extends Item {
    private ArrayList<Item> contenu;
    private Image image=null;


    public Coffre(){
        super("Coffre");
        //state=false;
        contenu = new ArrayList<Item>();
        if (Math.random() < 0.8)
            ajouter(new Tresor());
        image = Images.getImage("Coffre_ferme");
    }

    public ArrayList<Item> ouvrir(){
        ArrayList<Item> tab = contenu;
        contenu = new ArrayList<Item>();
        image = Images.getImage("Coffre_ouvert");
        return tab;
    }

    public void ajouter(Item item){
        contenu.add(item);
    }

    public ArrayList<Item> getContenu(){
        return contenu;
    }

    public void dessiner(Graphics g){
        int tc = Monde.tailleCase;
        g.drawImage(image,getX()*tc+1, getY()*tc+1, tc-2, tc-2, Monde.world);
    }
}
