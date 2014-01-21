/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.camera.*;
import edu.wpi.first.wpilibj.can.*;
import edu.wpi.first.wpilibj.image.CriteriaCollection;
import edu.wpi.first.wpilibj.image.NIVision;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Dashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
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
    private RobotDrive robotDrive;
   
    //sensors, encoder, accelerometer, and gyro perameters not final
    private Encoder leftEncoder = new Encoder(1, 2);
    private Encoder rightEncoder = new Encoder(3, 4);
    private Gyro gyro = new Gyro(1);
    private Accelerometer accelerometer = new Accelerometer(1);
    
    //drive controller
    public PidDriveController pidDriveBase;
    
    public void robotInit(){
       System.out.println("robotInit i");
    }
    
    public Robot() {
        try {
            leftMotor1 = new CANJaguar(2);
            leftMotor2 = new CANJaguar(3);
            rightMotor1 = new CANJaguar(4);
            rightMotor2 = new CANJaguar(5);
            pickupMotor = new CANJaguar(6);

            robotDrive = new RobotDrive(leftMotor1, leftMotor2, rightMotor1, rightMotor2);
            //robotDrive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true); //inverts motor direction

            ShootingFunctions.shootingPiston();
            
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
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
        pidDriveBase.queueTurnAngle(180.0, 3.0);

        while (isAutonomous() && isEnabled()) {
            if (pidDriveBase.getControlState() == pidDriveBase.DRIVE) {
                pidDriveBase.updateDriveDistance();
            } else if (pidDriveBase.getControlState() == pidDriveBase.TURN) {
                pidDriveBase.updateTurnAngle();
            } else {
                break;
            }
        }

        pidDriveBase.removeWaypoint();
    }
    

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {
        ShootingFunctions.compressor.start();
        double shootRetractTime = 0.0;

        double lastTime, timeDelta = 0.0;
        double time = Timer.getFPGATimestamp();
        while (isOperatorControl() && isEnabled()) {
            lastTime = time;
            time = Timer.getFPGATimestamp();
            timeDelta = time - lastTime;

            if (rightstick.getMagnitude() > leftstick.getMagnitude()) {
                robotDrive.arcadeDrive(rightstick.getY() * 0.25, rightstick.getX() * 0.25, true);
            } else {
                robotDrive.arcadeDrive(-leftstick.getY() * 0.25, leftstick.getX() * 0.25, true);
            }
            //pickup
            if (leftstick.getRawButton(3) || rightstick.getRawButton(3)) {
                pickupMotor.set(1);
            } else {
                pickupMotor.set(0);
            }
            if (leftstick.getRawButton(2) || rightstick.getRawButton(2)) {
                ShootingFunctions.pickupSolenoid.set(true);
            } else if (leftstick.getRawButton(6) || rightstick.getRawButton(6)) {
                ShootingFunctions.pickupSolenoid.set(false);
            }
        }
        
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

    /**
     * This function is called once each time the robot enters test mode.
     */
    public void test() {
    }
}
