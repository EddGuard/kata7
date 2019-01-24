/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kata7;

import java.util.List;
import java.util.ArrayList;
import java.util.Observer;
import java.util.TimerTask;
import java.util.Timer;

/**
 *
 * @author Eduardo
 */
public class Watch {
    private static final double SecondsPerStep = 2 * Math.PI / 60;
    private static final double MinutesPerStep = SecondsPerStep / 60;
    private static final double HoursPerStep = MinutesPerStep / 12;

    private double seconds = Math.PI / 2;
    private double minutes = Math.PI / 2;
    private double hours = Math.PI / 2;
    private final List<Observer> observers;
    
    public Watch(int hours, int minutes, int seconds) {
        this.observers = new ArrayList<>();
        this.hours=normalize(this.hours-((hours%12)*60*60+minutes*60+seconds)*HoursPerStep);
        this.minutes=normalize(this.minutes-(minutes*60+seconds)*MinutesPerStep);
        this.seconds=normalize(this.seconds-seconds*SecondsPerStep);
        System.out.println("Hora actual: "+hours+":"+minutes+":"+seconds);
        Timer timer;
        timer = new Timer();
        timer.schedule(timerTask(), 0, 1000);
    }

    private TimerTask timerTask() {
        return new TimerTask() {
            @Override
            public void run() {
                step();
                updateObservers();
            }

        };
    }
    
    private void step() {
        seconds = normalize(seconds - SecondsPerStep);
        minutes = normalize(minutes - MinutesPerStep);
        hours = normalize(hours - HoursPerStep);
    }

    private void updateObservers() {
        for (Observer observer : observers) 
            observer.update(null, null);
    }

    public double getSeconds() {
        return seconds;
    }

    public double getMinutes() {
        return minutes;
    }

    public double getHours() {
        return hours;
    }

    public void add(Observer observer) {
        observers.add(observer);
    }

    private double normalize(double v) {
        return v % (2 * Math.PI);
    }   
}