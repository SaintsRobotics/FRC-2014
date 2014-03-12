package com.saintsrobotics.frc;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Joystick;

/**
 * The joystick control for the robot.
 * @author Saints Robotics
 */
public class JoystickControl implements IRobotComponent
{
    // Drive
//    public static final int DRIVE_LEFT_MOTOR_1_PORT = 1;
//    public static final int DRIVE_LEFT_MOTOR_2_PORT = 2;
//    
//    public static final int DRIVE_RIGHT_MOTOR_1_PORT = 3;
//    public static final int DRIVE_RIGHT_MOTOR_2_PORT = 4;
    
    
    private final Joystick driveJoystick;
    private final Joystick operatorJoystick;
    
    
    public static final boolean DRIVE_SQUARED_INPUTS = true;
    
    // Pickup
//    public static final int PICKUP_MOTOR_PORT = 9;
//    public static final double PICKUP_MOTOR_POWER = 1.0;
//    
//    public static final int PICKUP_DIGITAL_INPUT_PORT = 2;
    
    // Shooter
//    public static final int SHOOTER_RELAY_PORT = 1;
//    public static final Relay.Direction SHOOTER_RELAY_DIRECTION =
//            Relay.Direction.kForward;
//    
//    public static final int SHOOTER_DIGITAL_INPUT_PORT = 1;
    
    // Gear shifter
//    public static final int GEAR_SHIFTER_MOTOR_PORT = 10;
//    public static final double GEAR_SHIFTER_MOTOR_POWER = 1.0;
    
    // Joystick control
    public static final double XBOX_DEAD_ZONE = 0.13;
    
    public static final double DRIVE_JOYSTICK_DEAD_ZONE = XBOX_DEAD_ZONE;
    public static final int DRIVE_JOYSTICK_PORT = 1;
    
    public static final int OPERATOR_JOYSTICK_PORT = 2;
    
    public static final int DRIVE_SLOW_MODE_FACTOR = 3;
    public static final boolean DRIVE_INVERTED = true;
    
    public static final XboxAxis ARCADE_MOVE_JOYSTICK_AXIS = XboxAxis.LEFT_THUMB_Y;
    public static final boolean ARCADE_MOVE_JOYSTICK_INVERTED = DRIVE_INVERTED;
    
    public static final XboxAxis ARCADE_ROTATE_JOYSTICK_AXIS = XboxAxis.RIGHT_THUMB_X;
    public static final boolean ARCADE_ROTATE_JOYSTICK_INVERTED = true;
    
    public static final XboxAxis TANK_LEFT_JOYSTICK_AXIS = XboxAxis.LEFT_THUMB_Y;
    public static final boolean TANK_LEFT_JOYSTICK_INVERTED = DRIVE_INVERTED;
    
    public static final XboxAxis TANK_RIGHT_JOYSTICK_AXIS = XboxAxis.RIGHT_THUMB_Y;
    public static final boolean TANK_RIGHT_JOYSTICK_INVERTED = DRIVE_INVERTED;
    
    // Button mappings
    public static final XboxButton SLOW_MODE_BUTTON = XboxButton.LEFT_BUMPER;
    public static final XboxButton SHIFT_GEAR_DOWN_BUTTON = XboxButton.RIGHT_BUMPER;
    public static final XboxButton SHIFT_GEAR_UP_BUTTON = XboxButton.Y;
    
    public static final XboxButton PICKUP_BUTTON = XboxButton.RIGHT_BUMPER;
    public static final XboxButton RELEASE_PICKUP_BUTTON = XboxButton.LEFT_BUMPER;
    public static final XboxButton SHOOT_WITH_RESET_BUTTON = XboxButton.A;
    public static final XboxButton SHOOT_WITHOUT_RESET_BUTTON = XboxButton.X;
    public static final XboxButton STOP_SHOOT_BUTTON = XboxButton.B;
    
    private ControlMode controlMode;

    private double arcadeThrottleValue = 0.0;
    private double arcadeTurnValue = 0.0;
    private boolean slowButton = false;
    private boolean winchButton = false;
    private boolean raiseButton = false;
    private boolean pickupButton = false;
    private boolean releasePickupButton = false;
    private boolean winchMomentButton;
    private boolean winchStopButton;
    
    public void robotDisable()
    {
    }

    public void robotEnable()
    {
        arcadeThrottleValue = 0.0;
        arcadeTurnValue = 0.0;
        winchButton = false;
    }

    public void act()
    {
        arcadeThrottleValue = driveJoystick.getRawAxis(ARCADE_MOVE_JOYSTICK_AXIS.value);
        arcadeTurnValue = driveJoystick.getRawAxis(ARCADE_ROTATE_JOYSTICK_AXIS.value);
        
        slowButton = driveJoystick.getRawButton(SLOW_MODE_BUTTON.value);
        curveTurnValues();
        deadZone();
        
        if (slowButton)
        {
            //slowDriveValues();
            DriverStationComm.printMessage(DriverStationLCD.Line.kUser1, 4, "Slow Mode: ON");
        }
        else
        {
            DriverStationComm.printMessage(DriverStationLCD.Line.kUser1, 4, "Slow Mode: OFF");
        }
        
        
        
        
        winchButton = operatorJoystick.getRawButton(SHOOT_WITH_RESET_BUTTON.value);
        winchMomentButton = operatorJoystick.getRawButton(SHOOT_WITHOUT_RESET_BUTTON.value);
        winchStopButton = operatorJoystick.getRawButton(STOP_SHOOT_BUTTON.value);
        
        releasePickupButton = operatorJoystick.getRawButton(RELEASE_PICKUP_BUTTON.value);
        pickupButton = operatorJoystick.getRawButton(PICKUP_BUTTON.value);
    }

    private void deadZone()
    {    
        if (Math.abs(arcadeThrottleValue) < DRIVE_JOYSTICK_DEAD_ZONE)
        {
            arcadeThrottleValue = 0;
        }
        if (Math.abs(arcadeTurnValue) < DRIVE_JOYSTICK_DEAD_ZONE)
        {
            arcadeTurnValue = 0;
        }
    }

    public void robotAuton()
    {
    }
    
    public static class ControlMode
    {
        public final int value;
        
        public static final ControlMode arcadeDrive = new ControlMode(0);

        private ControlMode(int value)
        {
            this.value = value;
        }
    }
    
    public JoystickControl()
    {
        driveJoystick = new Joystick(DRIVE_JOYSTICK_PORT);
        operatorJoystick = new Joystick(OPERATOR_JOYSTICK_PORT);
        
        
        // Default driving mode
        controlMode = ControlMode.arcadeDrive;
    }
    
        
    private void curveTurnValues()
    {
        arcadeTurnValue = 0.5 * MathUtils.pow(arcadeTurnValue, 3) + 0.5 * arcadeTurnValue;
    }
        
    public double[] getArcadeValues()
    {
        return new double[]{ arcadeThrottleValue, arcadeTurnValue };
    }
    
    public boolean getPickupButton()
    {
        return pickupButton;
    }
    
    public boolean getReleasePickupButton()
    {
        return releasePickupButton;
    }
       
    public boolean getSlowButton()
    {
        return slowButton;
    }
    
    public boolean getWinchButton()
    {
        return winchButton;
    }
    
    public boolean getWinchMomentButton()
    {
        return winchMomentButton;
    }
    
    public boolean getWinchStopButton()
    {
        return winchStopButton;
    }
}