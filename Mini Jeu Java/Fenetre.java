import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Fenetre extends JFrame implements KeyListener {
    private static final long serialVersionUID = 1L;
    private static final JFrame frame = new JFrame();
    public static final Monde monde = Monde.world;
    public static final MenuDroite menuDroite = new MenuDroite();
    public static final MenuGauche menuGauche = new MenuGauche();
    private static Fenetre fenetre;

    public Fenetre () {
        if (fenetre == null)
            fenetre = this;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Monde.world.setLayout(new BorderLayout());
        frame.add(monde, BorderLayout.CENTER);
        frame.add(menuDroite, BorderLayout.EAST);
        frame.add(menuGauche, BorderLayout.WEST);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setFocusable(true);
        frame.setLocationRelativeTo(null);
        frame.addKeyListener(this);
        addKeyBinding(KeyEvent.VK_Q, "left", (evt) -> {
            if (Interact.getState() == "play")
                Jeu.getCurrPlay().seDeplacer(-1, 0);
            repaint();
        });
        addKeyBinding(KeyEvent.VK_D, "right", (evt) -> {
            if (Interact.getState() == "play")
                Jeu.getCurrPlay().seDeplacer(+1, 0);
            repaint();
        });
        addKeyBinding(KeyEvent.VK_Z, "forward", (evt) -> {
            switch (Interact.getState()) {
                case "play" :
                    Jeu.getCurrPlay().seDeplacer(0, -1);
                    break;
                case "shop" :
                    if (Interact.getCursor() > 0)
                        Interact.cursorUp();
                    else
                        Interact.cursorMax();
                    break;
            }
            repaint();
        });
        addKeyBinding(KeyEvent.VK_S, "down", (evt) -> {
            switch (Interact.getState()) {
                case "play" :
                    Jeu.getCurrPlay().seDeplacer(0, +1);
                    break;
                case "shop" :
                    if (Interact.getCursor() < Interact.getMaxCursor())
                        Interact.cursorDown();
                    else
                        Interact.cursorMin();
                    break;
            }
            repaint();
        });
        addKeyBinding(KeyEvent.VK_SPACE, "action", (evt) -> {
            switch (Interact.getState()) {
                case "play" :
                    Interact.stop();
                    break;
                case "shop" :
                    Interact.choose();
                    break;
                case "talk" :
                    Interact.play();
                    break;
                case "choose" :
                    Interact.play();
                    break;
            }
            repaint();
        });
    }

    public void repaint() {
        monde.repaint();
        menuDroite.repaint();
    }

    private void addKeyBinding(int keyCode, String id, ActionListener listener) {
        InputMap im = frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = frame.getRootPane().getActionMap();

        im.put(KeyStroke.getKeyStroke(keyCode, 0, false), id);

        am.put(id, new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                listener.actionPerformed(e);
            }
        });

    }

    public void keyTyped (KeyEvent ke) { }

    public void keyPressed (KeyEvent ke) {
        switch (Interact.getState()){
            case "init" :
                Avatar player = Jeu.getCurrPlay();
                String name = player.getNom();
                int size = name.length() - 1;
                name = name.substring(0, size);
                switch (ke.getKeyCode()) {
                    case KeyEvent.VK_SHIFT :
                        break;
                    case KeyEvent.VK_BACK_SPACE :
                        if ( size > 0 )
                            name = name.substring(0, size - 1);
                        break;
                    case KeyEvent.VK_ENTER :
                        Interact.stop();
                        player.setNom(name);
                        return;
                    default :
                        if (size < 20)
                            name += String.valueOf( ke.getKeyChar() );
                        break;
                }
                player.setNom(name + "_");
                menuDroite.repaint();
                break;

            case "tour" :
                int key = ke.getKeyCode();
                int nb = Jeu.getNb_tours_max();
                switch (key) {
                    case KeyEvent.VK_Z :
                        Jeu.setNbTours(+1);
                        break;
                    case KeyEvent.VK_S :
                        Jeu.setNbTours(-1);
                        break;
                    case KeyEvent.VK_D :
                        Jeu.setNbTours(+5);
                        break;
                    case KeyEvent.VK_Q :
                        Jeu.setNbTours(-5);
                        break;
                    case KeyEvent.VK_ENTER :
                        Interact.play();
                        break;
                }
                menuDroite.repaint();
        }
    }

    public void keyReleased (KeyEvent ke) { }
/*
    public void removeListener(){
        frame.removeListener(this);
    }*/

    public static Fenetre getFenetre(){
        return fenetre;
    }
}
