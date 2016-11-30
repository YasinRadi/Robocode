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
    //private Craft craft;
    private final int DELAY = 10;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 700;
    private Tank t;
    public static int tankWidth, tankHeight;
    public static LinkedList<Bullet> bullets;

    public Board() {

        initBoard();
    }
    
    private void initBoard() {
        
        addKeyListener(new TAdapter());
        addMouseWheelListener( new MWAdapter());
        addMouseListener( new MAdapter());
        
        setFocusable(true);
        setBackground(Color.BLACK);

        //craft = new Craft();
        bullets = new LinkedList<>();
        t = new Tank(140, 160, 3, 3, 0, true);
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
//        Graphics2D g2d = (Graphics2D) g; 
        //g2d.drawImage(t.getImg(),(int) craft.getX(), (int) craft.getY(), this);
        t.paint(g);
        for(Bullet b : bullets)
            b.paint(g);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        t.move();
        for(Bullet b : bullets)
            b.move();
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
    
    private class MAdapter extends MouseAdapter{

        @Override
        public void mouseClicked(MouseEvent e) {
            t.mouseClick(e);
        }
        
    }
    
    public static boolean isInBoard(double x, double y){
        boolean isIn = false;
        if((0 <= x && x <= Board.WIDTH - tankWidth*1.5) 
                && (0 <= y && y <= Board.HEIGHT - tankHeight*2)){
            isIn = true;
        } 
        return isIn;
    }
}
