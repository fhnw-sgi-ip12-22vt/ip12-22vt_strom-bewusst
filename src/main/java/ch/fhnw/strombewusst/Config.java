package ch.fhnw.strombewusst;

public final class Config {
    /** If release Mode is enabled, puzzles can be skipped, cutscenes are disabled and the devMenu is enabled. */
    public static final boolean IS_RELEASE = false;
    /** If demo Mode is enabled, puzzles can be skipped, but cutscenes are still enabled. */
    public static final boolean IS_DEMO = false;
    /** The tick-rate at which the game runs */
    public static final int TICK_RATE = 60;
    /** The screen height */
    public static final int HEIGHT = 720;
    /** The screen width */
    public static final int WIDTH = 1280;
    /** The path of the file in which the high scores get saved */
    public static final String SAVE_FILE_NAME = "high_score.dat";
    /** Path of the file containing notes that get shown on the computers in room 1 */
    public static final String ROOM_1_TEXT_PATH = "room1_deskinfo.txt";
    /** Path of the file containing notes that get shown on the devices in room 2 */
    public static final String ROOM_2_TEXT_PATH = "room2_deviceinfo.txt";
    /** Path of the cutscene config for the tutorial cutscene */
    public static final String TUTORIAL_CUTSCENE_PATH = "tutorial.txt";
    /** Path of the cutscene config for the room 2 cutscene */
    public static final String ROOM_2_CUTSCENE_PATH = "tutorial2.txt";
    /** Path of the cutscene config for the final cutscene */
    public static final String FINAL_CUTSCENE_PATH = "credit_scene.txt";
    /** Path of the json file containing the questions for the quiz in room 1 */
    public static final String QUESTIONS_JSON_PATH = "json/questions.json";
    /** Path of the json file containing device infos for the puzzle in room 2 */
    public static final String DEVICES_JSON_PATH = "json/devices.json";
    /** The number of questions asked in the quiz in room 1 */
    public static final int QUIZ_QUESTION_COUNT = 10;
    /** The number of rounds to play in the device order puzzle */
    public static final int DEVICE_ORDER_COUNT = 2;
    /** The number of seconds that the timer gets initialized with */
    public static final int TIMER_INITIAL_SECONDS = 60 * 15;
    /** The number of seconds to wait after the last interaction before stopping the game */
    public static final int TIME_OUT_SECONDS = 60 * 1;
}
