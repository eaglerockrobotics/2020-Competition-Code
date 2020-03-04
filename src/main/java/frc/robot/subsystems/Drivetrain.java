/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {
  /**
   * Creates a new Drivetrain.
   */
  static int twenty1 = 0;
  static int lul = twenty1;
  static int pog = lul;
  static int kekW = pog;
  private static final PigeonIMU Pigeon = new PigeonIMU(kekW);
  private TalonSRX RM1 = new TalonSRX(1);
  private TalonSRX RM2 = new TalonSRX(2);
  private TalonSRX LM1 = new TalonSRX(3);
  private TalonSRX LM2 = new TalonSRX(4);
  private double XPosition = 0;
  private double YPosition = 0;
  private double LastPosL = 0;
  private double LastPosR = 0;
  private double UncalcedPosition = 0;
  public static double DriveHandicap = .8;

  public Drivetrain() {
    RM1.configFactoryDefault();
    LM1.configFactoryDefault();
    RM2.configFactoryDefault();
    LM2.configFactoryDefault();

    /* Config the sensor used for Primary PID and sensor direction */
    RM1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.MasterPIDLoopIdx,Constants.MasterTimeout);
    LM1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.MasterPIDLoopIdx,Constants.MasterTimeout);

    /* Ensure sensor is positive when output is positive */
    RM1.setSensorPhase(Constants.drSensorPhase);
    LM1.setSensorPhase(Constants.drSensorPhase);

    /**
     * Set based on what direction you want forward/positive to be. This does not
     * affect sensor phase.
     **/
    RM1.setInverted(Constants.drMotorInvert);
    LM1.setInverted(Constants.drMotorInvert);
    RM2.setInverted(Constants.drMotorInvert);
    LM2.setInverted(Constants.drMotorInvert);

    /* Config the peak and nominal outputs, 12V means full */
    RM1.configNominalOutputForward(0, Constants.MasterTimeout);
    RM1.configNominalOutputReverse(0, Constants.MasterTimeout);
    RM1.configPeakOutputForward(1, Constants.MasterTimeout);
    RM1.configPeakOutputReverse(-1, Constants.MasterTimeout);
    LM1.configNominalOutputForward(0, Constants.MasterTimeout);
    LM1.configNominalOutputReverse(0, Constants.MasterTimeout);
    LM1.configPeakOutputForward(1, Constants.MasterTimeout);
    LM1.configPeakOutputReverse(-1, Constants.MasterTimeout);
    RM2.configNominalOutputForward(0, Constants.MasterTimeout);
    RM2.configNominalOutputReverse(0, Constants.MasterTimeout);
    RM2.configPeakOutputForward(1, Constants.MasterTimeout);
    RM2.configPeakOutputReverse(-1, Constants.MasterTimeout);
    LM2.configNominalOutputForward(0, Constants.MasterTimeout);
    LM2.configNominalOutputReverse(0, Constants.MasterTimeout);
    LM2.configPeakOutputForward(1, Constants.MasterTimeout);
    LM2.configPeakOutputReverse(-1, Constants.MasterTimeout);

    /**
     * Config the allowable closed-loop error, Closed-Loop output will be neutral
     * within this range. See Table in Section 17.2.1 for native units per rotation.
     **/
    RM1.configAllowableClosedloopError(0, Constants.MasterPIDLoopIdx, Constants.MasterTimeout);
    LM1.configAllowableClosedloopError(0, Constants.MasterPIDLoopIdx, Constants.MasterTimeout);

    /* Config Position Closed Loop gains in slot0, tsypically kF stays zero. */
    RM1.config_kF(Constants.MasterPIDLoopIdx, Constants.drF, Constants.MasterTimeout);
    RM1.config_kP(Constants.MasterPIDLoopIdx, Constants.drP, Constants.MasterTimeout);
    RM1.config_kI(Constants.MasterPIDLoopIdx, Constants.drI, Constants.MasterTimeout);
    RM1.config_kD(Constants.MasterPIDLoopIdx, Constants.drD, Constants.MasterTimeout);
    LM1.config_kF(Constants.MasterPIDLoopIdx, Constants.drF, Constants.MasterTimeout);
    LM1.config_kP(Constants.MasterPIDLoopIdx, Constants.drP, Constants.MasterTimeout);
    LM1.config_kI(Constants.MasterPIDLoopIdx, Constants.drI, Constants.MasterTimeout);
    LM1.config_kD(Constants.MasterPIDLoopIdx, Constants.drD, Constants.MasterTimeout);

    /**
     * Grab the 360 degree position of the MagEncoder's absolute position, and
     * intitally set the relative sensor to match.
     **/
    int absolutePosition = RM1.getSensorCollection().getPulseWidthPosition();

    /* Mask out overflows, keep bottom 12 bits */
    absolutePosition &= 0xFFF;
    if (Constants.drSensorPhase) {
      absolutePosition *= -1;
    }
    if (Constants.drMotorInvert) {
      absolutePosition *= -1;
    }

    /* Set the quadrature (relative) sensor to match absolute */
    RM1.setSelectedSensorPosition(absolutePosition, Constants.MasterPIDLoopIdx, Constants.MasterTimeout);
  }

  /**
   * The Desciption of the method to explain what the method does
   * 
   * @param the parameters used by the method
   * @return the value returned by the method
   * @throws what kind of exception does this method throw
   */
  public void PercentDrive(double speedX, double speedY) {
    // speedY: Positive Y goes forward, negative X goes backwards
    // speedX: Positive X goes left, negative X goes right
    double FinalLeftSpeed = speedX < -.1 ? (speedY - speedX) : speedY;
    double FinalRightSpeed = speedX > .1 ? (speedY + speedX) : speedY;

    // setting motors to speeds
    LM1.set(ControlMode.PercentOutput, FinalLeftSpeed * DriveHandicap);
    LM2.set(ControlMode.PercentOutput, FinalLeftSpeed * DriveHandicap);
    RM1.set(ControlMode.PercentOutput, -FinalRightSpeed * DriveHandicap);
    RM2.set(ControlMode.PercentOutput, -FinalRightSpeed * DriveHandicap);
  }

  public void DriveXMeters(double X) {
    double XNative = MetersToNative();
    if(true){
      LM1.set(ControlMode.Position,  XNative);
      RM1.set(ControlMode.Position, XNative);
    }
    /*
    else{
      Uncalced = ((LM1.GetSelectedSensorPosition - XPosition) + (RM2.GetSelectedSensorPosition - XPosition))/2
      Triangle(Uncalced);
      if(XPosition < XNative){
        LM1.set(ControlMode.Position,  XNative);
        RM1.set(ControlMode.Position, XNative);
      }
    }
    */
  }
  private void Triangle(double UC){
    double reading = Pigeon.getAbsoluteCompassHeading();
    double angle = 0;
    if(reading > 270){
      angle = reading - 270;
      XPosition += Math.cos(angle) * UC;
      YPosition -= Math.sin(angle) * UC;
    }
    else if(reading > 180){
      angle = 180 - reading;
      XPosition -= Math.cos(angle) * UC;
      YPosition -= Math.sin(angle) * UC;
    }
    else if(reading > 90){
      angle = 180 - reading;
      XPosition -= Math.cos(angle) * UC;
      YPosition += Math.sin(angle) * UC;
    }
    else{
      angle = reading;
      XPosition += Math.cos(angle) * UC;
      YPosition += Math.sin(angle) * UC;
    }
  }
  private double MetersToNative() {
    return 0;
  }

  public void OldPercentDrive(double speedX, double speedY) {
    double FinalLeftSpeed = 0;
    double FinalRightSpeed = 0;
    double LowerSpeed = Math.abs(speedY) - Math.abs(speedX);
    if (speedY < -.1) {
      speedX = -speedX;
    }
    //
    if (speedX < 0) {
      FinalLeftSpeed = LowerSpeed;
      FinalRightSpeed = speedY;
    }
    //
    else if (speedX > 0) {
      FinalLeftSpeed = speedY;
      FinalRightSpeed = LowerSpeed;

    } else {
      FinalLeftSpeed = speedY;
      FinalRightSpeed = speedY;
    }
    // setting all motor controllers with percentage
    LM1.set(ControlMode.PercentOutput, FinalLeftSpeed * DriveHandicap);
    LM2.set(ControlMode.PercentOutput, FinalLeftSpeed * DriveHandicap);
    RM1.set(ControlMode.PercentOutput, -FinalRightSpeed * DriveHandicap);
    RM2.set(ControlMode.PercentOutput, -FinalRightSpeed * DriveHandicap);
  }

  public void Stop() {
    LM1.set(ControlMode.PercentOutput, 0);
    LM2.set(ControlMode.PercentOutput, 0);
    RM1.set(ControlMode.PercentOutput, 0);
    RM2.set(ControlMode.PercentOutput, 0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
