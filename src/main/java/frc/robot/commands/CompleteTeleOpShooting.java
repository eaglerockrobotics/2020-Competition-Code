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
import frc.robot.subsystems.Indexer;

public class CompleteTeleOpShooting extends CommandBase {
  // THE C MEANS CURRENT NOT CONSTANT LOL
  private Shooter cShoot;
  private Indexer cIndex;
  private Joystick cJoy;
  private boolean Toggle = false;
  private boolean PressOnce = false;

  /**
   * Creates a new CompleteTeleOpShooting.
   */
  public CompleteTeleOpShooting(Shooter in, Indexer inn, Joystick innn) {
    cShoot = in;
    cJoy = innn;
    SmartDashboard.putBoolean("Shooter On", false);
    SmartDashboard.putBoolean("Shooter Ready", false);
    SmartDashboard.putBoolean("Indexer On", false);
    addRequirements(cShoot);
    addRequirements(cIndex);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Emergencies only!
    if (cJoy.getRawButton(9) && cJoy.getRawButton(10)) {
      cIndex.PercentDrive(-.8);
      cShoot.ShootPercent(-.8);
    } 
    else {
      if (cJoy.getRawButton(1)) {
        if (!PressOnce) {
          PressOnce = true;
          Toggle = !Toggle;
        }
      } 
      else {
        PressOnce = false;
      }

      if (Toggle) {
        cShoot.ShootPID();
        if (cShoot.ReadyToFire()) {
          cIndex.ConstantDrive(false);
        } 
        else {
          cIndex.Stop();
        }
      } 
      else {
        cShoot.Stop();
        cIndex.Stop();
      }
    }
    SmartDashboard.putBoolean("Shooter On", Toggle);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    cShoot.Stop();
    cIndex.Stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
