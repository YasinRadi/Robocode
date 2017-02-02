/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robocode;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

/**
 *
 * @author YasinR
 */
public class Tank extends ImageObject {

    private int upDown;
    private int rightLeft;
    private int mouseWheel;
    private int mouseRotation;
    private ArrayList<TankPart> parts;
    private int hp;

    public Tank(double x, double y, double vx, double vy, double angle,
            boolean visible) {
        super(x, y, vx, vy, angle, visible, null);

        this.setParts(new ArrayList<>());
        // Body
        this.getParts().add(new TankPart(this.getX(), this.getY(), 2, 2, 0, 0,
                0, true, "body.png"));
        // Turret
        this.getParts().add(new TankPart(this.getX(), this.getY(), 5, 5, 0, 7,
                -7, true, "turret.png"));
        // Radar
        this.getParts().add(new TankPart(this.getX(), this.getY(), 2, 2, 0, 6,
                14, true, "radar.png"));
        this.setWidth(this.getParts().get(0).getWidth());
        this.setHeight(this.getParts().get(0).getHeight());

        this.setHp(5);
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getUpDown() {
        return upDown;
    }

    public void setUpDown(int upDown) {
        this.upDown = upDown;
    }

    public int getRightLeft() {
        return rightLeft;
    }

    public void setRightLeft(int rightLeft) {
        this.rightLeft = rightLeft;
    }

    public ArrayList<TankPart> getParts() {
        return parts;
    }

    public void setParts(ArrayList<TankPart> parts) {
        this.parts = parts;
    }

    public int getMouseWheel() {
        return mouseWheel;
    }

    public void setMouseWheel(int mouseWheel) {
        this.mouseWheel = mouseWheel;
    }

    public int getMouseRotation() {
        return mouseRotation;
    }

    public void setMouseRotation(int mouseRotation) {
        this.mouseRotation = mouseRotation;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        if (this.isVisible()) {
            for (int i = 0; i < this.parts.size(); i++) {
                this.getParts().get(i).paint(g);
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            }
        }
    }

    public void move() {
        double x0 = this.getX(), y0 = this.getY();

        // Main body rotation
        if (this.getRightLeft() < 0) {
            this.setAngle(this.getAngle() - this.getVx());
        }
        if (this.getRightLeft() > 0) {
            this.setAngle(this.getAngle() + this.getVx());
        }
        // Traslation
        if (this.getUpDown() < 0) {
            x0 = this.getX() + Utilities.calculateCoordX(this.getAngle(), this.getVx());
            y0 = this.getY() + Utilities.calculateCoordY(this.getAngle(), this.getVy());
        }
        if (this.getUpDown() > 0) {
            x0 = this.getX() - Utilities.calculateCoordX(this.getAngle(), this.getVy());
            y0 = this.getY() - Utilities.calculateCoordY(this.getAngle(), this.getVy());
        }
        // Boundaries controlling
        if (Board.isInBoard(x0, y0)) {
            this.setX(x0);
            this.setY(y0);
            // Part coordinates updating
            for (int i = 0; i < this.getParts().size(); i++) {
                // Tank Parts X & Y
                this.getParts().get(i).setX(this.getX());
                this.getParts().get(i).setY(this.getY());
                // Tank Parts Angle
                if (this.getMouseRotation() != 0 && i == 1) {
                    if (this.getRightLeft() < 0) {
                        this.getParts().get(1).setAngle(this.getParts().get(1).getAngle()
                                - this.getVx() + this.getMouseWheel());
                    }
                    if (this.getRightLeft() > 0) {
                        this.getParts().get(1).setAngle(this.getParts().get(1).getAngle()
                                + this.getVx() + this.getMouseWheel());
                    }

                } else {
                    this.getParts().get(i).setAngle(this.getAngle());
                }
            }
        }
        if (this.getMouseWheel() != 0) {
            this.getParts().get(1).setAngle(this.getParts().get(1).getAngle() + this.getMouseWheel());
        }
        this.setMouseWheel(0);
    }

    public Bullet fire() {
        return new Bullet(6, 6, this.getParts().get(1).getAngle(), true,
                Color.RED, 7, this);
    }

    public void death() {
        if (this.getHp() == 0) {
            this.setHp(-1);
            this.setVisible(false);
            Board.explosions.add(new Explode("exploteDeath.png", 130, 130, 64, 50, false, (int) this.getX(), (int) this.getY(), 0));
        }
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A) {
            this.setRightLeft(-1);
        }

        if (key == KeyEvent.VK_D) {
            this.setRightLeft(1);
        }

        if (key == KeyEvent.VK_W) {
            this.setUpDown(-1);
        }

        if (key == KeyEvent.VK_S) {
            this.setUpDown(1);
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A) {
            this.setRightLeft(0);
        }

        if (key == KeyEvent.VK_D) {
            this.setRightLeft(0);
        }

        if (key == KeyEvent.VK_W) {
            this.setUpDown(0);
        }

        if (key == KeyEvent.VK_S) {
            this.setUpDown(0);
        }
    }

    public void mouseWheelMoved(MouseWheelEvent e) {

        this.mouseRotation = e.getWheelRotation();

        if (this.mouseRotation > 0) {
            this.setMouseWheel(2);

        } else if (this.mouseRotation < 0) {
            this.setMouseWheel(-2);
        }
    }

    public void mouseClick(MouseEvent e) {
        Board.bullets.add(this.fire());
        System.out.println("Fired Bullets: " + Board.bullets.size());
    }

}
