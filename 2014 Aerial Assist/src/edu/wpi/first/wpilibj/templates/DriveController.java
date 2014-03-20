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
            //Pretty self explanitory....the driving controls...
            if (Variables.rightStick.getMagnitude() > Variables.leftStick.getMagnitude()) {
                Variables.robotDrive.arcadeDrive(Variables.rightStick.getY() * Variables.driveSpeed, Variables.rightStick.getX() * Variables.driveSpeed, true);
            } else {
                Variables.robotDrive.arcadeDrive(-Variables.leftStick.getY() * Variables.driveSpeed, Variables.leftStick.getX() * Variables.driveSpeed, true);
            }
        }
   
   
  /* public static void shiftingControls() {
         //Shifting functionl.
            if (Variables.leftStick.getRawButton(4) || Variables.rightStick.getRawButton(4) || !Variables.shifterPosition) {
                Variables.gearShifter.set(DoubleSolenoid.Value.kForward);
                Variables.shifterPosition = false;
            } else 
            if (Variables.leftStick.getRawButton(5) || Variables.rightStick.getRawButton(5) || Variables.shifterPosition) {
                Variables.gearShifter.set(DoubleSolenoid.Value.kReverse);
                Variables.shifterPosition = true;
            }
    }*/
   
   public static void loaderControls() {
            //pickup solenoid
            if (Variables.leftStick.getRawButton(3) || Variables.rightStick.getRawButton(3)) {
                Variables.pickupSolenoid.set(true);
            } else if (Variables.leftStick.getRawButton(2) || Variables.rightStick.getRawButton(2)) {
                Variables.pickupSolenoid.set(false);
            }
       
            //Pickup motor
            double pickupSpeed = 0;
            if (Variables.leftStick.getRawButton(4) || Variables.rightStick.getRawButton(4)) {
                pickupSpeed = -0.5;
            }
            if (Variables.leftStick.getRawButton(5) || Variables.rightStick.getRawButton(5)) {
                pickupSpeed = 0.5;
            }
            Variables.pickupMotor.set(pickupSpeed);
            
            
   }
}
