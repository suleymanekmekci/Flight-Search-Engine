import java.util.ArrayList;

public class Path {
    private int price;
    private int passedTimeMinutes;
    private ArrayList<Flight> flights;

    public Path(int price, int passedTime, ArrayList<Flight> flights) {
        this.price = price;
        this.passedTimeMinutes = passedTime;
        this.flights = flights;
    }

    @SuppressWarnings("unchecked")
    public Path(Path clone) {
        this.price = clone.price;
        this.passedTimeMinutes = clone.passedTimeMinutes;

        this.flights = (ArrayList<Flight>) clone.flights.clone();
    }

    public Path() {
        this.flights = new ArrayList<>();
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void addPrice(int price) {
        this.price += price;
    }

    public void subtractPrice(int price) {
        this.price -= price;
    }

    public int getPassedTime() {
        return passedTimeMinutes;
    }

    public void setPassedTime(int passedTime) {
        this.passedTimeMinutes = passedTime;
    }

    public void addTime(int time) {
        this.passedTimeMinutes += time;
    }
    public void subtractTime(int time) {
        this.passedTimeMinutes -= time;
    }

    public void addFlight(Flight flight, int price, int time) {
        this.flights.add(flight);
        this.price += price;
        this.passedTimeMinutes += time;
    }

    public void removeFlight(Flight flight, int price, int time) {
        this.flights.remove(flight);
        this.price -= price;
        this.passedTimeMinutes -= time;
    }

    public ArrayList<Flight> getFlights() {
        return flights;
    }

    public void setFlights(ArrayList<Flight> flights) {
        this.flights = flights;
    }

    @Override
    public String toString() {
        String output = "";
        int counter = 0;
        for (Flight flight : this.flights) {
            if (counter == this.flights.size() - 1) {
                output += flight.toString();
            }
            else {
                output += flight.toString() + "||";
            }

            counter++;
        }

        output += "\t" + this.minutesToHour() + "/" + this.price;

        return output;
    }

    public String minutesToHour() {
        int passedTime = this.getPassedTime();
        int hours = passedTime/60;
        int minutes = passedTime%60;
        String outputHour = "";
        String outputMinute = "";
        if (hours < 10) {
            outputHour = "0" + hours + ":";
        }
        else {
            outputHour = String.valueOf(hours) + ":";
        }
        if (minutes < 10) {
            outputMinute = "0" + minutes;
        }
        else {
            outputMinute = String.valueOf(minutes);
        }
        return outputHour + outputMinute;
    }
}
