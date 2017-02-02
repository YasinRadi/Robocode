/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robocode;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.LinkedList;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private Timer timer;
    private final int DELAY = 10;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 700;
    private Tank t;
    public static int tankWidth, tankHeight;
    public static LinkedList<Bullet> bullets;
    public static LinkedList<Tank> tanks;
    //public static Explode explosion;
    public static LinkedList<Explode> explosions;

    public Board() {

        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        addMouseWheelListener(new MWAdapter());
        addMouseListener(new MAdapter());

        setFocusable(true);
        setBackground(Color.BLACK);
        explosions = new LinkedList<>();
        bullets = new LinkedList<>();
        t = new Tank(140, 160, 3, 3, 0, true);
        tanks = new LinkedList<>();
        tanks.add(new Tank(400, 500, 3, 3, 0, true));
        
        tankWidth = t.getWidth();
        tankHeight = t.getHeight();
        timer = new Timer(DELAY, this);
        timer.start();

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);

        Toolkit.getDefaultToolkit().sync();
    }

    private void doDrawing(Graphics g) {
        t.paint(g);
        for(Tank tank : tanks)
        {
            tank.paint(g);
        }
        for (Bullet b : bullets) {
            if(b.isVisible())
                b.paint(g);
        }
        for(Explode e : explosions)
        {
            e.paint(g);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        t.move();
        for (Bullet b : bullets) {
            b.move();
            for(Tank tank : tanks)
            {
                if(b.intersects(tank))
                {
                    b.hit(tank);
                }
                tank.death();
            }
            b.hitWall();
        }
        for(int i = bullets.size() - 1; i >= 0; i--)
        {
            Bullet del = null;
            if(!bullets.get(i).isVisible())
            {
                del = bullets.get(i);
            }
            bullets.remove(del);
        }
        repaint();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            t.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            t.keyPressed(e);
        }
    }

    private class MWAdapter extends MouseAdapter {

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            t.mouseWheelMoved(e);
        }

    }

    private class MAdapter extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            t.mouseClick(e);
        }

    }

    public static boolean isInBoard(double x, double y) {
        boolean isIn = false;
        if ((0 <= x && x <= Board.WIDTH - tankWidth * 1.5)
                && (0 <= y && y <= Board.HEIGHT - tankHeight * 2)) {
            isIn = true;
        }
        return isIn;
    }
    
    
}
