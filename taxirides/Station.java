package taxirides;

/**
 * @author Tiko Huizinga - s44608988
 * @author Conny Blach - s4329872
 * Class that holds the number of persons arriving by train at the station and
 * waiting for a taxi*
 */
public class Station {

    private int nrOfPassengersAtStation;
    private int totalNrOfPassengers;
    private boolean isClosed;
    
    public Station(  ) {
        this.nrOfPassengersAtStation = 0;
        this.totalNrOfPassengers = 0;
        this.isClosed = false;
    }

    public void enterStation( int nrOfPassengers ) {
        nrOfPassengersAtStation += nrOfPassengers;
        totalNrOfPassengers += nrOfPassengers;
        System.out.println("Train with " + nrOfPassengers + " passengers has arrived");
    }
    /**
     * Ask for nrOfPassengers Passengers to leave the station
     *
     * @param nrOfPassengers
     */
    public void leaveStation( int nrOfPassengers ) {
        nrOfPassengersAtStation -= nrOfPassengers;
    }

    public int getNrOfPassengersWaiting() {
        return nrOfPassengersAtStation;
    }
    
    public synchronized int tryPassengers(int taxiCapacity){
        int passengersToTake = Math.min(taxiCapacity, nrOfPassengersAtStation);
        nrOfPassengersAtStation -= passengersToTake;
        return passengersToTake;
    }

    public void close() {
        isClosed = true;
    }
    
    public boolean isClosed() {
        return isClosed;     
    }

    public int getTotalNrOfPassengers() {
        return totalNrOfPassengers;
    }


}

