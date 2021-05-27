import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Flight {
    String id;
    Airport departureAirport;
    Airport arrivalAirport;
    Date departureDate;
    int flightDuration; // in minutes
    Date arrivalDate;
    int price;

    public Flight(String id, Airport departureAirport, Airport arrivalAirport, Date departureDate, int flightDuration, int price) {
        this.id = id;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureDate = departureDate;
        this.flightDuration = flightDuration;
        this.arrivalDate = addMinutesToDate(departureDate,flightDuration);
        this.price = price;
    }

    public Date addMinutesToDate(Date date, int minutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minutes);
        return cal.getTime();
    }

    public String getCompany() {
        return id.substring(0,2);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public int getFlightDuration() {
        return flightDuration;
    }

    public void setFlightDuration(int flightDuration) {
        this.flightDuration = flightDuration;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    //    SimpleDateFormat outputDateFormat = new SimpleDateFormat(pattern, Locale.ENGLISH);


    @Override
    public String toString() {
        return id + '\t' + departureAirport + "->" + arrivalAirport;

    }
}
