/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robocode;

/**
 *
 * @author YasinR
 */
public class Utilities {
    
    public static double calculateCoordX(double angle, double vx) {
        return vx * Math.sin(Math.toRadians(angle));
    }

    public static double calculateCoordY(double angle, double vy) {
        return -vy * Math.cos(Math.toRadians(angle));
    }
}
