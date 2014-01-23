package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;

public class Variables {
   
     //Jaguar Motor Controlers
    public static CANJaguar leftMotor1;
    public static CANJaguar leftMotor2;
    public static CANJaguar rightMotor1;
    public static CANJaguar rightMotor2;
    public static CANJaguar pickupMotor;
    
    //Joysticks
    public static Joystick leftStick = new Joystick(1);
    public static Joystick rightStick = new Joystick(2);
    
    //Robot drive controller
    public static RobotDrive robotDrive;
    
    //sensors, encoder, accelerometer, and gyro perameters not final
    public static Encoder leftEncoder = new Encoder(1, 2);
    public static Encoder rightEncoder = new Encoder(3, 4);
    public static Gyro gyro = new Gyro(1);
    public static Accelerometer accelerometer = new Accelerometer(1);
    public static boolean isOperatorControlled;
    public static boolean isEnabled;
    
    //Pnumatics and Compressors
    public static DoubleSolenoid gearShifter = new DoubleSolenoid(1, 2);
    public static Compressor compressor = new Compressor(1, 1);
    public static Solenoid pickupSolenoid = new Solenoid(1);
    public static DoubleSolenoid catapultSolenoid1 = new DoubleSolenoid(3, 4);
    public static DoubleSolenoid catapultSolenoid2 = new DoubleSolenoid(4, 5);
    
    //shifter position. false = foreward, true = reverse.
    public static boolean shifterPosition;
    
    //Public drive speed for robot. Modifiable from the driver station.
    public static double driveSpeed = 0.25;
}
