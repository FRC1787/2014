/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends SimpleRobot {

    /*
     * Jaquar Motor Controllers
     */
    private CANJaguar leftMotor1;
    private CANJaguar leftMotor2;
    private CANJaguar rightMotor1;
    private CANJaguar rightMotor2;
    private CANJaguar pickupMotor;

    /*
     * joysticks
     */
    private Joystick leftstick = new Joystick(1);
    private Joystick rightstick = new Joystick(2);
    /*
     * pneumatics
     * comressor and solenoid parameters are NOT final
     */
    private Compressor compressor = new Compressor(1, 1);
    private Solenoid pickupSolenoid = new Solenoid(1);
    private Solenoid catapultSolenoid1 = new Solenoid(2);
    private Solenoid catapultSolenoid2 = new Solenoid(3);
    private DoubleSolenoid gearShifter = new DoubleSolenoid(1,2);
    /*
     * RobotDrive controller
     */
    private RobotDrive robotDrive;
    
    /*
     * sensors
     * encoder, accelerometer, and gyro perameters not final
     */
private Encoder leftEncoder = new Encoder(1,2);
private Encoder rightEncoder = new Encoder(3,4);
private Gyro gyro = new Gyro(1);
private Accelerometer accelerometer = new Accelerometer(1);
    /**
     * This function is called once each time the robot enters autonomous mode.
     */
    public void autonomous() {
        try {
            leftMotor1 = new CANJaguar(2);
            leftMotor2 = new CANJaguar(3);
            rightMotor1 = new CANJaguar(4);
            rightMotor2 = new CANJaguar(5);
            pickupMotor = new CANJaguar(6);

            robotDrive = new RobotDrive(leftMotor1, leftMotor2, rightMotor1, rightMotor2);
            //robotDrive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true); //inverts motor direction

        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {
        compressor.start();
        while (isOperatorControl() && isEnabled()) {
            if (rightstick.getMagnitude() > leftstick.getMagnitude()) {
                robotDrive.arcadeDrive(rightstick.getY() * 0.25, rightstick.getX() * 0.25, true);
            } else {
                robotDrive.arcadeDrive(-leftstick.getY() * 0.25, leftstick.getX() * 0.25, true);
            }
        }
    }

    /**
     * This function is called once each time the robot enters test mode.
     */
    public void test() {
    }
}
