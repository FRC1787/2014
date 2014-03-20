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
        
        Variables.isRunning = true;
                
        System.out.println("Shifter position set to false");
        Variables.shifterPosition = false;
        
        while(Variables.isRunning && isEnabled()){
        //System.out.println("Compressor started!");
        //Variables.compressor.enabled();
        //Variables.compressor.start();
        System.out.println("robotInit is running bool running");
        }
        
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
            
        } catch (CANTimeoutException e) {
            e.printStackTrace();
        }
    }

    public Robot() {
        while(isEnabled()){
            //Variables.compressor.enabled();
            //Variables.compressor.start();
            System.out.println("Robot() isEnabled while called and running");
            //if(!Variables.isRunning){
            //Variables.isRunning = true;
            //}
        }
    }
    
    //Autonomous Code. This is run ONCE each time the code is initialized for 10 seconds.
    public void autonomous() {
        System.out.println("The Robot has entered autonomous");
        
        Variables.compressor.enabled();
        Variables.compressor.start();
        
        Autonomous.autoDrive(); //Runs the Autonomous drive function
        Autonomous.autoShoot(); //Runs the Autonomous shoot function
    }

    //This function is called once the robot enters Operated Control mode.
    public void operatorControl() {
        System.out.println("The Robot has entered operatorControl");
        while (isOperatorControl() && isEnabled()) {

            //Compressor started
            Variables.compressor.enabled();
            Variables.compressor.start();
            // / Variables.compressor.getPressureSwitchValue();
            System.out.println(Variables.compressor.getPressureSwitchValue());
            
            //Shooting function
            ShootingFunctions.shootingPiston();
            
            //Driving Controlls
            Variables.robotDrive.arcadeDrive(Variables.rightStick);
            
            //Driver Controls
            DriveController.driveControls(); //This calls the Driver Controlls and let the operator drive
            DriveController.loaderControls(); //This calls the pickup function from the DriveController class
            //DriveController.shiftingControls(); //Calls the shifting function from the DriveController class
        }
    }

    //This function is called each time the robot enters test mode.
    public void test() {
        System.out.println("The Robot has entered test mode");
        Variables.compressor.enabled();
        Variables.compressor.start();
        
        TestingFunctions.buttonTest(); //This just runs the button test function in the TestinFuntions class
    }
}
    
