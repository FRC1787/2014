package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;

public class Autonomous {
    
    //This is a function called turnTowardsTarget. It is for positioning the robot in the direction of a goal
    //The functionality of the PidDriveController needs to be placed back into the project for it to work - Dale
    /*public boolean turnTowardsTarget() {
     VisionTarget target = getBestTarget(true, true);
     if (target == null) {
     System.out.println("no targets found");
     return false;
     } 

     System.out.println(target.isHighGoal ? "high goal found" : "middle goal found");
     System.out.println("distance to target: " + target.distance);

     pidDriveBase.queueTurnAngle(target.angle, 5.0);
     //      pidDriveBase.queueDriveDistance(target.distance - 180.0, 4.0);


     return true;
     }*/
    
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
