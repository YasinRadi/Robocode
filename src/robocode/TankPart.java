/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robocode;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 *
 * @author YasinR
 */
public class TankPart extends ImageObject {

    private double dx, dy;

    public TankPart(double x, double y, double vx, double vy, double angle,
            double dx, double dy, boolean visible, String file) {

        super(x, y, vx, vy, angle, visible, file);

        this.setDx(dx);
        this.setDy(dy);
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform at = new AffineTransform();
        at.translate(this.getX() + this.getDx(), this.getY() + this.getDy());
        at.rotate(Math.toRadians(this.getAngle()), this.getWidth() / 2, this.getHeight() / 2);
        g2d.drawImage(img, at, null);
    }
}
