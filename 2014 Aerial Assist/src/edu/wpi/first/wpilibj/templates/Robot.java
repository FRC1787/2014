/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.can.*;
import edu.wpi.first.wpilibj.Dashboard;
import edu.wpi.first.wpilibj.templates.vision.VisionRobot;

//This is the main class for the entire Robot!
public class Robot extends VisionRobot {

    //Jaguar Motor Controlers
    public static CANJaguar leftMotor1;
    public static CANJaguar leftMotor2;
    public static CANJaguar rightMotor1;
    public static CANJaguar rightMotor2;
    public static CANJaguar pickupMotor;

    //Joysticks
    public static Joystick leftstick = new Joystick(1);
    public static Joystick rightstick = new Joystick(2);
    
    //Robot drive controller
    public static RobotDrive robotDrive;
   
    //sensors, encoder, accelerometer, and gyro perameters not final
    private Encoder leftEncoder = new Encoder(1, 2);
    private Encoder rightEncoder = new Encoder(3, 4);
    private Gyro gyro = new Gyro(1);
    private Accelerometer accelerometer = new Accelerometer(1);
    
    public static boolean isOperatorControlled;
    public static boolean isEnabled;
   
    public void robotInit(){
       System.out.println("robotInit has been called!");
    }
    
    public Robot() {
        try {
            leftMotor1 = new CANJaguar(2);
            leftMotor2 = new CANJaguar(3);
            rightMotor1 = new CANJaguar(4);
            rightMotor2 = new CANJaguar(5);
            pickupMotor = new CANJaguar(6);

            robotDrive = new RobotDrive(leftMotor1, leftMotor2, rightMotor1, rightMotor2);
            
            ShootingFunctions.shootingPiston();
            
        } catch (CANTimeoutException e) {
            e.printStackTrace();
        }
    }
    
    //autonomous Stuff
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
    
    //Autonomous Code. This is run ONCE each time the code is initialized for 10 seconds.
    public void autonomous() {
        while (isAutonomous() && isEnabled()) {
            
        } else {
            isOperatorControlled = true;
            isEnabled = true;
        }
        
    }
    

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {
        ShootingFunctions.compressor.start();

        DriveController.driveControls();
        
        //All Shooting code is in ShootingFunctions class
        
        //shifting
        if (leftstick.getRawButton(
                4) || rightstick.getRawButton(4)) {
            ShootingFunctions.gearShifter.set(DoubleSolenoid.Value.kForward);
        } else if (leftstick.getRawButton(
                5) || rightstick.getRawButton(5)) {
            ShootingFunctions.gearShifter.set(DoubleSolenoid.Value.kReverse);
        }
    }

    //This function is called each time the robot enters test mode.
    public void test() {
        System.out.println("The Robot has entered test mode!");
    }
}
