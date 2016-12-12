package states;


public class StateManager {
    //It will hold what state we currently want to tick() and render()
    private static State currentState = null;

    //Changes the current state of the game
    public static void setState(State state) {
        currentState = state;
    }

    //Gets the current state of the game
    public static State getState() {
        return currentState;
    }
}
