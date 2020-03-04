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

public class LiftArm extends SubsystemBase {
  VictorSPX Ar1 = new VictorSPX(7);
  VictorSPX Ar2 = new VictorSPX(8);

  public LiftArm() {
  }
  public void Lol(double output) {
    Ar1.set(ControlMode.PercentOutput, output/2);
    Ar2.set(ControlMode.PercentOutput, -output/2);
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
