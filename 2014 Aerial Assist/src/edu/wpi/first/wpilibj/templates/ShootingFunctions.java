package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;

public class ShootingFunctions {
    
    //This is the shooting funcito, when the trigger is pressed, the solenoid for the catapult is fired.
    public static void shootingPiston() {    
        if (Variables.leftStick.getRawButton(1) || Variables.rightStick.getRawButton(1)) {
            Variables.catapultSolenoid1.set(DoubleSolenoid.Value.kForward);
          //  Variables.catapultSolenoid2.set(DoubleSolenoid.Value.kReverse);
            Variables.shootRetractTime = 2.0;
            //This is the amount of time it takes for the piston to retract
        }
        /*shootRetractTime -= timeDelta;
        if (shootRetractTime
                < 0.0) {
            shootRetractTime = 0.0;*/ //Still needs testing before added, keep though
            //Variables.catapultSolenoid1.set(DoubleSolenoid.Value.kReverse);
            //Variables.catapultSolenoid2.set(DoubleSolenoid.Value.kForward);
        }
}
