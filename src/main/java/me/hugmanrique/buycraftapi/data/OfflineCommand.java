package me.hugmanrique.buycraftapi.data;

import java.util.UUID;

/**
 * Created by HugmanriqueMC. All Rights Reserved
 * The copy of this file may not be copied in any form without
 * the prior written permission of Hugo Manrique.
 *
 * @author Hugmanrique
 *         Spigot. Created the 14/05/2016.
 **/
public class OfflineCommand {
    private int id;
    private String command;
    private String payment;
    private String packageId;

    private int delay;

    private int playerId;
    private String playerName;
    private UUID playerUUID;

    public OfflineCommand(int id, String command, String payment, String packageId, int delay, int playerId, String playerName, UUID playerUUID) {
        this.id = id;
        this.command = command;
        this.payment = payment;
        this.packageId = packageId;
        this.delay = delay;
        this.playerId = playerId;
        this.playerName = playerName;
        this.playerUUID = playerUUID;
    }

    public int getId() {
        return id;
    }

    public String getCommand() {
        return command;
    }

    public String getPayment() {
        return payment;
    }

    public String getPackageId() {
        return packageId;
    }

    public int getDelay() {
        return delay;
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }
}
