# Flight-Search-Engine

## Introduction
In this experiment, I practiced on directed graphs and graph search by perfoming file I/O operations in Java. 

## Background
A Graph is a non-linear data structure consisting of nodes and edges as shown in Figure 1. The nodes are sometimes also referred to as vertices and the edges are lines or arcs. They are data structures used to model pairwise relations between objects. The vertices are used to model the objects and edges connecting two vertices that are used to model the pairwise relation between those objects. They are used to solve many real-life problems. Graphs are used to represent networks. The networks may include paths in a city or telephone network or circuit network.

## Experiment
In this experiment, I developed a flight search engine (such as skyscanner
(www.skayscanner.com/) or e-dream (www.edreams.com/)) that enables users to make flight plans. The
flight database is provided via input files. Program reads these input files and creates a
directed graph structure to store the information. Finally, program creates output files according
to information given two input files.

## Input Files
In this section, details will be given about the input files to be used in this experiment. All the information
about the flight is stored in three input files, which are described below: airportList.txt, flightList.txt and
commandList.txt.

### Airport List
The first input file contains a list of cities along with aliases of the airports located at those cities. Each
line of this file contains the name of a city and the airport aliases which are separated by tab character. A
city can have more than one aliases. The city names and airport aliases will be unique among the system

#### Structure of airportList.txt
**[city_name]** tab **[airport_alias]** newline
**[city_name]** tab **[airport_alias]** tab **[airport_alias]** newline

#### Example of airportList.txt
Vienna VIE
Brussels BRU CRL
Sofia SOF

### Flight List

The second input file contains a list of all the available flights to be included in the database. Each line
represents an individual flight containing the flight id, departure and arrival airport aliases, the departure
date and time (as hours and minutes), flight duration (again, as hours and minutes) and the price respectively.
The flight ids are composed of two uppercase characters that represent one of the airlines companies that
are:
- Turkish Airlines (TK)
- Royal Dutch Airlines (KL)
- Lufthansa Airlines (LH)
- Delta Airlines (DL)
- American Airlines (AA)
and a four-digit number to ensure that the flight ids are unique among the system. Program creates
a graph according to airportList.txt and flightList.txt files.

#### Structure of flightList.txt
**[flight_id]** tab **[dept]** -> **[arr]** tab **[dept_date]** tab **[duration]** tab **[price]** newline
**[flight_id]** tab **[dept]** -> **[arr]** tab **[dept_date]** tab **[duration]** tab **[price]** newline

#### Example of flightList.txt
KL6805 FCO->TXL 30/04/2020 21:40 Thu 02:25 75
LH6171 SOF->FCO 01/05/2020 19:00 Fri 01:55 135
TK2133 IST->FCO 01/05/2020 16:20 Thu 02:25 125


### Command List

After reading the two input files mentioned above, program reads from a third input file that
contains the commands which are essentially flight search queries. A flight search query yields a flight plan
which is either a single flight or a series of connected flights from the departure point to the arrival point.
There are two rules for two flights to be eligible to be connected in a flight plan:
- Arrival point of the first flight and departure point of the second flight should be the same airport.
- Departure time of the second flight should be later than arrival time of the first flight.
There are also two more points that should be taken into consideration while forming a flight plan that are:
- Transferring between two airports at the same city is not allowed.
- A flight plan may not pass through the same city more than once. This rule applies to all the
airports at a city.
Below is a list of 10 different commands. Each command includes a departure point and an arrival point
that are cities. The start date parameter of the commands indicates the earliest date (starting from midnight,
00:00) the flights may start and it is possible for a flight plan to start any time after the start date.


#### 1- List All

List all command is used for listing all the possible flight plan(s) from departure point to the arrival point.
##### Structure of command
**[listAll]** tab **[dept]**-> **[arr]** tab **[start_date]**


#### 2- List Proper

List proper command is used for listing all the proper flight plan(s) from departure point to the arrival
point which means a flight plan should be removed from the resulting flight plan set if there is another
flight plan which is both quicker (that arrives at the final destination earlier) and cheaper.
##### Structure of command
**[listProper]** tab **[dept]**-> **[arr]** tab **[start_date]**

#### 3- List Cheapest

List cheapest command is used for listing the cheapest possible flight plan(s) from departure point to the
arrival point. There may be more than one possible cheapest flight plans.

##### Structure of command
**[listCheapest]** tab **[dept]**-> **[arr]** tab **[start_date]**

#### 3- List Cheapest

List cheapest command is used for listing the cheapest possible flight plan(s) from departure point to the
arrival point. There may be more than one possible cheapest flight plans.

##### Structure of command
**[listCheapest]** tab **[dept]**-> **[arr]** tab **[start_date]**

#### 4- List Quickest

List quickest command is used for listing the quickest possible flight plan(s) from departure point to the
arrival point. There may be more than one possible quickest flight plans.

##### Structure of command
**[listQuickest]** tab **[dept]**-> **[arr]** tab **[start_date]**

#### 5- List Cheaper

List cheaper command is used for listing all the **proper** flight plans from departure point to the arrival
point that are cheaper than given parameter.

##### Structure of command
**[listCheaper]** tab **[dept]**-> **[arr]** tab **[start_date]** tab **[max_price]**

#### 6- List Quicker

List quicker command is used for listing all the **proper** flight plans from departure point to the arrival
point that arrive to the destination earlier than given parameter.

##### Structure of command
**[listQuicker]** tab **[dept]**-> **[arr]** tab **[start_date]** tab **[latest_date_time]**

#### 7- List Excluding

List excluding command is used for listing all the **proper** flight plans from departure point to the arrival
point that do not involve a flight from given airlines company.

##### Structure of command
**[listExcluding]** tab **[dept]**-> **[arr]** tab **[start_date]** tab **[company]**

#### 8- List Only From

List only from command is used for listing all the **proper** flight plans from departure point to the arrival
point that consists of flights only from the given airlines company.

##### Structure of command
**[listOnlyFrom]** tab **[dept]**-> **[arr]** tab **[start_date]** tab **[company]**

#### 9- Diameter of Graph

The diameter of graph is the maximum distance between the pair of vertices. It can also be defined as the
longest shortest path. When calculating the diameter of graph, consider the graph that you created by using 
input files and flight price will be cost of edges.

##### Structure of command
**[diameterOfGraph]**


### Output File
Results of each command is a list of flight plans are printed to an output file which name 
is output.txt. The output format of first eight command are similar but for last one (diameterOfGraph) format is different. 
Simply, each command is printed to a line of the output file in the following format:

##### Structure of output.txt
[command] : [first command in command.txt] newline
[flight_id] tab [dept]-> [arr]||[flight_id] tab [dept]-> [arr]||…… tab [duration]/[total_price] newline
newline
newline
[command] : [second command in command.txt] newline
[flight_id] tab [dept]-> [arr]||[flight_id] tab [dept]-> [arr]||…… tab [duration]/[total_price] newline
newline
newline
[command] : [third command in command.txt] newline // for diameter of graph
[The diameter of graph] : total_price
newline
newline


## Execution and Test
The input files should be given as program arguments. The argument order will be as follow:
airportList.txt, flightList.txt and commandList.txt.


- Compile the code (javac *.java)
- Run the program (java Main airportList.txt flightList.txt commandList.txt)
- Control your output data and format.



