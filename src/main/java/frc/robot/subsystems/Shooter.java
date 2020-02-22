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
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
  Timer PIDCooldown = new Timer();
  boolean PIDReady = false;
  WPI_TalonSRX Encoder = new WPI_TalonSRX(6);
  TalonSRXConfiguration defaults = new TalonSRXConfiguration();
  double TargetVelocity = -10000;
  double P = .5, I = 1, D = 1;
  double error,derivative,previous_error,integral,Answer,AnswerPercent;
  double speed = 0;
  PIDController WPI = new PIDController(1,1,0);
  
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
    SmartDashboard.putNumber("Position", 0);
    SmartDashboard.putNumber("P",0);
    SmartDashboard.putNumber("I",0);
    SmartDashboard.putNumber("D",0);
    SmartDashboard.putNumber("Answer",0);
    SmartDashboard.putNumber("WPI's Answer",0);
    SmartDashboard.putNumber("Ultrasonic Voltage",0);
    SmartDashboard.putNumber("Time",0);
    SmartDashboard.putNumber("Insert Target Velocity", 0);
    SmartDashboard.putNumber("To Percentage", 0);
    
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
  public void CheckTargetVelSmartdash(){
    if(SmartDashboard.getNumber("Insert Target Velocity", 0) != 0){
      TargetVelocity = SmartDashboard.getNumber("Insert Target Velocity", -10000);
    }
    SmartDashboard.putNumber("Target Velocity", TargetVelocity);
  }
    /**
   * Speeds up to a constant speed with Proportional control, after which able to shoot
   */
  public void ShootP(){
    SmartDashboard.putNumber("WPI's Answer",WPI.calculate(Encoder.getSelectedSensorVelocity(), TargetVelocity));
    SmartDashboard.putNumber("Time",PIDCooldown.get());
    speed = PIPercent(TargetVelocity);
    SmartDashboard.putNumber("Speed",speed);
    Encoder.set(ControlMode.Velocity,speed);
    SmartDashboard.putNumber("VelocityGraph",Encoder.getSelectedSensorVelocity());
    SmartDashboard.putNumber("CurrentVelocity",Encoder.getSelectedSensorVelocity());
    SmartDashboard.putNumber("Position", Encoder.getSelectedSensorPosition());
  }
      /**
   * Stops
   */
  public void Stop(){
    Encoder.set(ControlMode.PercentOutput, 0);
  }
  private double PIPercent(double setspeed){
    error = setspeed - Encoder.getSelectedSensorVelocity();// Error = Target - Actual
    SmartDashboard.putNumber("P",error);
    //this.integral += (error*.02); // Integral is increased by the error*time (which is .02 seconds using normal IterativeRobot)
    SmartDashboard.putNumber("I",this.integral);
    SmartDashboard.putNumber("D",0);
    Answer = P*error + I*this.integral;
    SmartDashboard.putNumber("Answer",Answer);
    AnswerPercent = VelocityToPercent(Answer, setspeed);
    /*
    if(Answer < 0){
      AnswerPercent = -Math.abs(AnswerPercent);
    }
    if(AnswerPercent > .8){
      AnswerPercent = .8;
    }
    if(AnswerPercent < -.8){
      AnswerPercent = -.8;
    }
    return AnswerPercent;
    */
    return Answer;
  }
  public void ResetPID(){
    this.integral = 0;
    this.error = 0;
    this.derivative = 0;
  }
  private double VelocityToPercent(double in, double target){
    double out = (in/target);
    SmartDashboard.putNumber("To Percentage", out);
    return out;
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
