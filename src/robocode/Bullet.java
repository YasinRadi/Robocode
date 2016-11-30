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
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 *
 * @author Yasin Radi
 */
public class Bullet extends ShapeObject {

    private int power;
    private Tank trigger;

    public Bullet(double vx, double vy, double angle,
            boolean visible, Color color, int power, Tank trigger) {
        super(0, 0, vx, vy, angle, visible, null, color);
        
        AffineTransform tranform = AffineTransform.getRotateInstance(
            Math.toRadians(this.getAngle()), 
                trigger.getX() + (trigger.getWidth() / 2) - 12,
                trigger.getY() + (trigger.getHeight() / 2) - 11);
        
        Point2D before = new Point2D.Double(this.getX() + 15, this.getY() - 11);
        Point2D after  = new Point2D.Double();
        after = tranform.transform(before, after);
        
        Ellipse2D bullet = new Ellipse2D.Double(after.getX(), after.getY(), this.getPower(), this.getPower());
        
        this.setX(after.getX());
        this.setY(after.getY());
        this.setShape(bullet);
        this.setPower(power);
        this.setTrigger(trigger);
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public Tank getTrigger() {
        return trigger;
    }

    public void setTrigger(Tank trigger) {
        this.trigger = trigger;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setPaint(this.getColor());
        g2d.fill(this.getShape());
    }
    
    public void move(){

        this.setX(this.getX() + Utilities.calculateCoordX(this.getAngle(), this.getVx()));
        this.setY(this.getY() + Utilities.calculateCoordY(this.getAngle(), this.getVy()));
        this.setShape(new Ellipse2D.Double(this.getX(),this.getY(), this.getPower(), this.getPower()));
    }

}
