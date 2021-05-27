import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Graph {
    public static final long HOUR = 3600*1000; // in milli-seconds.
    Map<String, Airport> graph = new HashMap<>();
    String airportTxt;
    String flightTxt;
    String datePattern = "dd/MM/yyyy HH:mm EEE";

    SimpleDateFormat comparison = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat hour = new SimpleDateFormat("HH:mm");
    ArrayList<Path> paths = new ArrayList<>();
    ArrayList<Path> properPaths = new ArrayList<>();
    ArrayList<Path> diameterPaths = new ArrayList<>();


    public Graph(String airportTxt, String flightTxt) {
        this.airportTxt = airportTxt;
        this.flightTxt = flightTxt;
        this.generateGraph();
    }

    private void generateGraph() {

        String[] lines = ReadFromFile.readFile(airportTxt);
        for(String line : lines) {
            String[] elements = line.split("\t");
            String cityName = elements[0];

            if (elements.length > 1) {
                for (int i = 1; i < elements.length; i++) {
                    String airportName = elements[i];
                    Airport airport = new Airport(cityName,airportName);
                    graph.put(airportName,airport);
                }
            }

        }

        lines = ReadFromFile.readFile(flightTxt);
        for(String line : lines) {
            String[] elements = line.split("\t");
            String flightId = elements[0];
            String deptArr = elements[1];
            String departureAirport = deptArr.split("->")[0];
            String arrivalAirport = deptArr.split("->")[1];

            String dateString = elements[2];

            SimpleDateFormat dt = new SimpleDateFormat(datePattern, Locale.ENGLISH);
            Date date = null;
            try {
                date = dt.parse(dateString);

            }
            catch (Exception e) {
                System.out.println("Date error");
            }

            String flightDuration = elements[3];

            int duration = hourStringtoMinutes(flightDuration);

            int price = Integer.parseInt(elements[4]);

            // get airport objects from map
            Airport depAirport = graph.get(departureAirport);
            Airport arrAirport = graph.get(arrivalAirport);

            // create current flight as object
            Flight flight = new Flight(flightId,depAirport,arrAirport,date,duration,price);

            // get the departure airport to add new adjacent vertex to it
            Airport airport = graph.get(departureAirport);

            // this flight's arrivalAirport will be adjacent vertex of departure airport
            airport.getFlights().add(flight);

        }

    }

    public void listAllHelper(String departureCity, String finalCity, Path path, Set<String> visitedCities, String currentTimeString) {
        this.paths.clear();


        List<Airport> departureAirports = new ArrayList<>();
        for (Map.Entry<String,Airport> entry : graph.entrySet()) {
            if (entry.getValue().getCityName().equals(departureCity)) {
                departureAirports.add(entry.getValue());
            }
        }

        if (departureAirports.size() == 0) {
            System.out.println("No path");
            return;
        }


        Date currentHour = this.stringToDateWithHour(currentTimeString);
        currentHour = this.addMinutesToDate(currentHour,-1);

        for (Airport departureAirport : departureAirports) {
            listAll(departureAirport,finalCity,path,visitedCities,currentHour);
        }




    }
    public void listAll(Airport departureAirport, String finalCity, Path path, Set<String> visitedCities, Date currentTime) {

        if (departureAirport.getCityName().equals(finalCity)) {
            Path clonedPath = new Path(path);
            paths.add(clonedPath);
            return;
        }
        List<Flight> adjacentAirportFlights = departureAirport.getFlights();

        for (Flight adjacentAirportFlight : adjacentAirportFlights) {
            String currentCity = adjacentAirportFlight.departureAirport.getCityName();


            // if we haven't visited the city yet
            if (!(visitedCities.contains(currentCity))) {

                boolean makeFlight = false;

                    if (adjacentAirportFlight.getDepartureDate().after(currentTime)) {
                        //make flight
                        makeFlight = true;
                    }

                if (makeFlight) {

                    int passedTime = adjacentAirportFlight.getFlightDuration();

                    if (path.getFlights().size() > 0) {
                        currentTime = path.getFlights().get(path.getFlights().size() - 1 ).getArrivalDate();

                        if (!(adjacentAirportFlight.getDepartureDate().after(currentTime))) {
                            continue;
                        }

                        Date flightDate = adjacentAirportFlight.getDepartureDate();
                        int timeDifference = subtractTwoDates(flightDate,currentTime);
                        passedTime += timeDifference;
                    }

                    visitedCities.add(currentCity);
                    path.addFlight(adjacentAirportFlight,adjacentAirportFlight.getPrice(),passedTime);


                    listAll(adjacentAirportFlight.getArrivalAirport(),finalCity,path,visitedCities,currentTime);
                    visitedCities.remove(currentCity);
                    path.removeFlight(adjacentAirportFlight,adjacentAirportFlight.getPrice(),passedTime);
                }


            }
        }
    }



    public String listAllOutput() {
        String output = "";
        for (Path path : this.paths) {
            output += path.toString();
            output += "\n";
        }
        return output;
    }

    public String listProperOutput() {
        this.properPaths.clear();

        String outputString = "";
        for (int i = 0; i < this.paths.size(); i++) {
            boolean toBeAdded = true;
            Path currentPath = this.paths.get(i);
            for (int j = 0; j < this.paths.size(); j++) {
                Path comparingPath = this.paths.get(j);

                if (currentPath.getPrice() > comparingPath.getPrice() && currentPath.getPassedTime() > comparingPath.getPassedTime()) {
                    toBeAdded = false;
                }

            }
            if (toBeAdded) {
                this.properPaths.add(currentPath);
            }
        }

        for (Path path : this.properPaths) {
            outputString += path.toString();
            outputString += "\n";
        }

        return outputString;
    }

    public String listCheapestOutput() {

        int minPrice = Integer.MAX_VALUE;
        for (int i = 0; i < this.paths.size(); i++) {
            Path currentPath = this.paths.get(i);
            if (currentPath.getPrice() < minPrice) {
                minPrice = currentPath.getPrice();
            }
        }
        List<Path> cheapestPaths = new ArrayList<>();
        for (int i = 0; i < this.paths.size(); i++) {
            Path currentPath = this.paths.get(i);
            if (currentPath.getPrice() == minPrice) {
                cheapestPaths.add(currentPath);
            }
        }

        String outputString = "";
        for (Path path : cheapestPaths) {
            outputString += path.toString();
            outputString += "\n";
        }

        return outputString;
    }

    public String listQuickestOutput() {

        int minTime = Integer.MAX_VALUE;
        for (int i = 0; i < this.paths.size(); i++) {
            Path currentPath = this.paths.get(i);
            if (currentPath.getPassedTime() < minTime) {
                minTime = currentPath.getPassedTime();
            }
        }
        List<Path> quickestPaths = new ArrayList<>();
        for (int i = 0; i < this.paths.size(); i++) {
            Path currentPath = this.paths.get(i);
            if (currentPath.getPassedTime() == minTime) {
                quickestPaths.add(currentPath);
            }
        }

        String outputString = "";
        for (Path path : quickestPaths) {
            outputString += path.toString();
            outputString += "\n";
        }

        return outputString;
    }

    public String listCheaper(int maxPrice) {
        List<Path> output = new ArrayList<>();

        for(Path path : this.properPaths) {
            if (path.getPrice() < maxPrice) {
                output.add(path);
            }
        }

        String outputString = "";
        for (Path path : output) {
            outputString += path.toString();
            outputString += "\n";
        }
        return outputString;
    }

    public String listQuicker(Date latestDateTime) {
        List<Path> output = new ArrayList<>();

        for(Path path : this.properPaths) {
            if (path.getFlights().get( path.getFlights().size() - 1 ).getArrivalDate().before(latestDateTime)) {
                output.add(path);
            }
        }

        String outputString = "";
        for (Path path : output) {
            outputString += path.toString();
            outputString += "\n";
        }
        return outputString;
    }

    public String listExcluding(String company) {
        List<Path> output = new ArrayList<>();

        for(Path path : this.properPaths) {
            boolean toBeAdded = true;
            for(Flight flight : path.getFlights()) {
                if (flight.getCompany().equals(company)) {
                    toBeAdded = false;
                    break;
                }
            }
            if (toBeAdded) {
                output.add(path);
            }

        }

        String outputString = "";
        for (Path path : output) {
            outputString += path.toString();
            outputString += "\n";
        }
        return outputString;
    }

    public String listOnlyFrom(String company) {
        List<Path> output = new ArrayList<>();

        for(Path path : this.properPaths) {
            boolean toBeAdded = true;
            for(Flight flight : path.getFlights()) {
                if (!(flight.getCompany().equals(company))) {
                    toBeAdded = false;
                    break;
                }
            }
            if (toBeAdded) {
                output.add(path);
            }

        }

        String outputString = "";
        for (Path path : output) {
            outputString += path.toString();
            outputString += "\n";
        }
        return outputString;
    }

    public String diameterOfGraph() {
//        Map<String, Airport> graph = new HashMap<>();
        List<Path> minimumPrices = new ArrayList<>();

        for (Map.Entry<String,Airport> entry: graph.entrySet()) {
            for(Map.Entry<String,Airport> entry1: graph.entrySet()) {
                if (!(entry.getKey().equals(entry1.getKey()))) {
                    diameterOfGraphDFS(entry.getValue(),entry1.getValue(),new HashSet<>(),new Path());

                    int min  = Integer.MAX_VALUE;
                    Path temp = new Path();
                    for (Path path : diameterPaths) {
                        if (path.getPrice() < min) {
                            min = path.getPrice();
                            temp = path;
                        }
                    }
                    minimumPrices.add(temp);
                    diameterPaths.clear();

                }
            }
            // min price

        }

        int max = Integer.MIN_VALUE;
        Path result = new Path();
        for (Path path: minimumPrices) {
            if (path.getPrice() >max) {
                max = path.getPrice();
                result = path;
            }
        }

        return String.valueOf(result.getPrice());
    }

    public void diameterOfGraphDFS(Airport departureAirport,Airport finalAirport,Set<Airport> visitedAirports,Path path) {
        if (departureAirport.getAirportName().equals(finalAirport.getAirportName())) {
            Path clonedPath = new Path(path);
            diameterPaths.add(clonedPath);
            return;
        }

        List<Flight> adjacentAirportFlights = departureAirport.getFlights();

        for (Flight adjacentAirportFlight : adjacentAirportFlights) {
            Airport currentAirport = adjacentAirportFlight.departureAirport;


            // if we haven't visited the city yet
            if (!(visitedAirports.contains(currentAirport))) {

                visitedAirports.add(currentAirport);
                path.addFlight(adjacentAirportFlight,adjacentAirportFlight.getPrice(),0);


                diameterOfGraphDFS(adjacentAirportFlight.getArrivalAirport(),finalAirport,visitedAirports,path);
                visitedAirports.remove(currentAirport);
                path.removeFlight(adjacentAirportFlight,adjacentAirportFlight.getPrice(),0);

            }
        }
    }

    public int subtractTwoDates(Date d2, Date d1) {
        long diff = d2.getTime() - d1.getTime();//as given
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
        return (int)minutes;
    }

    public Date addMinutesToDate(Date date, int minutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minutes);
        return cal.getTime();
    }

    public Date stringToDate(String dateString) {
        Date output = null;
        try {
            output = fmt.parse(dateString);
        }
        catch ( Exception e ) {
            System.out.println("date error in listall");
        }
        return output;
    }

    public Date stringToDateWithHour(String dateString) {

        String currentHourstr = dateString + " 00:00";

        Date output= null;
        try {
            output = this.comparison.parse(currentHourstr);
        } catch (Exception e) {
            System.out.println("Date error first check");
        }
        return output;
    }



    public int hourStringtoMinutes(String hour) {
        String hourString = hour.split(":")[0];
        String minuteString = hour.split(":")[1];

        int hourInt = Integer.parseInt(hourString);
        int hourToMinute = hourInt * 60;
        int output = hourToMinute + Integer.parseInt(minuteString);
        return output;


    }
}
