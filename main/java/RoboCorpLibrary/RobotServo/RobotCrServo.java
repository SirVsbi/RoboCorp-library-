package RoboCorpLibrary.RobotServo;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import RoboCorpLibrary.Resources.RobotHardware;

public class RobotCrServo implements RobotHardware{


    private String name;
    private CRServo crServo;


    public RobotCrServo(String name, HardwareMap hwMap){
        crServo = hwMap.crservo.get("name");
        setName(name);
    }

    public void setName(String name){
        this.name = name;
    }



    public double getPower(){
        return crServo.getPower();

    }


    public void setPower(double power){
        crServo.setPower(power);

    }




    public String getName(){
        return name;
    }

    public String[] getTelemeter() {
        return new String[]{

        };
    }



}