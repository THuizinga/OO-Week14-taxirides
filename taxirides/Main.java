package taxirides;
/**
 * 
 * @author Tiko Huizinga - s44608988
 * @author Conny Blach - s4329872
 *
 */
public class Main {
  public static void main(String[] args) {
    Simulation sim = new Simulation();
    while (!sim.ended()) {
      sim.step();
    }
    sim.showStatistics();
  }

  
  // hoi tiko
  
  
}
