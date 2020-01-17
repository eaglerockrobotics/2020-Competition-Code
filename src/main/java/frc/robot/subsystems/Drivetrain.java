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

public class Drivetrain extends SubsystemBase {
  /**
   * Creates a new Drivetrain.
   */
  private VictorSPX LM1 = new VictorSPX(1);
  private VictorSPX LM2 = new VictorSPX(2);
  private VictorSPX RM1 = new VictorSPX(3);
  private VictorSPX RM2 = new VictorSPX(4);
  public static double DriveHandicap = .8;
  public Drivetrain() {
  }

  public void PercentDrive(double speedX, double speedY){
    double FinalLeftSpeed = 0;
    double FinalRightSpeed = 0;

    FinalLeftSpeed = speedX > -.2 ? (speedY - speedX) : speedY;
    FinalRightSpeed = speedX < .2 ? -(speedY - speedX) : -speedY;

    //setting all motor controllers with percentage
    LM1.set(ControlMode.PercentOutput, FinalLeftSpeed * DriveHandicap);
    LM2.set(ControlMode.PercentOutput, FinalLeftSpeed * DriveHandicap);
    RM1.set(ControlMode.PercentOutput, FinalRightSpeed * DriveHandicap);
    RM2.set(ControlMode.PercentOutput, FinalRightSpeed * DriveHandicap);
  }

  public void Stop(){
    LM1.set(ControlMode.PercentOutput,0);
    LM2.set(ControlMode.PercentOutput,0);
    RM1.set(ControlMode.PercentOutput,0);
    RM2.set(ControlMode.PercentOutput,0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
