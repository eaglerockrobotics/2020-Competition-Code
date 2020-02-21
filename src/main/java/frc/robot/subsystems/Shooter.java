/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
  Timer PIDCooldown = new Timer();
  boolean PIDReady = false;
  WPI_TalonSRX Encoder = new WPI_TalonSRX(6);
  TalonSRXConfiguration defaults = new TalonSRXConfiguration();
  final double TargetVelocity = -10000;
  double P = 1, I = 1, D = 1;
  double error,derivative,previous_error,integral,Answer,AnswerPercent;
  double speed = 0;
  
  public Shooter() {
    //Initialize Encoder
    defaults.primaryPID.selectedFeedbackSensor = FeedbackDevice.CTRE_MagEncoder_Relative;
    defaults.slot0.integralZone = 5;
    defaults.closedloopRamp = .1;
    Encoder.configAllSettings(defaults);
    Encoder.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,0,10);
    //Initializing SmartDashboard numbers
    SmartDashboard.putNumber("VelocityGraph",0);
    SmartDashboard.putNumber("CurrentVelocity",0);
    SmartDashboard.putNumber("Position?", 0);
    SmartDashboard.putNumber("P",0);
    SmartDashboard.putNumber("I",0);
    SmartDashboard.putNumber("D",0);
    SmartDashboard.putNumber("Answer",0);
    SmartDashboard.putNumber("Ultrasonic Voltage",0);
    SmartDashboard.putNumber("Time",0);
    //Start Timer, always on
    PIDCooldown.start();
  }
  /**
   * Starts shooting motor, continously applies percentage
   */
  public void ShootPercent(){
    speed = SmartDashboard.getNumber("Speed in Percentage", 0);
    Encoder.set(ControlMode.PercentOutput,speed);
    SmartDashboard.putNumber("VelocityGraph",Encoder.getSelectedSensorVelocity());
    SmartDashboard.putNumber("CurrentVelocity",Encoder.getSelectedSensorVelocity());
  }
    /**
   * Speeds up to a constant speed with Proportional control, after which able to shoot
   */
  public void ShootP(){
    SmartDashboard.putNumber("Time",PIDCooldown.get());
    if(PIDCooldown.hasPeriodPassed(.05)){
      speed = PPercent(TargetVelocity);
    } 
    SmartDashboard.putNumber("Speed",speed);
    Encoder.set(ControlMode.PercentOutput,speed);
    


    SmartDashboard.putNumber("VelocityGraph",Encoder.getSelectedSensorVelocity());
    SmartDashboard.putNumber("CurrentVelocity",Encoder.getSelectedSensorVelocity());
    SmartDashboard.putNumber("Position?", Encoder.getSelectedSensorPosition());
  }
      /**
   * Stops
   */
  public void Stop(){
    Encoder.set(ControlMode.PercentOutput, 0);
  }
  private double PPercent(double setspeed){
    error = setspeed - Encoder.getSelectedSensorVelocity();// Error = Target - Actual
    SmartDashboard.putNumber("P",error);
    Answer = P*error;
    SmartDashboard.putNumber("Answer",Answer);
    AnswerPercent = VelocityToPercent(Answer, setspeed);
    if(AnswerPercent > .8){
      AnswerPercent = .8;
    }
    if(AnswerPercent < -.8){
      AnswerPercent = -.8;
    }
    if(Answer < 0){
      AnswerPercent = -Math.abs(AnswerPercent);
    }
    return AnswerPercent;
  }
  private double VelocityToPercent(double in, double target){
    return (1 - (Math.abs(in - target)/target));
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
