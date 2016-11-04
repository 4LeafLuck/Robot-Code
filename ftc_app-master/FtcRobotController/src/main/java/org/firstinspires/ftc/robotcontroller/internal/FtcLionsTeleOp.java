/* Copyright (c) 2014 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package com.qualcomm.ftcrobotcontroller.opmodes;

import android.bluetooth.BluetoothClass;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;


public class FTCLionsTeleOp extends OpMode {
    final boolean DEBUG = true;

    DcMotor leftDrive;
    DcMotor rightDrive;

    public FTCLionsTeleOp() {

    }

    @Override
    public void start() {
        started = true;

        leftFront = hardwareMap.dcMotor.get("leftFront");
        leftBack = hardwareMap.dcMotor.get("leftBack");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftBack = hardwareMap.dcMotor.get("leftBack");

        leftFront.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        rightFront.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        leftBack.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        rightBack.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
    }

    @Override
    public void init() {

    }


    @Override
    public void loop() {
        if (DEBUG) {
            // TELEMETRY FOR JOYSTICK DEBUGGING
            telemetry.addData("****Controller Data****");
            telemetry.addData("Gamepad1 Movement: y", gamepad1.right_stick_y, ", x", gamepad1.right_stick_x);
            telemetry.addData("Gamepad2 Movement: y", gamepad2.right_stick_y, ", x", gamepad2.right_stick_x);
        }

        ////////////////////////////////
        //     GAMEPAD 1 CONTROLS     //
        ////////////////////////////////


        // TANK DRIVE \\
        leftFront.setPower(gamepad1.left_stick_y);
        rightFront.setPower(-gamepad1.right_stick_y);
        leftBack.setPower(gamepad1.left_stick_y);
        rightBack.setPower(-gamepad1.right_stick_y);

        while (gamepad1.right_stick_x != 0) { //right side of robot
            rightFront.setPower(gamepad1.right_stick_x); //assuming that the right side is backwards
            rightBack.setPower(-gamepad1.right_stick_x);

        }
        while (gamepad1.left_stick_x != 0) { //left side of the robot
            leftFront.setPower(gamepad1.left_stick_x);
            leftBack.setPower(-gamepad1.left_stick_x);
        }





        ////////////////////////////////
        //     GAMEPAD 2 CONTROLS     //
        ////////////////////////////////




        

        // E-STOP
        if (gamepad1.left_bumper && gamepad1.right_bumper && gamepad2.left_bumper && gamepad2.right_bumper) { //mash those bumpers
            leftDrive.setPower(0);
            rightDrive.setPower(0);
        }
    }
}

    @Override
    public void stop() {

    }
}
