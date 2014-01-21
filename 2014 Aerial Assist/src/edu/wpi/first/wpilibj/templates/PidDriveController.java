/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.can.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.Vector;

/**
 *
 * @author Developer
 */
public class PidDriveController {

    private CANJaguar leftMotor1;
    private CANJaguar leftMotor2;
    private CANJaguar rightMotor1;
    private CANJaguar rightMotor2;
    //private CANJaguarPair leftMotors;
    //private CANJaguarPair rightMotors;
    //private CANJaguarTurnBase turnBase;
    private Encoder leftEncoder;
    private Encoder rightEncoder;
    private Gyro gyro;
    //private PIDController leftPid;
    //private PIDController rightPid;
    //private PIDController anglePid;
    private static double p_drive = 0.03;
    private static double max_drive = 0.5;
    //private static final double Ki_drive = 0.0;
    //private static final double Kd_drive = 0.0;
    private static double p_turn = 0.1;
    private static double max_turn = 0.25;
    //private static final double Ki_turn = 0.0;
    //private static final double Kd_turn = 0.0;

    void initConstants(){
        
        SmartDashboard.putNumber("P Drive", p_drive);
        SmartDashboard.putNumber("Max Drive", max_drive);
        
        SmartDashboard.putNumber("P Turn", p_turn);
        SmartDashboard.putNumber("Max Turn", max_turn);
    }
    void updateConstants(){
        p_drive = SmartDashboard.getNumber("P Drive");
        max_drive = SmartDashboard.getNumber("Max Drive");
        
        p_turn = SmartDashboard.getNumber("P Turn");
        max_turn = SmartDashboard.getNumber("Max Turn");
    }
    void setMotorOutputs(double left, double right){
        try {
            leftMotor1.setX(left, (byte) 0x80);
        } catch (CANTimeoutException e) {
        }
        try {
            leftMotor2.setX(left, (byte) 0x80);
        } catch (CANTimeoutException e) {
        }
        try {
            rightMotor1.setX(-right, (byte) 0x80);
        } catch (CANTimeoutException e) {
        }
        try {
            rightMotor2.setX(-right, (byte) 0x80);
        } catch (CANTimeoutException e) {
        }
        try {
            CANJaguar.updateSyncGroup((byte) 0x80);
        } catch (CANTimeoutException e) {
        }
    }

    PidDriveController(CANJaguar l1, CANJaguar l2, CANJaguar r1, CANJaguar r2, Encoder lEncoder, Encoder rEncoder, Gyro g) {
        //leftMotors = new CANJaguarPair(l1, l2, false);
        //rightMotors = new CANJaguarPair(r1, r2, true);
        //turnBase = new CANJaguarTurnBase(l1, l2, r1, r2);

        leftMotor1 = l1;
        leftMotor2 = l2;
        rightMotor1 = r1;
        rightMotor2 = r2;

        leftEncoder = lEncoder;
        rightEncoder = rEncoder;

        gyro = g;

        //leftPid = new PIDController(Kp_drive, Ki_drive, Kd_drive, leftEncoder, leftMotors);
        //leftPid.enable();
        //leftPid.setOutputRange(-0.5, 0.5);
        //leftPid.setAbsoluteTolerance(3);
        //leftPid.disable();

//        rightPid = new PIDController(Kp_drive, Ki_drive, Kd_drive, rightEncoder, rightMotors);
//        rightPid.enable();
//        rightPid.setOutputRange(-0.5, 0.5);
//        rightPid.setAbsoluteTolerance(3);
//        rightPid.disable();

//        anglePid = new PIDController(Kp_turn, Ki_turn, Kd_turn, gyro, turnBase);
//        anglePid.enable();
//        anglePid.setOutputRange(-0.5, 0.5);
//        anglePid.setAbsoluteTolerance(1.5);
//        anglePid.disable();
        initConstants();
    }

    double clamp(double v, double low, double high) {
        if (v > high) {
            return high;
        }
        if (v < low) {
            return low;
        }
        return v;
    }
    public static final int STOPPED = 0;
    public static final int DRIVE = 1;
    public static final int TURN = 2;
    public static final int WAITING = 3;
    private static final double distanceThreshold = 4.0;
    private static final double angleThreshold = 2.0;

    class Waypoint {

        private int controlState = STOPPED;
        private double leftTarget = 0.0;
        private double rightTarget = 0.0;
        private double angleTarget = 0.0;
        private double endTime = 0.0;       //stores maxTime if this is not the first element in the waypoints vector
    }
    Vector waypoints = new Vector();

