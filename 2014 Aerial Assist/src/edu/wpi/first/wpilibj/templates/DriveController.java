package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Timer;
//import  edu.wpi.first.wpilibj.templates.Robot.leftstick;
//import edu.wpi.first.wpilibj.templates.Robot.rightstick;
/**
 * @Jeremy Stark
 **/
public class DriveController {
    
    public static CANJaguar leftMotor1;
    public static CANJaguar leftMotor2;
    public static CANJaguar rightMotor1;
    public static CANJaguar rightMotor2;
    
    public DriveController() {
        
    }
    
    public static void driveControls() {
        double lastTime, timeDelta = 0.0;
        double time = Timer.getFPGATimestamp();
        while (Robot.isOperatorControlled() && Robot.isEnabled()) {
            lastTime = time;
            time = Timer.getFPGATimestamp();
            timeDelta = time - lastTime;

            if (Robot.rightstick.getMagnitude() > Robot.leftstick.getMagnitude()) {
                Robot.robotDrive.arcadeDrive(Robot.rightstick.getY() * 0.25, Robot.rightstick.getX() * 0.25, true);
            } else {
                Robot.robotDrive.arcadeDrive(-Robot.leftstick.getY() * 0.25, Robot.leftstick.getX() * 0.25, true);
            }
            //pickup
            if (Robot.leftstick.getRawButton(3) || Robot.rightstick.getRawButton(3)) {
                Robot.pickupMotor.set(1);
            } else {
                Robot.pickupMotor.set(0);
            }
            if (Robot.leftstick.getRawButton(2) || Robot.rightstick.getRawButton(2)) {
                ShootingFunctions.pickupSolenoid.set(true);
            } else if (Robot.leftstick.getRawButton(6) || rightstick.getRawButton(6)) {
                ShootingFunctions.pickupSolenoid.set(false);
            }
        }
    }
    
}
