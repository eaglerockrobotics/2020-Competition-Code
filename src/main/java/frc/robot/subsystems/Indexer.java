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

public class Indexer extends SubsystemBase {
  /**
   * Creates a new Indexer.
   */
  VictorSPX one = new VictorSPX(10);
  VictorSPX two = new VictorSPX(11);
  public Indexer() {

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
    two.set(ControlMode.PercentOutput, -in);
  }
  public void ConstantDrive(boolean reverse){
    if(reverse){
      one.set(ControlMode.PercentOutput, -Constants.IndexerFeedForward);
      two.set(ControlMode.PercentOutput, Constants.IndexerFeedForward);
    }
    else{
      one.set(ControlMode.PercentOutput, Constants.IndexerFeedForward);
      two.set(ControlMode.PercentOutput, -Constants.IndexerFeedForward);
    }

  }

public void Stop() {
  one.set(ControlMode.PercentOutput, 0);
  two.set(ControlMode.PercentOutput, 0);
}
}
