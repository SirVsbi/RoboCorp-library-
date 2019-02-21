package RoboCorpLibrary.Wrappers;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.util.ArrayList;
import java.util.List;

import RoboCorpLibrary.Resources.RobotUtils;


/**
 * Created by Szab on 03/2/2019. (RoboCorp RO084)
 *
 * Copyright (c) 2019 RoboCorp
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
 *
 *  Wrapper for LinearOpMode, adds multithreading to the standard LinearOpMode
 */


public abstract class RobotLinearOpMode extends LinearOpMode{


    protected Telemeter telemeter;
    protected RobotController controller1, controller2;
    public final void runOpMode() throws InterruptedException{
        try{
            telemeter = new Telemeter(super.telemetry);
            telemeter.setNewFirst();
            RobotUtils.setLinearOpMode(this);
            controller1 = new RobotController(super.gamepad1, "controller1");
            controller2 = new RobotController(super.gamepad2, "controller2");
            runLinearOpMode();
        }
        finally {
            stopLinearOpMode();
        }
    }

    public abstract void runLinearOpMode() throws InterruptedException;

    public void stopLinearOpMode(){}


    public void runSimultaneously(Runnable r1, Runnable r2){
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);

        t1.start();
        t2.start();

        while (opModeIsActive() && (t1.isAlive() || t2.isAlive())) {
            idle();
        }

    }

    public void runSimultaneiously(Runnable... runnables){
        List<Thread> threads = new ArrayList<>();
        int i = 0;
        for(Runnable runnable : runnables){
            threads.add(new Thread(runnable));
            threads.get(i).start();
            i++;
        }
        boolean isAlive = true;
        while (opModeIsActive() && isAlive) {
            for(Thread t : threads) isAlive = !t.isAlive();
        }
    }



}