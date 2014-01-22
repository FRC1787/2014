package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;

/**
 * @Jeremy Stark
 **/
public class Autonomous {
    
    public static void autoDrive() {
        while(!Variables.isOperatorControlled && Variables.isEnabled) {
            double angle = Variables.gyro.getAngle();
            Variables.robotDrive.arcadeDrive(-1.0, -angle * Variables.driveSpeed);
            Timer.delay(0.1);
        } 
    }
    
    public static void autoShoot() {
        while (!Variables.isOperatorControlled) {
            Variables.catapultSolenoid1.set(DoubleSolenoid.Value.kForward);
            Variables.catapultSolenoid2.set(DoubleSolenoid.Value.kForward);
            
            Variables.robotDrive.arcadeDrive(Variables.rightStick.getY() * 0.25, Variables.rightStick.getX() * 0.25, true);
        }
    }
   
}
