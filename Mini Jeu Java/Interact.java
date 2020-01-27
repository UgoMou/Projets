import java.util.ArrayList;
import java.awt.*;

public class Interact {
    private static String state;
    private static ArrayList<String> talk;
    private static int cursor;
    private static final Image imageTalk = Images.getImage("Dialogue");
    private static final Image imageCursor = Images.getImage("Cursor");

    public static String getState(){
        return state;
    }

    public static void init(){
        for (int i = 0; i < 2; i++){
            state = "init";
            Avatar curr = Jeu.getCurrPlay();
            curr.setNom("_");
            talk =  getTalk("Rentrer le nom du Joueur " + (i + 1) );
            Fenetre.getFenetre().repaint();
            while (state == "init") {
                try {
                    Thread.sleep(250);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            Jeu.nextPlayer();
        }
        state = "tour";
        talk = getTalk("Choisir le nombre de tour :\n\t- Z : +1\n\t- S : -1\n\t- Q : -5\n\t- D : +5");
        Fenetre.getFenetre().repaint();
        while (state =="tour"){
            try {
                    Thread.sleep(250);
            } catch (Exception e) {
                    System.out.println(e);
            }
        }
    }

    public static void choose () {
        state = "choose";
    }

    public static void stop () {
        state = "stop";
    }

    public static void play () {
        state = "play";
    }

    public static void end () {
      state = "end";
    }

    public static void talk (String str) {
        state = "talk";
        talk = getTalk(str);
        Fenetre.getFenetre().repaint();
        while (state == "talk") {
            try {
                Thread.sleep(250);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public static void shop (String str) {
        cursor = 0;
        state = "shop";
        talk = getTalk(str);
        Fenetre.getFenetre().repaint();
        while (state == "shop") {
            try {
                Thread.sleep(250);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public static void cursorUp () {
        cursor--;
    }

    public static void cursorDown () {
        cursor++;
    }

    public static void cursorMax () {
        cursor = getMaxCursor();
    }

    public static void cursorMin () {
        cursor = 0;
    }

    public static int getCursor () {
        return cursor;
    }

    public static int getMaxCursor () {
        int max = 0;
        for (String str : talk)
            if (str.contains("\t"))
                max++;
        return max - 1;
    }

    private static ArrayList<String> getTalk (String str) {
        String temp = "";
        String[] disc = str.split(" ");
        String[] split = new String[2];
        ArrayList<String> dialog = new ArrayList<String>();
        for (String s : disc) {
            if (s.contains("\n")) {
                split = s.split("\n");
                temp += split[0];
                dialog.add(temp);
                if (split.length > 1) {
                    temp = split[1] + " ";
                }
                continue;
            }
            if ( (temp + s).length() > 60 ) {
                dialog.add(temp);
                temp = "";
            }
            temp += s + " ";
        }
        dialog.add(temp);
        return dialog;
    }

    public static void dessinerTalk (Graphics g) {
        int k = 0;
        String space = "";
        int height = 200;
        int plus = 0;
        if (talk.size() > 5)
            plus = 30 * (talk.size() - 4);
        int size = Monde.taille * Monde.tailleCase;
        g.setFont(new Font ("Trajan", Font.BOLD, 22));
        g.setColor(new Color(95, 0, 0));
        //############# BOITE DE DIALOGUE #############
        g.drawImage(imageTalk, 18, size - height - plus + 3, size - 35 , height + plus - 20, Monde.world);
        g.setColor(Color.WHITE);
        //############# DIALOGUE #############
        for (int i = 0; i < talk.size(); i++){
            if (talk.get(i).contains("\t")){
                if (k == cursor && state == "shop")
                    g.drawImage(imageCursor, 55, size - height - (int) (0.85 * plus) + 37 + 25 * i, Monde.world);
                k++;
                space = "    ";
            }
            else
                space = "";
            g.drawString(space + talk.get(i), 55, size - height - (int) (0.85* plus) + 50 + 25 * i);
        }
    }
}
