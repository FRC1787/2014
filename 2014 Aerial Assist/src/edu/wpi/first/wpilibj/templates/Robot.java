/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.can.*;
import edu.wpi.first.wpilibj.templates.vision.VisionRobot;

//This is the main class for the entire Robot!
public class Robot extends VisionRobot {

    public void robotInit() {
        System.out.println("The Robot has entered robotInit!");
        
        try {
            Variables.leftMotor1 = new CANJaguar(2);
            Variables.leftMotor2 = new CANJaguar(3);
            Variables.rightMotor1 = new CANJaguar(4);
            Variables.rightMotor2 = new CANJaguar(5);
            Variables.pickupMotor = new CANJaguar(6);
            //Motors set up
            Variables.robotDrive = new RobotDrive(Variables.leftMotor1, Variables.leftMotor2, Variables.rightMotor1, Variables.rightMotor2);            
            //Robot Drive called
            ShootingFunctions.shootingPiston();
            //Piston shooting function called
            if (false)
                throw new CANTimeoutException();
            
        } catch (CANTimeoutException e) {
            e.printStackTrace();
        }        
        Variables.shifterPosition = false;
        //Shifter position set to false
        Variables.compressor.start();
        //Compressor started!
    }

    public Robot() {
        
    }
    
    //Autonomous Code. This is run ONCE each time the code is initialized for 10 seconds.
    public void autonomous() {
        System.out.println("The Robot has entered autonomous");
        
        Autonomous.autoDrive();
        Autonomous.autoShoot();
    }

    //This function is called once the robot enters Operated Control mode.
    public void operatorControl() {
        System.out.println("The Robot has entered operatorControl");
        while (isOperatorControl() && isEnabled()) {
            //Shooting function
            
            //Driving Controlls
            Variables.robotDrive.arcadeDrive(Variables.rightStick);
            
            //Driver Controls
            DriveController.driveControls();
            DriveController.shiftingControls();
        }
    }

    //This function is called each time the robot enters test mode.
    public void test() {
        System.out.println("The Robot has entered test mode");
        
        TestingFunctions.buttonTest();
    }
}
