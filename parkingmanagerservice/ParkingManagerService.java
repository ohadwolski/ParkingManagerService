/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingmanagerservice;

import virtualEsp.MessagesParser;

import java.util.Date;
import java.util.Vector;

import static parkingmanagerservice.StateMachine.INIT;

/**
 *
 * @author Ohad Wolski
 */
public class ParkingManagerService {
    public static StateMachine StateMachine = INIT;
    public static Threads Threads = new Threads();
    public static boolean run = true;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        while (run) {
            switch (StateMachine) {
                case INIT:
                    break;
                case GENERAL_CONFIGURATION:
                    break;
                case WAIT_FOR_GENERAL_CONFIGURATION_FINISH:
                    break;
                case GROUP_AND_DISPLAYS_CONFIGURATION:
                    break;
                case WAIT_FOR_GROUP_AND_DISPLAYS_CONFIGURATION:
                    break;
                case OPERATION_MODE_CONFIGURATION:
                    break;
                case WAIT_FOR_OPERATION_MODE_CONFIGURATION:
                    break;
                case STANDBY:
                    break;
            }
        }

        initialize();

        Threads.Start();


    }
    
     /**
     */
    public static void initialize() {
        // TODO code application logic here


        
    }

}

