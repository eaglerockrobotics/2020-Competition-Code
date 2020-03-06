/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.*;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.LiftArm;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  //subsystems && other physicals
  private Joystick DriveArmJoy = new Joystick(0);
  private Joystick MechJoy = new Joystick(0);
  private final Drivetrain mDt = new Drivetrain();
  private final Indexer mI = new Indexer();
  private final Shooter mS = new Shooter();
  private final LiftArm mA = new LiftArm();

  //Commands
  private final Command JoyDrive = new TeleopDrive(mDt, DriveArmJoy);
  private final Command FullShooter = new CompleteTeleOpShooting(mS, mI, MechJoy);
  private final Command TeleArm = new TeleOpLiftArm(mA, DriveArmJoy);
  private final Command AutoLine = new AutoPastLine(mDt);

  //Buttons and extras for Commands



  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
  }
  public Drivetrain getDrivetrain(){
    return mDt;
  }
  public Shooter getShooter(){
    return mS;
  }
  public Joystick getJoy(boolean driveOrMech){
    if(driveOrMech){
      return DriveArmJoy;
    }
    return MechJoy;
  }
  public Command getDrivetrainTeleop(){
    return JoyDrive;
  }
  public Command getFullShooterWithIndexer(){
    return FullShooter;
  }
  public Command getTeleArm(){
    return TeleArm;
  }
  public Command getSimpleAuto(){
    return AutoLine;
  }
}
