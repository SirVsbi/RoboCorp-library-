package RoboCorpLibrary.Resources;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import RoboCorpLibrary.Wrappers.RobotLinearOpMode;




/**
 * Created by Szab on 03/2/2019. (RoboCorp RO084)
 *
 * Copyright (c) 2018 RoboCorp
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */



public class RobotUtils{

    private static RobotLinearOpMode linearOpMode;

    public static void sleep(int sleep){
        try{
            Thread.sleep(sleep);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public static void setLinearOpMode(RobotLinearOpMode robotLinearOpMode){
        linearOpMode = robotLinearOpMode;
    }

    public static RobotLinearOpMode getLinearOpMode(){
        return linearOpMode;
    }

    public static Telemetry getTelemetry(){
        return linearOpMode.telemetry;
    }

    public static boolean opModeIsActive(){
        return linearOpMode.opModeIsActive();
    }

    public HardwareMap getHardwareMap(){
        return linearOpMode.hardwareMap;
    }


    public static double max(double... values){
        double max = Double.MIN_VALUE;
        for(double i : values)
            if(max < i) max = i;
        return max;
    }

}