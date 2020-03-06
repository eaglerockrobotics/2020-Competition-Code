/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  /**
   * Creates a new Intake.
   */
  VictorSPX one = new VictorSPX(Constants.IntakeMotorID);
  public Intake() {

  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void PercentDrive(double in){
    if(in > .8){
      in = .8;
    }
    if(in < -.8){
      in = -.8;
    }
    one.set(ControlMode.PercentOutput, in);
  }
  public void ConstantDrive(boolean reverse){
    if(reverse){
      one.set(ControlMode.PercentOutput, -Constants.IntakeFeedForward);
    }
    else{
      one.set(ControlMode.PercentOutput, Constants.IntakeFeedForward);
    }

  }

public void Stop() {
  one.set(ControlMode.PercentOutput, 0);
}
}
