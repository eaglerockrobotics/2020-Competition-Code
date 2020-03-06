/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */

public final class Constants {
    /**
     * Which PID slot to pull gains from. Starting 2018, you can choose from 0,1,2
     * or 3. Only the first two (0,1) are visible in web-based configuration.
     */
    public static final int MasterSlotIndex = 0;

    /**
     * Talon SRX/ Victor SPX will supported multiple (cascaded) PID loops. For now
     * we just want the primary one.
     */
    public static final int MasterPIDLoopIdx = 0;

    /**
     * Set to zero to skip waiting for confirmation, set to nonzero to wait and
     * report to DS if action fails.
     */
    public static final int MasterTimeout = 30;

    /**
     * PID Gains may have to be adjusted based on the responsiveness of control
     * loop. F: 1023 represents output value to Talon at 100%, 7200 represents
     * Velocity units at 100% output
     */
    public static final double shF = 1023 / 25000; // solve soon
    public static final double shP = 1;
    public static final double shI = 0;
    public static final double shD = 0;
    public static final int shIzone = 200;
    public static final double shPeakOutput = 1;

    public static final double drP = 1;
    public static final double drI = 0;
    public static final double drD = 0;
    public static final double drF = 1023 / 7200; // solve eventually
    public static final int drIzone = 200;
    public static final double drPeakOutput = 1;
    public static final boolean drSensorPhase = true;
    public static final boolean drMotorInvert = false;

    public static final double IndexerFeedForward = .5;
    public static final double IntakeFeedForward = .5;

    public static final int RightMotor1ID = 1;
    public static final int RightMotor2ID = 2;
    public static final int LeftMotor1ID = 3;
    public static final int LeftMotor2ID = 4;
    public static final int IntakeMotorID = 5;
    public static final int ShooterID = 6;
    public static final int IndexerMotor1ID = 7;
    public static final int IndexerMotor2ID = 8;
    public static final int LiftArmMotor1ID = 9;
    public static final int LiftArmMotor2ID = 10;
    

}
