
public class Main {
    public static void main(String[] args)  {
        // args: airportList.txt flightList.txt commandList.txt
        InputManager inputManager = new InputManager(args[0],args[1],args[2]);
        inputManager.run();
    }
}
