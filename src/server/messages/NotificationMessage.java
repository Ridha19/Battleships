package server.messages;

import java.io.Serializable;

public class NotificationMessage extends ServerMessage implements Serializable {

    public final static int PLACE_SHIPS = 101;
    public final static int YOUR_TURN = 102;
    public final static int OPPONENTS_TURN = 103;

    public final static int GAME_WIN = 201;
    public final static int GAME_LOSE = 202;
    public final static int TIMEOUT_WIN = 203;
    public final static int TIMEOUT_LOST = 204;
    public final static int TIMEOUT_DRAW = 205;

    public final static int OPPONENTS_NAME = 301;

    public final static int GAME_TOKEN = 401;
    public final static int GAME_NOT_FOUND = 402;

    private int code;
    private String[] text;

    public NotificationMessage(int code) {
        this.code = code;
    }

    public NotificationMessage(int code, String... text) {
        this.code = code;
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public String[] getText() {
        return text;
    }
}
