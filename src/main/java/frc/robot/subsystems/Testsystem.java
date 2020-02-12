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
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

public class Testsystem extends SubsystemBase {
  WPI_TalonSRX Encoder = new WPI_TalonSRX(12);
  TalonSRXConfiguration defaults = new TalonSRXConfiguration();
  double P = 2, I = 1, D = 1;
  double error,derivative,previous_error,integral,Answer = 0;
  double speed = 0;
  /**
   * Creates a new Testsystem.
   */
  public Testsystem() {
    //m_US = new AnalogInput(0);
    defaults.primaryPID.selectedFeedbackSensor = FeedbackDevice.CTRE_MagEncoder_Relative;
    defaults.slot0.integralZone = 5;
    defaults.closedloopRamp = .1;
    Encoder.configAllSettings(defaults);
    Encoder.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,0,10);
    BuildSmartDashboard();
  }
  public void SpinNScan(){
    speed = SmartDashboard.getNumber("Speed in Percentage", 0);
    Encoder.set(ControlMode.PercentOutput,speed);
    SmartDashboard.putNumber("VelocityGraph",Encoder.getSelectedSensorVelocity());
    SmartDashboard.putNumber("CurrentVelocity",Encoder.getSelectedSensorVelocity());
  }
  public void SpinScanAndControl(){
    speed = PIDencoder(-20000);
    //Encoder.set(ControlMode.PercentOutput,speed);


    SmartDashboard.putNumber("VelocityGraph",Encoder.getSelectedSensorVelocity());
    SmartDashboard.putNumber("CurrentVelocity",Encoder.getSelectedSensorVelocity());
  }



  public void BuildSmartDashboard(){
    SmartDashboard.putNumber("VelocityGraph",0);
    SmartDashboard.putNumber("CurrentVelocity",0);
    //SmartDashboard.putNumber("Speed in Percentage",0);
    SmartDashboard.putNumber("Target Velocity",0);
    SmartDashboard.putNumber("P",0);
    SmartDashboard.putNumber("I",0);
    SmartDashboard.putNumber("D",0);
    SmartDashboard.putNumber("Answer",0);
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
  public double PIDencoder(double setspeed){
    error = setspeed - Encoder.getSelectedSensorVelocity();// Error = Target - Actual
    SmartDashboard.putNumber("P",error);
    /*
    this.integral += (error*.02); // Integral is increased by the error*time (which is .02 seconds using normal IterativeRobot)
    SmartDashboard.putNumber("I",this.integral);
    this.derivative = (error - this.previous_error) / .02;
    previous_error = error;
    SmartDashboard.putNumber("D",this.derivative);
    Answer = P*error + I*this.integral + D*derivative;
    */
    Answer = P*error;
    if(Answer > .8){
      Answer = .8;
    }
    if(Answer < -.8){
      Answer = -.8;
    }
    SmartDashboard.putNumber("Answer",Answer);
    return Answer;
  }
}
