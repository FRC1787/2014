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

    //This is the autoDrive function, it sets the safety to false, then drives the robot forward and then delays for 2 seconds
    public static void autoDrive() {
        Variables.robotDrive.setSafetyEnabled(false);
        // Stupidity control
        Variables.gearShifter.set(DoubleSolenoid.Value.kForward);
        Variables.robotDrive.arcadeDrive(1, 0.0);
        Timer.delay(2.0);
        Variables.robotDrive.drive(0.0, 0.0);
    }
    
    // Lower the loader mechanism
    public static void lowerLoader()
    {
        Variables.pickupSolenoid.set(true);
    }
    
    //This is the autoShoot function, it fires the solenoid for the catapult.
    public static void autoShoot() {
            Variables.catapultSolenoid1.set(DoubleSolenoid.Value.kReverse);
            Variables.catapultSolenoid2.set(DoubleSolenoid.Value.kReverse);
            Timer.delay(2);
            Variables.catapultSolenoid1.set(DoubleSolenoid.Value.kForward);
            Variables.catapultSolenoid2.set(DoubleSolenoid.Value.kForward);
    }
}
