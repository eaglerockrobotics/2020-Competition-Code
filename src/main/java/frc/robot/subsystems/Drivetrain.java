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
  private VictorSPX RM1 = new VictorSPX(1);
  private VictorSPX RM2 = new VictorSPX(2);
  private VictorSPX LM1 = new VictorSPX(3);
  private VictorSPX LM2 = new VictorSPX(4);
  public static double DriveHandicap = .8;
  public Drivetrain() {
  }
  /**
  The Desciption of the method to explain what the method does
  @param the parameters used by the method
  @return the value returned by the method
  @throws what kind of exception does this method throw
*/
  public void PercentDrive(double speedX, double speedY){
    //speedY: Positive Y goes forward, negative X goes backwards
    //speedX: Positive X goes left, negative X goes right
    double FinalLeftSpeed = speedY;
    double FinalRightSpeed = speedY;
    if(speedX < -.1){
      /*FinalLeftSpeed = speedY - speedX;
      FinalLeftSpeed = -(speedY - speedX);
      */
      if (-.1 < speedY && speedY < .1) {

      FinalLeftSpeed = -speedX;
      FinalRightSpeed = speedX;
      }
      else{
        FinalLeftSpeed = speedY - speedX;
      }
    }
    else if(speedX > .1){
      if(-.1 < speedY && speedY < .1){
      FinalLeftSpeed = -speedX;
      FinalRightSpeed = speedX;
      }
      else{
        FinalRightSpeed = speedY + speedX;
      }
    }
    /*FinalLeftSpeed = speedX < -.1 ? (speedY - speedX) : speedY;
    FinalRightSpeed = speedX > .1 ? (speedY + speedX) : speedY;
    */

    //setting motors to speeds
    LM1.set(ControlMode.PercentOutput, FinalLeftSpeed * DriveHandicap);
    LM2.set(ControlMode.PercentOutput, FinalLeftSpeed * DriveHandicap);
    RM1.set(ControlMode.PercentOutput, -FinalRightSpeed * DriveHandicap);
    RM2.set(ControlMode.PercentOutput, -FinalRightSpeed * DriveHandicap);
  }
  public void OldPercentDrive(double speedX, double speedY){
    double FinalLeftSpeed = 0;
    double FinalRightSpeed = 0;
    double LowerSpeed = Math.abs(speedY) - Math.abs(speedX);
    if(speedY <-.1){
        speedX = -speedX;
    }
      //
      if(speedX < 0){
        FinalLeftSpeed = LowerSpeed;
        FinalRightSpeed = speedY;
      }
      //
      else if(speedX > 0){
        FinalLeftSpeed = speedY;
        FinalRightSpeed = LowerSpeed;
        
      }
      else{
        FinalLeftSpeed = speedY;
        FinalRightSpeed = speedY;
      }
    //setting all motor controllers with percentage
    LM1.set(ControlMode.PercentOutput, FinalLeftSpeed * DriveHandicap);
    LM2.set(ControlMode.PercentOutput, FinalLeftSpeed * DriveHandicap);
    RM1.set(ControlMode.PercentOutput, -FinalRightSpeed * DriveHandicap);
    RM2.set(ControlMode.PercentOutput, -FinalRightSpeed * DriveHandicap);
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
