import java.util.ArrayList;
import java.util.List;

public class Airport {
    private String cityName;
    private String airportName;
    private List<Flight> flights; // adjacency

    public Airport(String cityName, String airportName) {
        this.cityName = cityName;
        this.airportName = airportName;
        flights = new ArrayList<>();
    }

    public Airport(String cityName, String airportName, List<Flight> flights) {
        this.cityName = cityName;
        this.airportName = airportName;
        this.flights = flights;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    @Override
    public String toString() {
        return this.airportName;
    }
}
