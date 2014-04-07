package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;

public class ShootingFunctions {
    
    public static void debug(String text)
    {
        if (Variables.printLauncherDebugMessage)
        {
            System.out.println(text);
        }
    }
    
    //This is the shooting funcito, when the trigger is pressed, the solenoid for the catapult is fired.
    public static void shootingPiston() {    
        if (Variables.leftStick.getRawButton(8) || Variables.rightStick.getRawButton(8)) {
            // Retract the piston
            Variables.catapultSolenoid1.set(DoubleSolenoid.Value.kForward);
            Variables.catapultSolenoid2.set(DoubleSolenoid.Value.kForward);
        }
        if (Variables.leftStick.getRawButton(1) || Variables.rightStick.getRawButton(1)){
            // Launch the piston
            Variables.catapultSolenoid1.set(DoubleSolenoid.Value.kReverse);
            Variables.catapultSolenoid2.set(DoubleSolenoid.Value.kReverse);
            if (Variables.automaticRetractEnabled)
            {
                Variables.launcherRetracting = true;
                Variables.shootRetractTime = Timer.getFPGATimestamp()+2.0;
                debug("Retract timestamp: " + Variables.shootRetractTime);
            }
        }
        
        if (Variables.launcherRetracting)
        {
            debug("Current timestamp: " + Timer.getFPGATimestamp());
            if (Timer.getFPGATimestamp() >= Variables.shootRetractTime)
            {
                debug("Retracting");
                Variables.catapultSolenoid1.set(DoubleSolenoid.Value.kForward);
                Variables.catapultSolenoid2.set(DoubleSolenoid.Value.kForward);
                Variables.launcherRetracting = false;
            }
        }
        
        //Variables.shootRetractTime -= timeDelta;
        /*if (Variables.shootRetractTime == 2.0) {
            //Variables.shootRetractTime = 0.0; //Still needs testing before added, keep though
            Variables.catapultSolenoid1.set(DoubleSolenoid.Value.kReverse);
            Variables.catapultSolenoid2.set(DoubleSolenoid.Value.kReverse);
            }*/
        }
    public static void miscControls(){
        //Up and down for pickup
        if(Variables.rightStick.getRawButton(3)){
            if (!Variables.pickupButtonTime){
                //first action
                Variables.pickupSolenoid.set(true);
                Variables.pickupMotor.set(Variables.loaderSpeed);
                System.out.println("First action");
                Variables.pickupButtonTime = true;   
            } else {
                //second action
                Variables.pickupSolenoid.set(false);
                System.out.println("Second action");
                Variables.pickupButtonTime = false;
            }
        }
        /*****************************************************************/
        //Manual Stop wheels
        if (Variables.rightStick.getRawButton(4)){
            Variables.pickupMotor.set(0);
        }
        /*****************************************************************/
        //Forward and Backword motors
        if(Variables.rightStick.getRawButton(5)){
            if(!Variables.pickupMotorDir){
                //First Action
                Variables.pickupMotor.set(Variables.loaderSpeed);
                
                Variables.pickupMotorDir = true;
            } else {
                //Second Action
                Variables.pickupMotor.set(Variables.loaderSpeed * -1);
                
                Variables.pickupMotorDir = false;
            }
        }
        /*****************************************************************/
        //Unload motors
        if(Variables.rightStick.getRawButton(2)) {
            Variables.pickupMotor.set(Variables.loaderSpeed * -1);
        }
    
    }
    
    }