    public void removeWaypoint(){
        if(waypoints.isEmpty())
            return;
        
        waypoints.removeElementAt(0);
        setMotorOutputs(0.0, 0.0);
        
        if(!waypoints.isEmpty())
        {
            ((Waypoint) waypoints.firstElement()).endTime += Timer.getFPGATimestamp();
        }
    }
    public void clearWaypoints(){
        waypoints.removeAllElements();
        setMotorOutputs(0.0, 0.0);
    }
    public int getControlState(){
        if (waypoints.isEmpty()) {
            return STOPPED;
        }
        return ((Waypoint) waypoints.firstElement()).controlState;
    }
    void queueDriveDistance(double distance, double maxTime){
        Waypoint w = new Waypoint();
        w.controlState = DRIVE;
        w.leftTarget = leftEncoder.getDistance() + distance;
        w.rightTarget = rightEncoder.getDistance() + distance;
        w.endTime = waypoints.isEmpty() ? Timer.getFPGATimestamp() + maxTime : maxTime;
        waypoints.addElement(w);
    }
    boolean updateDriveDistance(){     //false indicates that the command has timed out.
        if (waypoints.isEmpty()) {
            return true;
        }
        Waypoint w = ((Waypoint) waypoints.firstElement());
        if (w.controlState != DRIVE) {
            return true;
        }
        
        updateConstants();

        double lOutput, rOutput;
        double leftError = w.leftTarget - leftEncoder.getDistance();
        double rightError = w.rightTarget - rightEncoder.getDistance();

        if (Math.abs(leftError) < distanceThreshold && Math.abs(rightError) < distanceThreshold) {
            setMotorOutputs(0, 0);
            removeWaypoint();
            return true;
        } else if (Timer.getFPGATimestamp() >= w.endTime) {
            setMotorOutputs(0, 0);
            removeWaypoint();
            return false;
        }

        lOutput = 0;
        rOutput = 0;
        if (Math.abs(leftError) > distanceThreshold) {
            lOutput = clamp(p_drive * leftError, -max_drive, max_drive);
        }
        if (Math.abs(rightError) > distanceThreshold) {
            rOutput = clamp(p_drive * rightError, -max_drive, max_drive);
        }

        setMotorOutputs(lOutput, rOutput);
        return true;
    }
    void queueTurnAngle(double angle, double maxTime) {
        Waypoint w = new Waypoint();
        w.controlState = TURN;
        w.angleTarget = gyro.getAngle() + angle;
        w.endTime = waypoints.isEmpty() ? Timer.getFPGATimestamp() + maxTime : maxTime;
        waypoints.addElement(w);
    }
    boolean updateTurnAngle() {     //false indicates that the command has timed out.
        if (waypoints.isEmpty()) {
            return true;
        }
        Waypoint w = ((Waypoint) waypoints.firstElement());
        if (w.controlState != TURN) {
            return true;
        }
        
        updateConstants();
        double error = w.angleTarget - gyro.getAngle();

        if (Math.abs(error) < angleThreshold) {
            setMotorOutputs(0, 0);
            removeWaypoint();
            return true;
        } else if (Timer.getFPGATimestamp() >= w.endTime) {
            setMotorOutputs(0, 0);
            removeWaypoint();
            return false;
        }

        double output = clamp(p_turn * error, -max_turn, max_turn);
        setMotorOutputs(output, -output);
        return true;
    }
    void queueWait(double seconds) {
        Waypoint w = new Waypoint();
        w.controlState = WAITING;
        w.endTime = waypoints.isEmpty() ? Timer.getFPGATimestamp() + seconds : seconds;
        waypoints.addElement(w);
    }
    void updateWait() {     //can't exactly time out...
        if (waypoints.isEmpty()) {
            return;
        }
        Waypoint w = ((Waypoint) waypoints.firstElement());
        if (w.controlState == WAITING && Timer.getFPGATimestamp() >= w.endTime) {
            removeWaypoint();
        }
    }
    public void arcadeDrive(double moveValue, double rotateValue, boolean squaredInputs) {
        double leftMotorSpeed;
        double rightMotorSpeed;

        moveValue = clamp(moveValue, -1.0, 1.0);
        rotateValue = clamp(rotateValue, -1.0, 1.0);

        if (squaredInputs) {
            // square the inputs (while preserving the sign) to increase fine control while permitting full power
            if (moveValue >= 0.0) {
                moveValue = (moveValue * moveValue);
            } else {
                moveValue = -(moveValue * moveValue);
            }
            if (rotateValue >= 0.0) {
                rotateValue = (rotateValue * rotateValue);
            } else {
                rotateValue = -(rotateValue * rotateValue);
            }
        }

        if (moveValue > 0.0) {
            if (rotateValue > 0.0) {
                leftMotorSpeed = moveValue - rotateValue;
                rightMotorSpeed = Math.max(moveValue, rotateValue);
            } else {
                leftMotorSpeed = Math.max(moveValue, -rotateValue);
                rightMotorSpeed = moveValue + rotateValue;
            }
        } else {
            if (rotateValue > 0.0) {
                leftMotorSpeed = -Math.max(-moveValue, rotateValue);
                rightMotorSpeed = moveValue + rotateValue;
            } else {
                leftMotorSpeed = moveValue - rotateValue;
                rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
            }
        }

        setMotorOutputs(leftMotorSpeed, rightMotorSpeed);
    }
}
