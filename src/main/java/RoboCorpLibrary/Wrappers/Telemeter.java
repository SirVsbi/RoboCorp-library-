package RoboCorpLibrary.Wrappers;

import android.sax.TextElementListener;

import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import RoboCorpLibrary.Resources.RobotHardware;
import RoboCorpLibrary.Resources.RobotUtils;
import RoboCorpLibrary.Robot;
import RoboCorpLibrary.RobotControlSystems.RobotSubSystems;


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


/**
 * Wrapper for telemetry
 *
 */


public class Telemeter{
    private Telemetry telemetry;
    private static Telemeter instance;

    private boolean open;
    private int TelemeterLength;


    //constructor
    public Telemeter(Telemetry telemetry){
        this.telemetry = telemetry;
        instance = this;
    }


    public static Telemeter getTelemeter(){
        return instance;
    }


    //-------Add something to telemetry

    public void create(String string){
        telemetry.addLine(string);
    }

    public void create(Object data){
        telemetry.addLine(data.toString());
    }

    public void create(String string, Object data){
        telemetry.addData(string, data);
    }

    public void create(final RobotHardware hardware){
        TelemeterLength = hardware.getTelemeter().length;
        for(int i = 0; i < TelemeterLength; i++){
            create(hardware.getName(), hardware.getTelemeter()[i]);
        }
    }

    public void create(RobotHardware... hardwares){
        for(RobotHardware hardware : hardwares){
            create(hardware);
        }
    }


    public void create(final RobotSubSystems subSystem){
        for(RobotHardware hardware : subSystem.getComponents()){
            create(hardware.getTelemeter());
        }
    }





    //--------------------------------






    //-------Create sticky data
    public void createSticky(String string){
        telemetry.log().add(string);
        update();
    }

    public void createSticky(Object data){
        telemetry.log().add(data.toString());
        update();
    }
    public void createSticky(String string, Object data){
        telemetry.log().add(string, data);
        update();
    }

    //sticky output of a hardware device
    public void createSticky(final RobotHardware hardware){
        TelemeterLength = hardware.getTelemeter().length;
        for(int i = 0; i < TelemeterLength; i++){
            telemetry.log().add(hardware.getName(), hardware.getTelemeter()[i]);
        }
        update();
    }

    //sticky output of a sub system
    public void createSticky(final RobotSubSystems subSystem){
        for(RobotHardware hardware : subSystem.getComponents()){
            createSticky(hardware.getTelemeter());
        }
    }





    //------------------



    //-------Add to log---------

    public void log(String string){
        RobotLog.i(string);
    }
    public void log(Object data){
        RobotLog.i(data.toString());
    }
    public void log(String string, Object data){
        RobotLog.i(string, data);
    }

    //Logs the output of a given hardware
    public void log(final RobotHardware hardware){
        TelemeterLength = hardware.getTelemeter().length;
        for(int i = 0; i < TelemeterLength; i++){
            RobotLog.i(hardware.getName(), hardware.getTelemeter()[i]);
        }
    }

    //logs the output of a given sub system
    public void log(final RobotSubSystems subSystem){
        for(RobotHardware hardware : subSystem.getComponents()){
            RobotLog.i(subSystem.getName());
            TelemeterLength = hardware.getTelemeter().length;
            for(int i = 0; i < TelemeterLength; i++){
                RobotLog.i(hardware.getName(), hardware.getTelemeter()[i]);
            }
        }
    }



    //----------------------------






    public void setNewFirst(){
        telemetry.log().setDisplayOrder(Telemetry.Log.DisplayOrder.NEWEST_FIRST);
    }
    public void setNewLast(){
        telemetry.log().setDisplayOrder(Telemetry.Log.DisplayOrder.OLDEST_FIRST);
    }

    public void update(){
        telemetry.update();
    }

    public void clear(){
        telemetry.clearAll();
    }


    public void close(){
        open = false;
    }
    public void open(){
        open = true;
    }


    public void StartUpdate(){
        Runnable main = new Runnable() {
            @Override
            public void run() {
                while (RobotUtils.opModeIsActive() && open) {
                    update();
                    RobotUtils.sleep(100);
                }
            }
        };
        Thread t = new Thread(main);
        t.start();
    }

}