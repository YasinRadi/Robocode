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
        
        AffineTransform transform = AffineTransform.getRotateInstance(
            Math.toRadians(this.getAngle()), 
                trigger.getX() + (trigger.getWidth() / 2) - 2,
                trigger.getY() + (trigger.getHeight() / 2) - 3);
        
        Point2D before = new Point2D.Double(trigger.getX() + 14, trigger.getY() - 15);
        Point2D after  = new Point2D.Double();
        after = transform.transform(before, after);
        
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
    
    public boolean intersects(Tank t)
    {
        return this.getShape().intersects(t.getX(), t.getY(), t.getWidth(), t.getHeight());
    }
    
    public boolean intersectsWall()
    {
        return this.getX() >= Board.WIDTH - 20|| this.getY() >= Board.HEIGHT - 40 || this.getX() <= 0 + 10 || this.getY() <= 0 + 5;
    }
    
    public void hit(Tank t)
    {
        if(this.isVisible())
        {
            Board.explosions.add( new Explode("exploteBullet.png", 60, 60, 18, 50, false, (int) this.getX(), (int) this.getY(), 0));
            this.setVisible(false);
        }
            
    }
    
    public void hitWall()
    {
        if(this.isVisible() && this.intersectsWall())
        {
            Board.explosions.add( new Explode("exploteBullet.png", 60, 60, 18, 50, false, (int) this.getX(), (int) this.getY(), 0));
            this.setVisible(false);
        }
            
    }

}
