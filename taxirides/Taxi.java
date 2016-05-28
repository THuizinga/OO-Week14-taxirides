package taxirides;

/**
 *
 * @author Tiko Huizinga - s44608988
 * @author Conny Blach - s4329872
 *
 */
import java.util.concurrent.TimeUnit;

public class Taxi {

    private static final long SLEEPTIME = 100;
    private final int taxiId;
    private final int maxNrOfPassengers;
    private final int transportationTime;
    private final Station station;
    private int totalNrOfPassengers = 0;
    private int nrOfRides = 0;

    public Taxi(int nr, int maxNumberOfPassengers, int transportationTime, Station station) {
        this.taxiId = nr;
        this.maxNrOfPassengers = maxNumberOfPassengers;
        this.transportationTime = transportationTime;
        this.station = station;
        System.out.println("New taxi " + nr + " created ");
    }

    /**
     * Tries to take maxNrOfPassenegers from the station. If actual number is
     * less then that number is taken
     */
    public void takePassengers() {
        int passengersTaken = station.tryPassengers(maxNrOfPassengers);
        if (passengersTaken > 0) {
            totalNrOfPassengers += passengersTaken;
            nrOfRides++;
            System.out.println("Taxi " + taxiId + " takes " + passengersTaken + " passengers");
        } else {
            System.out.println("Taxi " + taxiId + " takes no passengers");
            try {
                TimeUnit.MILLISECONDS.sleep(SLEEPTIME); // if no passengers at the station wait some time
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Calculates the total time of this taxi by multiplying the number of rides
     * by the transportation time
     *
     * @return total time
     */
    public int calcTotalTime() {
        return transportationTime * nrOfRides;
    }

    public int getTotalNrOfPassengers() {
        return totalNrOfPassengers;
    }

}
