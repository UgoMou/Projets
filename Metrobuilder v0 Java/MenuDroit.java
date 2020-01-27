import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * Classe gérant le MenuDroit interractif
 * @author Ugo
 */
public class MenuDroit extends JPanel {
    private static final long serialVersionUID = 1L;
    private static String state = "stop";
    private static String item = "none";
    private JLabel selectL = new JLabel("None");
    private JLabel selectS = new JLabel("None");
/**
 * Constructeur de la classe MenuDroit
 * @param height hauteur du MenuDroit
 */
    public MenuDroit (int height) {
        if (height < 500)
            height = 500;
        setPreferredSize(new Dimension(400, height));
        setLayout(null);
        /*
        addButton("Undo", (evt) -> {
            Map.map.undo();
        }, 10, 10, 90);
        addButton("Redo", (evt) -> {
            Map.map.redo();
        }, 105, 10, 90);
        */
        addButton("Draw Axe", (evt) -> {
            Map.map.drawAxe();
        }, 10, 10, 120);
        addButton("Erase Axe", (evt) -> {
            Map.map.eraseAxe();
        }, 140, 10, 120);
        addButton("Erase White", (evt) -> {
            Map.map.erase();
        }, 270, 10, 120);

        addLabel("Clic on the map to place the Station", 100, 45, 200);
        addButton("New Station", (evt) -> {
            state = "new";
            item = "station";
        }, 75, 70, 120);
        
        addButton("Delete Station", (evt) -> {
            state = "delete";
            item = "station";
        }, 205, 70, 120);

        addLabel("Clic on the station to increase/decrease its degree", 50, 105, 300);
        addButton("Up Degree", (evt) -> {
            state = "up";
            item = "degree";
        }, 75, 130, 120);
        addButton("Down Degree", (evt) -> {
            state = "down";
            item = "degree";
        }, 205, 130, 120);

        addLabel("Clic on both ends of the line", 100, 165, 200);
        addButton("New Ligne", (evt) -> {
            state = "new";
            item = "ligne";
            Map.resetSelectedStations();
        }, 10, 190, 120);
        addButton("Delete Ligne", (evt) -> {
            Map.map.deleteLigne();
        }, 140, 190, 120);
        addButton("Select Line", (evt) -> {
            state = "select";
            item = "ligne";
        }, 270, 190, 120);

        addLabel("Select a line then choose which split to apply", 50, 225, 300);
        addButton("Split Straight", (evt) -> {
            state = "straight";
            Map.map.split();
        }, 75, 250, 120);
        addButton("Split Around", (evt) -> {
            state = "around";
            Map.map.split();
        }, 205, 250, 120);
        
        addLabel("Select a line then a station", 110, 285, 180);
        addButton("Add Station", (evt) -> {
            state = "add";
            item = "ligne";
        }, 50, 310, 140);
        addButton("Remove Station", (evt) -> {
            state = "remove";
            item = "ligne";
        }, 200, 310, 140);
        // addButton("Inscire Cercle", (evt) -> {
        //     String n = JOptionPane.showInputDialog("Nombre de cercle à inscrire");
        //     if (Map.isInt(n))
        //         Map.getCity().inscrireCercle(Integer.parseInt(n));
        // }, 125, 490, 150);
        addButton("Save img", (evt) -> {
            String name = JOptionPane.showInputDialog("Nom du fichier");
            Map.saveF(name);
        }, 150, 450, 100);

        Font font = new Font(Font.DIALOG, 0, 20);
        addLabel("Selected Station : ", 80, 350, 120);
        selectS.setBounds(200, 350, 200, 30);
        selectS.setOpaque(true);
        selectS.setFont( font );
        add(selectS);
        addLabel("Selected Line : ", 97, 400, 100);
        selectL.setBounds(200, 400, 200, 30);
        selectL.setOpaque(true);
        selectL.setFont( font );
        add(selectL);
    }
/**
 * Crée un nouveau bouton
 * @param text String afficher dans bouton
 * @param listener ActionListener contenant l'action du bouton
 * @param x coordonnée x du bouton
 * @param y coordonnée y du bouton
 * @param w longueur du bouton
 */
    private void addButton(String text, ActionListener listener, int x, int y, int w){
        JButton jb = new JButton( new AbstractAction (){
			private static final long serialVersionUID = 1L;

			@Override
            public void actionPerformed(ActionEvent e) {
                listener.actionPerformed(e); // Récupère l'action "stockée" dans 'listener'
            }
        });
        jb.setText(text); // Définit le texte du bouton
        jb.setBounds(x, y, w, 30); // Définit les proportions du bouton et son emplacement
        add(jb); // Ajoute le bouton à la fenêtre
    }
/**
 * Crée une nouvelle étiquette
 * @param text String à afficher
 * @param x coordonnée x de l'étiquette
 * @param y coordonnée y de l'étiquette
 * @param w longueur de l'étiquette
 */
    private void addLabel(String text, int x, int y, int w) {
        JLabel jl = new JLabel(text);
        jl.setBounds(x, y, w, 30);
        add(jl);
    }
/**
 * Affiche la fenêtre
 */
    public void paintComponent(Graphics g) {
        Station selectedStation = Map.getCurrStation(); // Récupère la Station sélectionnée actuellement
        Ligne selectedLigne = Map.getCurrLigne(); // Récupère la Ligne sélectionnée actuellement
        String name = selectedStation == null ? "None" : selectedStation.getName(); // Vérifie qu'une Sation a été récupérée 
        selectS.setText( name ); // Affiche le nom de la Station
        name = selectedLigne == null ? "None" : selectedLigne.getName(); // Vérifie qu'une Ligne a été récupérée 
        selectL.setText( name ); // Affiche le nom de la Ligne
    }
/**
 * Retourne sur quel classe d'objet le MouseListener doit agir
 * @return String représentant la classe d'objet
 */
    public static String getState(){
        return state;
    }
/**
 * Retourne quel action MouseListener doit réaliser
 * @return String représentant l'action
 */
    public static String getItem(){
        return item;
    }
}