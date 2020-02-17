/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
public class Testsystem extends SubsystemBase {
  AnalogInput ultra = new AnalogInput(0);
  AnalogInput badUltrasonic = new AnalogInput(1);
  /**
   * Creates a new Testsystem.
   */
  public Testsystem() {

  }
  public void VSenseNPut(){
    double badValue = badUltrasonic.getVoltage();
    double sensorValue = ultra.getVoltage();
    //final double scaleFactor = 1/(5./1024.); //scale converting voltage to distance
    //double distance = 5*sensorValue*scaleFactor; //convert the voltage to distance
    SmartDashboard.putNumber("Ultrasonic Voltage",sensorValue);//write the value to the LabVIEW DriverStation
    SmartDashboard.putNumber("Bad Ultrasonic VOltage",badValue);
  }
  @Override
  public void periodic() {
  }
}
