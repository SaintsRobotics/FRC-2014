package com.saintsrobotics.frc;

import edu.wpi.first.wpilibj.DigitalOutput;

/**
 *
 * @author Saints Robotics
 */
public class LightShow {
    
    private static final int MODULE_NUMBER = 1;
    
    private static final int PIN1 = 12;
    private static final int PIN2 = 13;
    private static final int PIN3 = 14;
    
    private static final DigitalOutput LightPin1 = new DigitalOutput(MODULE_NUMBER,PIN1);
    private static final DigitalOutput LightPin2 = new DigitalOutput(MODULE_NUMBER,PIN2);
    private static final DigitalOutput LightPin3 = new DigitalOutput(MODULE_NUMBER,PIN3);
    
    public LightShow() {
        LightShow.setDefault();
    }
    
    public static void setDefault() {
        LightPin1.set(false);
        LightPin2.set(false);
        LightPin3.set(false);
        System.out.println("Default");
    }
    
    public static void setShootStandby() {
        LightPin1.set(true);
        LightPin2.set(false);
        LightPin3.set(false);
        System.out.println("Shoot Standby");
    }
    
    public static void setShoot() {
        LightPin1.set(false);
        LightPin2.set(true);
        LightPin3.set(false);
        System.out.println("Shoot!");
    }
    
    public static void setClimbUnfin() {
        LightPin1.set(true);
        LightPin2.set(true);
        LightPin3.set(false);
        System.out.println("Climb Unfin");
    }
    
    public static void setClimbFin() {
        LightPin1.set(false);
        LightPin2.set(false);
        LightPin3.set(true);
        System.out.println("Climb fin");
    }
    
    public static void setDisabled() {
        LightPin1.set(true);
        LightPin2.set(false);
        LightPin3.set(true);
        System.out.println("Light Disabled");
    }
}
