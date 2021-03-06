/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ShootingWithAButton extends CommandBase {
  /**
   * Creates a new ShootingWithAButton.
   */
  private Shooter cShoot;
  private Joystick cJoy;
  private boolean Toggle = false;
  private boolean PressOnce = false;
  public ShootingWithAButton(Shooter in, Joystick inn) {
    cShoot = in;
    cJoy = inn;
    SmartDashboard.putBoolean("Shooter On", false);
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(cShoot);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(cJoy.getRawButton(1)){
      if(!PressOnce){
        PressOnce = true;
        Toggle = !Toggle;
      }
    }
    else{
      PressOnce = false;
    }

    if(Toggle){ 
      cShoot.ShootPID();
    }
    else{
      cShoot.Stop();
    }
    SmartDashboard.putBoolean("Shooter On", Toggle);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
