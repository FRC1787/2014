package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
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
            //pickup
            if (Variables.leftStick.getRawButton(3) || Variables.rightStick.getRawButton(3)) {
                Variables.pickupMotor.set(1);
            } else {
                Variables.pickupMotor.set(0);
            }
            if (Variables.leftStick.getRawButton(2) || Variables.rightStick.getRawButton(2)) {
                Variables.pickupSolenoid.set(true);
            } else if (Variables.leftStick.getRawButton(6) || Variables.rightStick.getRawButton(6)) {
                Variables.pickupSolenoid.set(false);
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
}
