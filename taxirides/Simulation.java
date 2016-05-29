package taxirides;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Tiko Huizinga - s44608988
 * @author Conny Blach - s4329872
 *
 */
public class Simulation {
    
    public static final int TRAIN_TRIPS = 10;
    public static final int MIN_TRAVELLERS = 60;
    public static final int MAX_TRAVELLERS = 90;
    public static final int CAPACITYSMALL = 4;
    public static final int CAPACITYLARGE = 7;
    public static final int TIMESMALL = 2;
    public static final int TIMELARGE = 3;
    public static final int NROFTAXIS = 4;
    public static final int NROFSMALLTAXIS = 2;
    
    private Taxi[] taxis;
    private Train train;
    private Station station;
    private ExecutorService taxiHandler;
    
    private boolean hasEnded = false;
    private int nextTaxi = 0;
    
    public Simulation() {
        station = new Station();
        taxis = new Taxi[NROFTAXIS];
        taxiHandler = Executors.newCachedThreadPool();
        for (int i = 0; i < NROFTAXIS; i++) {
            taxis[i] = i < NROFSMALLTAXIS ? new Taxi(i + 1, CAPACITYSMALL, TIMESMALL, station) : new Taxi(i + 1,
                    CAPACITYLARGE, TIMELARGE, station);
        }
        train = new Train(station);
    }
    
    public void step() {
        if (station.getNrOfPassengersWaiting() > 0) {
            taxiHandler.submit(new TaxiDriver(taxis[nextTaxi]));
            nextTaxi = (nextTaxi + 1) % NROFTAXIS;
        } else if (train.getNrOfTrips() < TRAIN_TRIPS) {
            train.getIn(Util.getRandomNumber(MIN_TRAVELLERS, MAX_TRAVELLERS));
            train.getOff();
        } else {
            taxiHandler.shutdown();
            try {
				taxiHandler.awaitTermination(100, TimeUnit.MICROSECONDS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            train.closeStation();
            hasEnded = true;
        }
    }
    
    public boolean ended() {
        return hasEnded;
    }
    
    public void showStatistics() {
        System.out.println("All persons have been transported");
        System.out.println("Total time of this simulation:" + calcTotalTime(taxis));
        System.out.println("Total nr of train travellers:" + station.getTotalNrOfPassengers());
        System.out.println("Total nr of persons transported in this simulation:" + calcTotalNrOfPassengers(taxis));
    }

    /**
     * Calculates the total time of the simulation by looping over all taxis
     *
     * @param taxis
     * @return total time
     */
    private static int calcTotalTime(Taxi[] taxis) {
        int time = 0;
        for (Taxi taxi : taxis) {
            time = time + taxi.calcTotalTime();
        }
        return time;
    }

    /**
     * Calculates the total number of passengers that has been transported by
     * looping over all taxis
     *
     * @param taxis
     * @return total number of passengers
     */
    private static int calcTotalNrOfPassengers(Taxi[] taxis) {
        int total = 0;
        for (Taxi taxi : taxis) {
            total += taxi.getTotalNrOfPassengers();
        }
        return total;
    }
    
}
