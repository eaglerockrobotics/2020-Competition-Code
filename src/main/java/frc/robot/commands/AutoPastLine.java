/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class AutoPastLine extends CommandBase {
  /**
   * Creates a new AutoPastLine.
   */
  Timer TwoSecondsToMove = new Timer();
  Drivetrain cDrive;
  boolean done = false;
  public AutoPastLine(Drivetrain in) {
    cDrive = in;
    addRequirements(cDrive);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    TwoSecondsToMove.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(TwoSecondsToMove.hasPeriodPassed(2)){
      cDrive.Stop();
      done = true;
    }
    else{
      cDrive.PercentDrive(0, .5);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return done;
  }
}
