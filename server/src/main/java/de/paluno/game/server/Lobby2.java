package de.paluno.game.server;

import de.paluno.game.interfaces.*;

import java.util.ArrayList;
import java.util.List;

public class Lobby2 {

    public static final int ID_NONE = -1;

    private final int id;
    private final String name;
    private List<User> users;
    private final byte mapNumber;
    private final byte numWorms;
    private Match match;
    private Runnable destroyListener;

    private final User creatingUser;

    public Lobby2(int id, String name, int mapNumber, int numWorms, User creatingUser) {
        this.id = id;
        this.name = name;
        this.mapNumber = (byte)mapNumber;
        this.numWorms = (byte)numWorms;
        this.creatingUser = creatingUser;

        users = new ArrayList<>();

        joinUser(creatingUser);
    }

    public int getId() {
        return id;
    }

    public boolean isOpen() {
        return match == null && users.size() < Constants.NUM_MAX_PLAYERS;
    }

    public boolean joinUser(User joiningUser) {
        if (joiningUser.getCurrentLobbyId() != ID_NONE)
            return joiningUser.getCurrentLobbyId() == id;

        if (users.size() < Constants.NUM_MAX_PLAYERS) {
            UserMessage message = UserMessage.joined(joiningUser.getId(), joiningUser.getName());

            for (User user : users)
                user.getConnection().sendTCP(message);

            users.add(joiningUser);
            joiningUser.setCurrentLobbyId(getId());

            System.out.println("User(name: " + joiningUser.getName()  + ") joined lobby(id: " + id + ")");

            return true;
        }

        return false;
    }

    public boolean leaveUser(User leavingUser) {
        if (leavingUser.getCurrentLobbyId() == id) {
            users.remove(leavingUser);

            Message message;

            if (users.isEmpty() || (match == null && leavingUser.getId() == creatingUser.getId())) {
                message = Message.lobbyDestroyed();

                if (destroyListener != null)
                    destroyListener.run();
            }
            else {
                message = UserMessage.left(leavingUser.getId(), leavingUser.getName());
            }

            leavingUser.setCurrentLobbyId(ID_NONE);

            for (User user : users)
                user.getConnection().sendTCP(message);

            System.out.println("User(name: " + leavingUser.getName()  + ") left lobby(id: " + id + ")");

            return true;
        }

        return false;
    }

    public void broadcastChatMessage(ChatMessage message) {
        for (User user : users) {
            user.getConnection().sendTCP(message);
        }
    }

    public void handleGameData(User sender, GameData gameData) {

    }

    public void startMatch() {
        GameSetupRequest.Player[] players = new GameSetupRequest.Player[users.size()];
        int index = 0;
        for (User user : users)
            players[index++] = new GameSetupRequest.Player(user.getId(), user.getWormNames());

        GameSetupRequest request = new GameSetupRequest(players, mapNumber, numWorms);
        creatingUser.getConnection().sendTCP(request);
    }

    public void setupMatch(GameSetupData data) {
        match = new Match();

        for (User user : users)
            if (user.getId() != creatingUser.getId())
                user.getConnection().sendTCP(data);

        for (PlayerData playerData : data.getPlayerData()) {
            Player player = match.addPlayer(getUserById(playerData.getClientId()), playerData.getPlayerNumber());

            for (WormData wormData : playerData.getWorms()) {
                Worm worm = player.addWorm(wormData.getWormNumber());

            }
        }
    }

    private User getUserById(int clientId) {
        for (User user : users)
            if (user.getId() == clientId)
                return user;

        return null;
    }

    public String[] getUsers() {
        String[] array = new String[users.size()];

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            array[i] = user.getName();
        }

        return array;
    }

    public void setDestroyListener(Runnable listener) {
        this.destroyListener = listener;
    }

    public String getName() {
        return name;
    }

    public int getMapNumber() {
        return mapNumber;
    }

    public int getNumWorms() {
        return numWorms;
    }

    public User getCreatingUser() {
        return creatingUser;
    }
}
