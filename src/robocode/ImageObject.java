/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robocode;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author YasinR
 */
public abstract class ImageObject extends GraphicObject{
    
    protected Image img;
    protected int width;
    protected int height;
    protected String fileName;

    public ImageObject(double x, double y, double vx, double vy, double angle, 
            boolean visible, String fileName) {
        super(x, y, vx, vy, angle, visible);
        
        this.setFileName(fileName);
        this.setImg(new ImageIcon("src/img/"+this.getFileName()).getImage());
        this.setHeight( this.getImg().getHeight(null));
        this.setWidth( this.getImg().getWidth(null));
        
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    
}
