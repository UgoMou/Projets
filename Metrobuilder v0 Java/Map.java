import java.util.ArrayList;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;

import javax.swing.*;
import javax.swing.event.MouseInputListener;

/**
 * Map est la classe grapgique du MetroBuilder
 * Elle gère aussi les interractions de la souris par la sous-classe Mypanel
 * @author Ugo
 * @version 2.0
 */

public final class Map {

  public static final JFrame frame = new JFrame("Metrobuilder");
  public static final int bkgrd = Color.gray.getRGB();
  public static final int constr = Color.white.getRGB();
  public static final int trait = Color.blue.getRGB();
  public static final int nod = Color.green.getRGB();
  public static final int sec = Color.orange.getRGB();
  public static final int pri = Color.red.getRGB();
  public static final int contour = Color.red.getRGB();
  private static Ville city;
  private static BufferedImage img;
  private static int size;
  private boolean axe;
  // private int cursor = 0;
  // private  ArrayList<Object> done = new ArrayList<Object>(); // Historique des actions
  // private ArrayList<BufferedImage> histo = new ArrayList<BufferedImage>(); // Historique des images
  private int center;
  private static Station previousStation = null;
  private static Station selectedStation = null;
  private static Ligne selectedLigne = null;
  private MyPanel surf;
  public static final Map map = new Map(); // Singleton

/**
 * Constructeur de la classe Map
 */
  private Map() {
    newMap(); // Définition des dimension de la fenêtre/ville et des paramètres de la ville
    System.out.println(city);
    center = (size / 2) - 1;
    surf = new MyPanel(); // Création de la fenêtre graphique
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());
    frame.add(surf, BorderLayout.CENTER); // Ajout de la fenêtre graphique au centre de la fenêtre principale
    frame.add(new MenuDroit(size), BorderLayout.EAST); // Ajout du MenuDroit à droite la fenêtre principale
    frame.pack();
    frame.setFocusable(true);
    frame.setVisible(true);
    frame.setLocationRelativeTo(null);
    img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
    axe = false;
    // histo.add( new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB) ); // Ajout de la première image
    // done.add("START"); // Ajout d'une action "tampon"
    cover();
  }
