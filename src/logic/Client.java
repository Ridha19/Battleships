package logic;

import client.ClientView;
import server.messages.MoveMessage;
import server.messages.MoveResponseMessage;
import server.messages.NotificationMessage;

import java.io.*;
import java.net.Socket;

public class Client extends Thread {

    private Board ownBoard;
    private Board opponentBoard;

    private ObjectOutputStream out;
    private ObjectInputStream in;

    public Client(ClientView clientView, Board ownBoard, Board opponentBoard) {
        this.ownBoard = ownBoard;
        this.opponentBoard = opponentBoard;

        try {
            Socket socket = new Socket("localhost", 8900);
            out = new ObjectOutputStream(
                    new BufferedOutputStream(socket.getOutputStream()));
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        start();
    }

    @Override
    public void run() {
        super.run();
        Object input;
        try {
            while ((input = in.readObject()) != null) {
                parseInput(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void parseInput(Object input) {
        if (input instanceof NotificationMessage) {
            NotificationMessage n = (NotificationMessage) input;
            switch (n.getCode()) {
                case NotificationMessage.OPPONENTS_NAME:
                    // TODO: handle receiving opponents name
                    break;
                case NotificationMessage.GAME_TOKEN:
                    // TODO: handle receiving game token to share with friend
                    break;
                case NotificationMessage.GAME_NOT_FOUND:
                    // TODO: handle joining a game that doesn't exist
                    break;
                case NotificationMessage.PLACE_SHIPS:
                    // TODO: allow player to start positioning ships
                    break;
                case NotificationMessage.YOUR_TURN:
                    // TODO: inform player it's their turn and to make a move
                    break;
                case NotificationMessage.OPPONENTS_TURN:
                    // TODO: informs player it is their opponent's turn
                    break;
                case NotificationMessage.GAME_WIN:
                    // TODO: inform player they have won the game
                    break;
                case NotificationMessage.GAME_LOSE:
                    // TODO: inform player they have lost the game
                    break;
                case NotificationMessage.TIMEOUT_WIN:
                    // TODO: inform of win due to opponent taking too long
                    break;
                case NotificationMessage.TIMEOUT_LOSE:
                    // TODO: inform of loss due to taking too long
                    break;
                case NotificationMessage.TIMEOUT_DRAW:
                    // TODO: inform that both took too long to place ships
            }
        } else if (input instanceof MoveResponseMessage) {
            MoveResponseMessage move = (MoveResponseMessage) input;
            if (move.isOwnBoard()) {
                ownBoard.applyMove(move);
            } else {
                opponentBoard.applyMove(move);
            }
        }
    }

    public void sendMove(int x, int y) throws IOException {
        out.writeObject(new MoveMessage(x, y));
        out.flush();
    }
}