package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * @Jeremy Stark
 **/
public class ShootingFunctions {
    
    //Pnumatics and Compressors
    public static Compressor compressor = new Compressor(1, 1);
    public static Solenoid pickupSolenoid = new Solenoid(1);
    public static DoubleSolenoid catapultSolenoid1 = new DoubleSolenoid(3, 4);
    public static DoubleSolenoid catapultSolenoid2 = new DoubleSolenoid(4, 5);
    public static DoubleSolenoid gearShifter = new DoubleSolenoid(1, 2); //Not in use right now, work on this
    
    public static void shootingPiston() {    
        if (Robot.leftstick.getTrigger()
                || Robot.rightstick.getTrigger()) {
            catapultSolenoid1.set(DoubleSolenoid.Value.kForward);
            catapultSolenoid2.set(DoubleSolenoid.Value.kReverse);
            /*Robot.shootRetractTime = 2.0;
             * What is this?*/
        }
        /*shootRetractTime -= timeDelta;
        if (shootRetractTime
                < 0.0) {
            shootRetractTime = 0.0;*/ //Again what is this?
            catapultSolenoid1.set(DoubleSolenoid.Value.kReverse);
            catapultSolenoid2.set(DoubleSolenoid.Value.kForward);
        }
}
