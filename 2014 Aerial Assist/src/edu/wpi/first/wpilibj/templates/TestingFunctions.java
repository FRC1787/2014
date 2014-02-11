package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;

public class TestingFunctions {
    
    public static void buttonTest() {
        boolean testDrive;
        testDrive = Variables.rightStick.getButton(Joystick.ButtonType.kTrigger);
    }
    
}
