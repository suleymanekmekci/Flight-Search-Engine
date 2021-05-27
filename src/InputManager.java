import java.io.PrintWriter;
import java.util.Date;
import java.util.HashSet;

public class InputManager {
    Graph graph;
    String airportListTxt;
    String flightListTxt;
    String commandListTxt;

    public InputManager( String airportListTxt, String flightListTxt, String commandListTxt) {
        this.airportListTxt = airportListTxt;
        this.flightListTxt = flightListTxt;
        this.commandListTxt = commandListTxt;
        this.graph = new Graph(airportListTxt,flightListTxt);

    }

    public void run() {

        String[] lines = ReadFromFile.readFile(this.commandListTxt);
        String output = "";

        for (String line : lines) {
            String[] elements = line.split("\t");
            String command = elements[0];
            if (command.equals("listAll")) {
                listAllCommonFunction(elements);
                String temp = elements[1];
                String departureAirport = temp.split("->")[0];
                String arrivalAirport = temp.split("->")[1];
                String dateString = elements[2];


                output += "command : listAll\t"+ departureAirport +"->" + arrivalAirport +"\t" + dateString + "\n";

                String result = graph.listAllOutput();
                if (result.equals("")) {
                    output+= "No suitable flight plan is found\n";
                }
                else {
                    output += result;
                }

            }

            else if (command.equals("listProper")) {
                listAllCommonFunction(elements);

                String temp = elements[1];
                String departureAirport = temp.split("->")[0];
                String arrivalAirport = temp.split("->")[1];
                String dateString = elements[2];


                output += "command : listProper\t"+ departureAirport +"->" + arrivalAirport +"\t" + dateString + "\n";

                String result = graph.listProperOutput();
                if (result.equals("")) {
                    output+= "No suitable flight plan is found\n";
                }
                else {
                    output += result;
                }
            }

            else if (command.equals("listCheapest")) {
                listAllCommonFunction(elements);

                String temp = elements[1];
                String departureAirport = temp.split("->")[0];
                String arrivalAirport = temp.split("->")[1];
                String dateString = elements[2];


                output += "command : listCheapest\t"+ departureAirport +"->" + arrivalAirport +"\t" + dateString + "\n";

                String result = graph.listCheapestOutput();
                if (result.equals("")) {
                    output+= "No suitable flight plan is found\n";
                }
                else {
                    output += result;
                }
            }

            else if (command.equals("listQuickest")) {
                listAllCommonFunction(elements);
                String temp = elements[1];
                String departureAirport = temp.split("->")[0];
                String arrivalAirport = temp.split("->")[1];
                String dateString = elements[2];


                output += "command : listQuickest\t"+ departureAirport +"->" + arrivalAirport +"\t" + dateString + "\n";

                String result = graph.listQuickestOutput();

                if (result.equals("")) {
                    output+= "No suitable flight plan is found\n";
                }
                else {
                    output += result;
                }

            }

            else if (command.equals("listCheaper")) {
                listAllCommonFunction(elements);
                graph.listProperOutput();

                int maxPrice = Integer.parseInt(elements[3]);
                String temp = elements[1];
                String departureAirport = temp.split("->")[0];
                String arrivalAirport = temp.split("->")[1];
                String dateString = elements[2];


                output += "command : listCheaper\t"+ departureAirport +"->" + arrivalAirport +"\t" + dateString + "\t" + maxPrice +"\n";
                String result = graph.listCheaper(maxPrice);
                if (result.equals("")) {
                    output+= "No suitable flight plan is found\n";
                }
                else {
                    output += result;
                }
            }

            else if (command.equals("listQuicker")) {
                listAllCommonFunction(elements);
                graph.listProperOutput();

                String latestDateTimeString = elements[3];
                String temp = elements[1];
                String departureAirport = temp.split("->")[0];
                String arrivalAirport = temp.split("->")[1];
                String dateString = elements[2];


                output += "command : listQuicker\t"+ departureAirport +"->" + arrivalAirport +"\t" + dateString + "\t" + latestDateTimeString +"\n";

                Date latestDateTime = graph.stringToDate(latestDateTimeString);
                String result = graph.listQuicker(latestDateTime);
                if (result.equals("")) {
                    output+= "No suitable flight plan is found\n";
                }
                else {
                    output += result;
                }

            }

            else if (command.equals("listExcluding")) {
                listAllCommonFunction(elements);
                graph.listProperOutput();

                String company = elements[3];
                String temp = elements[1];
                String departureAirport = temp.split("->")[0];
                String arrivalAirport = temp.split("->")[1];
                String dateString = elements[2];

                output += "command : listExcluding\t"+ departureAirport +"->" + arrivalAirport +"\t" + dateString + "\t" + company +"\n";

                String result = graph.listExcluding(company);
                if (result.equals("")) {
                    output+= "No suitable flight plan is found\n";
                }
                else {
                    output += result;
                }

            }

            else if (command.equals("listOnlyFrom")) {
                listAllCommonFunction(elements);
                graph.listProperOutput();


                String company = elements[3];
                String temp = elements[1];
                String departureAirport = temp.split("->")[0];
                String arrivalAirport = temp.split("->")[1];
                String dateString = elements[2];

                output += "command : listOnlyFrom\t"+ departureAirport +"->" + arrivalAirport +"\t" + dateString + "\t" + company +"\n";
                String result = graph.listOnlyFrom(company);

                if (result.equals("")) {
                    output+= "No suitable flight plan is found\n";
                }
                else {
                    output += result;
                }

            }

            else if (command.equals("diameterOfGraph")) {


                output += "command : diameterOfGraph" +"\n";
                String result = graph.diameterOfGraph();

                if (result.equals("")) {
                    output+= "No suitable flight plan is found\n";
                }
                else {

                    output += "The diameter of graph : " + result;
                }

            }

            else if (command.equals("pageRankOfNodes")) {
                output += "command : pageRankOfNodes" +"\n";
                output += "Not implemented";
            }


            output += "\n\n";
        }

        try {
                PrintWriter printWriter = new PrintWriter("output.txt", "UTF-8");
                printWriter.write(output);
                printWriter.close();
            }
            catch (Exception e) {
                System.out.println("exception");
            }


    }
    public void listAllCommonFunction(String[] elements) {
        String temp = elements[1];
        String departureAirport = temp.split("->")[0];
        String arrivalAirport = temp.split("->")[1];
        String dateString = elements[2];
        String currentHourstr = dateString + " 00:00";

        graph.listAllHelper(departureAirport,arrivalAirport,new Path(),new HashSet<>(),currentHourstr);
    }
}
