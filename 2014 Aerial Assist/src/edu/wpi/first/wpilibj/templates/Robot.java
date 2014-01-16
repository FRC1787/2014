/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;

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
    Jaguar leftMotor1;
    Jaguar leftMotor2;
    Jaguar rightMotor1;
    Jaguar rightMotor2;
    Jaguar pickupMotor;

    /*
     * pnuematics
     */
    /**
     * This function is called once each time the robot enters autonomous mode.
     */
    public void autonomous() {
        leftMotor1 = new Jaguar(2);
        leftMotor2 = new Jaguar(3);
        rightMotor1 = new Jaguar(4);
        rightMotor2 = new Jaguar(5);
        pickupMotor = new Jaguar(6);
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {
    }

    /**
     * This function is called once each time the robot enters test mode.
     */
    public void test() {
    }
}
