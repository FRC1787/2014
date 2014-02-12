package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;

public class TestingFunctions {
    
    /*This entire class is just for testing things, all functions that are put into this class
    are ment to be put into the testing function in the Robot class. If it is for something else,
    you can put random code is here as long as you get rid of it before the commit. Again it's just for testing.*/
    
    public static void buttonTest() {
        boolean testDrive;
        testDrive = Variables.rightStick.getButton(Joystick.ButtonType.kTrigger);
    }
    
}
