package com.ijse.apexbuildingsolution.apex_building_solution.service;

import javafx.concurrent.Task;

public class LoadingThread extends Task<Integer> {
    @Override
    protected Integer call() {
        for (double i = 0; i <= 100 ; i++) {
            updateProgress(i, 100);
            try {
                Thread.sleep(25);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        return 100;
    }
}
