/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taxirides;

/**
 *
 * @author Conny
 */
public class TaxiDriver implements Runnable{

    private Taxi taxi;
    
    public TaxiDriver(Taxi t){
        taxi = t;
    }
    
    @Override
    public void run() {
        taxi.takePassengers();
    }
    
}
