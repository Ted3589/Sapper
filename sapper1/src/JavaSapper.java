import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import sapper.Box;
import sapper.Coord;
import sapper.Game;
import sapper.Ranges;

public class JavaSapper  extends JFrame {
    private  JPanel panel;
    private JLabel label;
    private Game game;
    private final int cols = 9;
    private final int rows = 9;
    private final int bombs = 10;
    private final int img_size = 50;

    public static void main(String[] args) {
        new JavaSapper();

    }

    private  JavaSapper(){
        game = new Game(cols,rows,bombs);
        game.start();
        setImg();
        initLabel();
        initPanel();
        initFrame();
    }

    private void initLabel(){
        label = new JLabel("haha");
        add(label, BorderLayout.SOUTH);
    }

    private void initPanel(){
        panel = new JPanel(){
            @Override
            protected void paintComponent (Graphics g) {
                super.paintComponent(g);
                for (Coord coord: Ranges.getAllCoords()) {
                    g.drawImage((Image) game.getBox(coord).img, coord.x * img_size, coord.y* img_size, this);
                }
            }
        };
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
               int x = e.getX() / img_size;
               int y = e.getY() / img_size;
               Coord coord = new Coord(x,y);
               if(e.getButton() == MouseEvent.BUTTON1)
                   game.pressLeftBtn(coord);
                if(e.getButton() == MouseEvent.BUTTON2)
                    game.start();
                if(e.getButton() == MouseEvent.BUTTON3)
                    game.pressRightBtn(coord);
                label.setText(getMessage());
               panel.repaint();
            }
        });
        panel.setPreferredSize(new Dimension(Ranges.getSize().x*img_size,Ranges.getSize().y*img_size));
        add(panel);
    }

    private String getMessage() {
        switch (game.getState()) {
            case Bombed:
                return "Вы проиграли!";
            case Played:
                return "Давай жми!";
            case Winner:
                return "Вы выиграли!";
            default: return "";
        }
    }

    private void initFrame(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Сапёр");
        setResizable(false);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        setIconImage(getImg("icon"));
    }

    private void setImg(){
        for (Box box: Box.values())
            box.img = getImg(box.name().toLowerCase());
    }

    private Image getImg(String name){
        String filename = "img/"+ name +".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();
    }
}
