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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {


  TalonSRX Encoder = new TalonSRX(6);
  double TargetVelocity = -13500;
  int CurrentVelocity = 0;
  
  public Shooter() {
    //Initialize Encoder
    Encoder.configFactoryDefault();
    Encoder.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,Constants.MasterPIDLoopIdx,Constants.MasterTimeout);
    Encoder.setSensorPhase(false);
    Encoder.configNominalOutputForward(0, Constants.MasterTimeout);
		Encoder.configNominalOutputReverse(0, Constants.MasterTimeout);
		Encoder.configPeakOutputForward(.8, Constants.MasterTimeout);
    Encoder.configPeakOutputReverse(-.8, Constants.MasterTimeout);
    Encoder.config_kF(Constants.MasterPIDLoopIdx, Constants.shF, Constants.MasterTimeout);
		Encoder.config_kP(Constants.MasterPIDLoopIdx, Constants.shP, Constants.MasterTimeout);
		Encoder.config_kI(Constants.MasterPIDLoopIdx, Constants.shI, Constants.MasterTimeout);
    Encoder.config_kD(Constants.MasterPIDLoopIdx, Constants.shD, Constants.MasterTimeout);
    Encoder.configClosedloopRamp(.1, Constants.MasterTimeout);
    //Initializing SmartDashboard numbers
    SmartDashboard.putNumber("VelocityGraph",0);
    SmartDashboard.putNumber("CurrentVelocity",0);
    SmartDashboard.putNumber("Position", 0);
    SmartDashboard.putBoolean("Ready to shoot?", false);
  }
  /**
   * Starts shooting motor, continously applies percentage
   */
  public void ShootPercent(double speed){
    Encoder.set(ControlMode.PercentOutput,speed);
  }
    /**
   * Speeds up to a constant speed with Proportional control, after which able to shoot
   */
  public void ShootPID(){
    Encoder.set(ControlMode.Velocity,TargetVelocity);
    CurrentVelocity = Encoder.getSelectedSensorVelocity();
    SmartDashboard.putNumber("VelocityGraph",CurrentVelocity);
    SmartDashboard.putNumber("CurrentVelocity",CurrentVelocity);
    SmartDashboard.putNumber("Position", Encoder.getSelectedSensorPosition());
  }
      /**
   * Stops
   */
  public void Stop(){
    Encoder.set(ControlMode.PercentOutput, 0);
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public boolean ReadyToFire(){
    if(Math.abs(Encoder.getSelectedSensorVelocity() - TargetVelocity) < 500){
      SmartDashboard.putBoolean("Ready to shoot?", true);
      return true;
    }
    SmartDashboard.putBoolean("Ready to shoot?", false);
    return false;
  }
}
