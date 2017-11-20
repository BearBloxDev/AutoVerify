package online.pizzacrust.autoverify.trello.api;

import online.pizzacrust.autoverify.trello.SimpleDatabase;

public class AutoTrello {

    private final Database database;

    public AutoTrello(Database database) {
        this.database = database;
    }

    public Database getDatabase() {
        return database;
    }

    private static Database INSTANCE = null;

    public static Database getInstance() {
        return INSTANCE;
    }

    public static void createDefaultTrello(String apiKey, String accessToken, String boardId)
            throws Exception {
        INSTANCE = new SimpleDatabase(apiKey, accessToken, boardId);
    }

}
