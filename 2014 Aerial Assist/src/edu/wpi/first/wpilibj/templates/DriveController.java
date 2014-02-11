package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
public class DriveController {
    
   public static void driveControls() {
        double lastTime, timeDelta = 0.0;
        double time = Timer.getFPGATimestamp();
            lastTime = time;
            time = Timer.getFPGATimestamp();
            timeDelta = time - lastTime;
            
            if (Variables.rightStick.getMagnitude() > Variables.leftStick.getMagnitude()) {
                Variables.robotDrive.arcadeDrive(Variables.rightStick.getY() * Variables.driveSpeed, Variables.rightStick.getX() * Variables.driveSpeed, true);
            } else {
                Variables.robotDrive.arcadeDrive(-Variables.leftStick.getY() * Variables.driveSpeed, Variables.leftStick.getX() * Variables.driveSpeed, true);
            }
        }
   
   
   public static void shiftingControls() {
         //Shifting functionl.
            if (Variables.leftStick.getRawButton(4) || Variables.rightStick.getRawButton(4) || !Variables.shifterPosition) {
                Variables.gearShifter.set(DoubleSolenoid.Value.kForward);
                Variables.shifterPosition = false;
            } else 
            if (Variables.leftStick.getRawButton(5) || Variables.rightStick.getRawButton(5) || Variables.shifterPosition) {
                Variables.gearShifter.set(DoubleSolenoid.Value.kReverse);
                Variables.shifterPosition = true;
            }
    }
   
   public static void loaderControls() throws CANTimeoutException {
       if (Variables.leftStick.getRawButton(5)){
           Variables.robotLoad.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
       }
       //This is the loading function. Right now I'm working on it, so it may not work -Jeremy
       
   }
}
