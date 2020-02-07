/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

public class Testsystem extends SubsystemBase {
  WPI_TalonSRX Encoder = new WPI_TalonSRX(12);
  TalonSRXConfiguration defaults = new TalonSRXConfiguration();
  int P, I, D = 1;
  int integral, previous_error;
  int setspeed = 25;
  int error = 0;
  /**
   * Creates a new Testsystem.
   */
  public Testsystem() {
    //m_US = new AnalogInput(0);
    defaults.primaryPID.selectedFeedbackSensor = FeedbackDevice.CTRE_MagEncoder_Relative;
    defaults.slot0.integralZone = 5;
    Encoder.configAllSettings(defaults);
    Encoder.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,0,10);
  }
  public double[] SpinNScan(double speed){
    Encoder.set(ControlMode.PercentOutput,speed);
    double[] ret = {Encoder.getSelectedSensorPosition(), Encoder.getSelectedSensorVelocity()};
    return ret;
  }

  @Override
  public void periodic() {
    //Encoder.getSelectedSensorPosition();
    //Encoder.getSelectedSensorVelocity();
    
    /*double sensorValue = m_US.getVoltage();
    final double scaleFactor = 1/(5./1024.); //scale converting voltage to distance
    double distance = 5*sensorValue*scaleFactor; //convert the voltage to distance
    System.out.println(distance);//write the value to the LabVIEW DriverStation
    */
    // This method will be called once per scheduler run
  }
  public double PIDencoder(){
    error = setspeed - 0;// Error = Target - Actual
    this.integral += (error*.02); // Integral is increased by the error*time (which is .02 seconds using normal IterativeRobot)
     return P*error + I*this.integral;
  }
}