/**
 * Demande à l'utilisateur la taille de la fenêtre/ville, la population et le budget
 */
  private void newMap(){
    int[] values = { -1, -1, -1 };// {diameter, population, budget}
    JTextField[] field = { new JTextField("1000", 5), new JTextField("0", 5), new JTextField("0", 5) }; // Boîtes de dialogue
    String[] label = { "Diameter : ", "Population : ", "Budget : " }; // Etiquettes des boîtes de dialogue
    String temp;
    
    while (values[0] == -1 && values[1] == -1 && values[2] == -1) {
      JPanel myPanel = new JPanel(); // Fenêtre contenant les boîtes de dialogue
      // Ajoute des éléments de la fenêtre
      for (int i = 0; i < 3; i++) {
        myPanel.add(new JLabel(label[i]));
        if (values[i] == -1)
          myPanel.add(field[i]);
        else
          myPanel.add(new JLabel("" + values[i]));
        myPanel.add(Box.createHorizontalStrut(15));
      }
      // Test permettant l'apparition de la fenêtre
      if ( JOptionPane.showConfirmDialog(null, myPanel, "Please enter Diameter, Population & Budget values for your city", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
        for (int i = 0; i < 3; i++) { // Traitement des valeurs des boîtes de dialogue
          temp = field[i].getText();
          if ( values[i] == -1 && isInt(temp) ) { // Vérification que les valeurs sont bien des entiers
            values[i] = Integer.parseInt(temp);
          }
        }
    }

    size = values[0];
    city = new Ville(values[0], values[1], values[2]);
  }
/**
 * Vérifie si le String correspond à un entier ou non
 * @param str String à comparer
 * @return true si 'str' est un int sinon false
 */
  public static boolean isInt(String str) {
    if (str.length() == 0)
      return false;
    for (char c : str.toCharArray())// Test 1 par 1 si chaqu'un des charactères de 'str' est un chiffre 
      if (! "0123456789".contains("" + c) )
        return false;
    return true;
  }
/**
 * Renvoie la taille de la ville/fenêtre
 * @return taille de la ville/fenêtre
 */
  public static int getSize() {
    return size;
  }
/**
 * Renvoie la Station actuellement sélectionnée
 * @return Station sélectionnée
 */
  public static Station getCurrStation() {
    return selectedStation;
  }
/**
 * Renvoie la Ligne actuellement sélectionnée
 * @return Ligne sélectionnée
 */
  public static Ligne getCurrLigne() {
    return selectedLigne;
  }
/**
 * Renvoie la ville
 *@return Ville de la fenêtre
 */
  public static Ville getCity() {
    return city;
  }
/**
 * Remet à 'null' les Stations sélectinnées
 */
  public static void resetSelectedStations() {
    previousStation = null;
    selectedStation = null;
  }
/**
 * Test si le point (x, y) est dans la fenêtre
 * @param x coordonnée x du point
 * @param y coordonnée y du point
 * @return true si le point est dans la fenêtre sinon false
 */
  private boolean inWindow(double x, double y) {
    if (x + center < size && x + center >= 0 && y + center < size && y + center >= 0)
      return true;
    return false;
  }
// /**
//  * Copie la BufferedImage
//  * @ param bi
//  * @ return
//  */
//   private BufferedImage deepCopy(BufferedImage bi) {
//     ColorModel cm = bi.getColorModel();
//     boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
//     WritableRaster raster = bi.copyData(null);
//     return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
//    }
/**
 * Sauvegarde l'image actuelle de la ville/fenêtre
 * @param str nom du fichier .png
 */
  public static void saveF(String str) {
    try {
      File outputfile = new File(str + ".png");
      // ImageIO.write(histo.get(cursor), "png", outputfile);
      ImageIO.write(img, "png", outputfile);
    } catch (IOException e) {
      System.out.println("Erreur lors de la sauvegarde");
    }
  }
// /**
//  * Ajoute l'Objet passé en paramètre à l'historique des actions et stock l'image dans l'historique des images
//  * @param obj Objet à ajouter dans l'historique des actions
//  */
//   private void newDo(Object obj) {
//     if (! done.contains(obj) ) {
//     // int max = histo.size();
//     // Object o;
//     // for (int i = 1; i < max - cursor; i++) {
//     //   o = done.get(cursor +1);
//     //   if (o instanceof Station)
//     //   ((Station) o).delete();
//     //   done.remove(cursor + 1);
//     //   histo.remove(cursor + 1);
//     // }
//     // histo.add( deepCopy( histo.get(cursor) ) );
//     // cursor++;
//     done.add(obj);
//     }
//   }
// /**
//  * Annule la dernière action réalisée
//  * 
//  */
//   public void undo () {
//     if (cursor != 0)
//       cursor--;
//     surf.repaint();
//   }
// /**
//  * Réalise la dernière action annulée
//  * 
//  */
//   public void redo () {
//     if (cursor < histo.size() - 1)
//       cursor++;
//     surf.repaint();
//   }

  /**
   * Développe la Ligne actuellement sélectionnée
   */
  public void split() {
    if (selectedLigne != null) {
      // newDo("split" + selectedLigne.getDistanceTotale());
      String state = MenuDroit.getState();
      if (state == "straight")
        selectedLigne.splitStraight();
      else
        selectedLigne.splitAround();
      // drawAll();
      surf.repaint();
    }
  }
/**
 * Supprime la Ligne actuellement sélectionnée
 */
  public void deleteLigne() {
    if (selectedLigne == null)
      JOptionPane.showMessageDialog(frame, "Select a line first");
    else
      if (JOptionPane.showConfirmDialog(frame, "Delete the line " + selectedLigne.getName() + " ?") == JOptionPane.YES_OPTION){
        // done.remove(selectedLigne);
        city.removeLigne(selectedLigne);
        selectedLigne.delete();
        selectedLigne = null;
        resetSelectedStations();
        // drawAll();
        frame.repaint();
      }
  }
/**
  * Recouvre l'image par un fond de couleur 'bkgrd'
  */
  public void cover() {
    for (int i = 0; i < size; i++)
      for (int j = 0; j < size; j++)
        img.setRGB(i, j, bkgrd);
        // histo.get(cursor).setRGB(i, j, bkgrd);
    surf.repaint();
  }
/**
  * Recouvre les pixels de couleur 'constr'
  */
  public void erase() {
    // newDo("erase");
    for (int i = 0; i < size; i++)
      for (int j = 0; j < size; j++)
        if (img.getRGB(i, j) == constr)
          img.setRGB(i, j, bkgrd);
        // if (histo.get(cursor).getRGB(i, j) == constr)
        //   histo.get(cursor).setRGB(i, j, bkgrd);
    surf.repaint();
  }
// /**
//  * Trace tous les éléments présent dans l'historique
//  */
//   public void drawAll() {
//     cover();
//     drawAllStations();
//     for (Object obj : done) {
//       if (obj instanceof Ligne)
//         drawLigne((Ligne) obj);
//       if (obj == "axe")
//         drawAxe();
//     }
//  }
/**
  * Trace les axes x,y sur la fenêtre grapgique
  *
  */
  public void drawAxe() {
    // newDo("axe");
    axe = true;
    for (int i = 0; i < size; i++){
      if (img.getRGB(i , center) == bkgrd)
        img.setRGB(i, center, constr);
      if (img.getRGB(center, i) == bkgrd)
        img.setRGB(center, i, constr);
      // if (histo.get(cursor).getRGB(center, i) == bkgrd)
      //   histo.get(cursor).setRGB(center, i, constr);
      // if (histo.get(cursor).getRGB(i, center) == bkgrd)
      //   histo.get(cursor).setRGB(i, center, constr);
    }
    surf.repaint();
  }

  public void eraseAxe() {
    // done.remove("axe");
    // drawAll();
    axe = false;
  }
/**
  * Trace le Cercle passé en paramètre dont le point central est de la couleur passée en paramètre
  *
  * @param cercle cerlce à tracer
  * @param color  couleur du point central
  */
  private void drawCercle(Cercle cercle, int color) {
    int r = (int) cercle.getRayon() / 2;
    int cir = 2 * (int) (Math.PI * r);
    int x = (int) cercle.getX();
    int y = (int) cercle.getY();
    for (int i = x - 3; i <= x + 3; i++)
      for (int j = y - 3; j <= y + 3; j++)
        if (inWindow(i, j) && Cercle.getDistance(x, y, i, j) <= 3)
          img.setRGB(i + center, j + center, color);
          // histo.get(cursor).setRGB(i + center, j + center, color);
    int xc, yc; // coordonnées du point du cercle à placer
    for (int n = 0; n < cir; n++) {
      xc = x + (int) (r * Math.cos((2 * Math.PI * n) / cir));
      yc = y + (int) (r * Math.sin((2 * Math.PI * n) / cir));
      for (int i = xc; i <= xc + 1; i++)
        for (int j = yc; j <= yc + 1; j++)
          if (inWindow(i, j) && img.getRGB(i + center, j + center) == bkgrd){
            if (cercle instanceof Ville)
              img.setRGB(i + center, j + center, contour);
            else
              img.setRGB(i + center, j + center, constr);
          }
          // if (inWindow(i, j) && histo.get(cursor).getRGB(i + center, j + center) == bkgrd)
          //   histo.get(cursor).setRGB(i + center, j + center, constr);
    }
    surf.repaint();
  }
/**
 * 
 * 
 * @param temp
 */
  public void drawCercleAllInside(Cercle temp) {
    for (Cercle i : temp.getPtSectAll()) {
      drawCercle(i, 10);
    }
    surf.repaint();
  }
  /**
   * Trace la Station passée en paramètre
   *
   * @param station
   */
  public void drawStation(Station station) {
    // newDo(station);
    switch (station.getDegre()) {
      case NODEG:
        drawCercle(station, nod);
        break;
      case SEC:
        drawCercle(station, sec);
        break;
      case PRI:
        drawCercle(station, pri);
        break;
    }
  }
/**
 * Trace toutes les Station crées
 * 
 */
  public void drawAllStations() {
    for (Station station : Station.getAllStations())
      drawStation(station);
  }
/**
 * Trace le trajet entre les 2 Station passées en paramètre
 * 
 * @param s1
 * @param s2
 * @param color
 */
  private void drawTrajet(Station s1, Station s2, int color) {
    // Coordonnées des 2 stations
    double x1 = s1.getX();
    double y1 = s1.getY();
    double x2 = s2.getX();
    double y2 = s2.getY();
    if (x1 == x2 && y1 == y2) // On trace rien si les stations sont au même emplacement
      return;
    // Plus petit x et plus petit y
    double minX = (x1 < x2 ? x1 : x2);
    double minY = (y1 < y2 ? y1 : y2);
    // Longeur et hauteur du rectangle dont la diagonale est le trajet
    double width = Math.abs(x1 - x2);
    double height = Math.abs(y1 - y2);
    // Coefficents des droites (y = a * x + b) et (x = (y - b) / a)
    double a = (x2 == x1 ? 0 : ((y2 - y1) / (x2 - x1)));
    double b = (x2 == x1 ? 0 : ((x2 * y1 - x1 * y2) / (x2 - x1)));
    // On souhaite placer le plus de point possible donc on choisit la plus grande distance entre width et height afin d'avoir un trait continu
    // Cas où width est plus grand
    if (width >= height) {
      double y; // Coordonnée, de la droite, à déterminée
      // On partcourt la plus grande distance
      for (double x = minX; x <= minX + width; x++) {
        // Déterminisation de la coordonnée mangquante
        y = a * x + b;
        // Eppaississement de la droite pour qu'elle soit plus visible
        for (double i = x - 1; i <= x + 1; i++){
          for (double j = y - 1; j <= y + 1; j++){
            // Vérification que le point (i, j) est dans la fenêtre 
            if (inWindow(i, j)) {
              // Vérification que l'on ne modifie pas la couleur d'une station
              if (img.getRGB((int) (i + center),(int) (j + center)) == bkgrd || img.getRGB((int) (i + center),(int) (j + center)) == constr) {
                img.setRGB((int) (i + center), (int) (j + center), color);
              }
              // if (histo.get(cursor).getRGB((int) (i + center),(int) (j + center)) == bkgrd || histo.get(cursor).getRGB((int) (i + center),(int) (j + center)) == constr) {
              //   histo.get(cursor).setRGB((int) (i + center), (int) (j + center), color);
              // }
            }
          }
        }
      }
    }
    // Cas où height est plus grand
    else {
      double x; // Coordonnée, de la droite, à déterminée
      // On partcourt la plus grande distance
      for (double y = minY; y <= minY + height; y++) {
        // Déterminisation de la coordonnée mangquante
        x = (y - b) / a;
        // Eppaississement de la droite pour qu'elle soit plus visible
        for (double i = x - 1; i <= x + 1; i++){
          for (double j = y - 1; j <= y + 1; j++){
            // Vérification que le point (i, j) est dans la fenêtre 
            if (inWindow(i, j)) {
              // Vérification que l'on ne modifie pas la couleur d'une station
              if (img.getRGB((int) (i + center),(int) (j + center)) == bkgrd || img.getRGB((int) (i + center),(int) (j + center)) == constr) {
                img.setRGB((int) (i + center), (int) (j + center), color);
              // if (histo.get(cursor).getRGB((int) (i + center),(int) (j + center)) == bkgrd || histo.get(cursor).getRGB((int) (i + center),(int) (j + center)) == constr) {
              //   histo.get(cursor).setRGB((int) (i + center), (int) (j + center), color);
              }
            }
          }
        }
      }
    }
    surf.repaint();
  }
/**
 * 
 * @param s1
 * @param s2
 */
  public void drawTrajet(Station s1, Station s2) {
    drawTrajet(s1, s2, trait);
  }
  /**
   * Trace la Ligne passée en paramètre
   * 
   * @param ligne ligne à tracer
   */
  public void drawLigne(Ligne ligne) {
    // newDo(ligne);
    ArrayList<Station> cross = ligne.getStations();
    for (Station station : ligne.getStations()) 
      drawStation(station);
    for (int i = 0; i < cross.size() - 1; i++) 
      drawTrajet(cross.get(i), cross.get(i + 1), ligne.color);
  }
  /**
   * Trace toutes les Ligne de la Ville passée en paramètre
   * 
   * @param city ville dont on veut tracer le métro
   */
  public void drawMetro(Ville city) {
    drawCercle(city, bkgrd);
    for (Station station : city.getStations())
      drawStation(station);
    for (Ligne ligne : city.getMetro())
      drawLigne(ligne);
  }

/**
 * Classe servant d'image principale de la fenêtre
 * 
 * 
 * 
 */
  class MyPanel extends JPanel implements MouseInputListener {
    private static final long serialVersionUID = 1L;

    MyPanel() {
      setPreferredSize(new Dimension(size, size));
      addMouseListener(this);
    }
/**
 * Trace l'image
 * @param g graphic pour dessinr l'image
 */
    public void paintComponent(Graphics g) {
      cover();
      // drawCercleAllInside(city);
      if (axe)
        drawAxe();
      drawMetro(city);
      g.drawImage(img, 0, 0, size, size, null);
      // g.drawImage(histo.get(cursor), 0, 0, size, size, null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {}
/**
 * Actions à réaliser lors d'un clic sur l'image
 * 
 */
    @Override
    public void mousePressed(MouseEvent e) {
      // Coordonnées du clic
      double x = e.getX() - center;
      double y = e.getY() - center;
      String state = MenuDroit.getState(); // Etat actuel des boutons du MenuDroit
      if (selectedStation != null)
        previousStation = selectedStation;
      selectedStation = Station.getStationAt(x, y); // Récupération de la station sélectionnée au point (x, y) (si elle existe)
      switch (MenuDroit.getItem()) { // Détermination de l'item sélectionné à l'aide des boutons du MenuDroit
        case "station" : // Action sur les Stations
          switch (state) {
            case "new" : // Ajout d'une Station
              if (Cercle.getDistance(x, y, city.getX(), city.getY()) > city.getRayon() / 2 ||Station.isNear(x, y))
                break;
              String name = ""; // Nom de la Station
              int rayon = -1; // Rayon d'action de la Station
              JTextField[] field = {new JTextField("Station " + Station.getAllStations().size()), new JTextField("100", 5)};
              String temp;
              while (name == "" || rayon == -1) { // Création d'une fenêtre pour demander le nom et le rayon d'action de la Station
                JPanel myPanel = new JPanel();
                myPanel.add(new JLabel("Name : "));
                if (name == "")
                  myPanel.add(field[0]);
                else
                  myPanel.add(new JLabel(name));
                myPanel.add(new JLabel("Effective Radius : "));
                if (rayon == -1)
                  myPanel.add(field[1]);
                else
                  myPanel.add(new JLabel("" + rayon));
                if ( JOptionPane.showConfirmDialog(frame, myPanel, "Please Enter Name & Effective Diameter of the station", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION){
                  name = field[0].getText();
                  temp = field[1].getText();
                  if ( rayon == -1 && isInt(temp) ) 
                    rayon = Integer.parseInt(temp);
                }
                else {
                  name = "out";
                  rayon = 0;
                }
              }
              if (rayon != 0) { // Vérifie que l'utilisateur n'a pas annuler la requête
                Station newOne = new Station(name, Cercle.cartesianToPolarTheta(x, y), Cercle.cartesianToPolarNorme(x, y), rayon, city);
                // drawStation( newOne );
                city.addStation(newOne);
              }
              break;
            
            case "delete" : // Suppression d'une Station
              // Vérifie qu'une Station à été sélectionnée et demande une confirmation de suppression de la Station
              if (selectedStation != null && JOptionPane.showConfirmDialog(frame, "Delete the station : '" + selectedStation.getName() +"' ?") == JOptionPane.YES_OPTION) {
                selectedStation.delete();
                city.removeStation(selectedStation);
                selectedStation = null;
                // drawAll();
              }
              break;
          }
          break;

        case "degree" : // Actions sur le Degre d'une Station
          if (selectedStation == null) // Test si une Station a été sélectionnée (permet d'éviter des NullPointerException)
            break;
          switch (state) {
            case "up" : // Augmente le Degre de la Station sélectionnée
              selectedStation.setDegre(true);
              break;
            
            case "down" :
              selectedStation.setDegre(false); // Diminue le Degre de la Station sélectionnée
              break;
          }
          drawStation(selectedStation);
          break;

        case "ligne" : // Actions sur les Ligne
          switch (state) {
            case "new" : // Création d'une nouvelle Ligne
              if (previousStation != null && selectedStation != null && previousStation != selectedStation){ // Vérifie que 2 Station on été sélectionnées 
                if (selectedStation.isMaxCrossed() || previousStation.isMaxCrossed()) { // Vérifie qu'il n'y pas trop de Lignes qui passent par les stations
                  JOptionPane.showMessageDialog(frame, "One of the Station is already crossed by too much line");
                  break;
                }
                String name = JOptionPane.showInputDialog("Name of the Line from " + previousStation.getName() + " to " + selectedStation.getName() + " :", "Line");
                if (name != "") {
                  selectedLigne = new Ligne(name, previousStation, selectedStation);
                  // drawLigne(selectedLigne);
                  city.addLigne(selectedLigne);
                  resetSelectedStations();
                }
              }
              break;
            
            case "select" : // Sélection d'une Ligne
              if (previousStation != null && selectedStation != null){ // Vérifie que 2 Station on été sélectionnées
                // Regarde si les 2 Station sélectionnées sont les terminus d'une Ligne
                ArrayList<Ligne> lc = selectedStation.getCrossed();
                for (Ligne ligne : selectedStation.getCrossed() ) {
                  if (lc.contains(ligne)){
                    Station[] term = ligne.getTerminus();
                    if ( (term[0] == previousStation && term[1] == selectedStation) || (term[0] == selectedStation && term[1] == previousStation)) {
                      selectedLigne = ligne;
                      JOptionPane.showMessageDialog(frame, "Line select : " + selectedLigne.getName());
                      resetSelectedStations();
                      break;
                    }
                  }
                }
              }
              break;
            
            case "add" : // Ajout d'une Station à la Ligne sélectionnée
              if (selectedLigne != null && selectedStation != null){
                selectedLigne.addStation(selectedStation);
                // drawAll();
              }
              break;

            case "remove" : // Suppression d'une Station à la Ligne sélectionnée
              if (selectedLigne != null && selectedStation != null){
                selectedLigne.notCrossing(selectedStation);
                selectedStation = null;
                // drawAll();
              }
              break;
              
          }
          break;
      }
      frame.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}
  }
}
