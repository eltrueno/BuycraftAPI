package me.hugmanrique.buycraftapi.data;

import me.hugmanrique.buycraftapi.BuycraftApi;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * Created by HugmanriqueMC. All Rights Reserved
 * The copy of this file may not be copied in any form without
 * the prior written permission of Hugo Manrique.
 *
 * @author Hugmanrique
 *         Spigot. Created the 14/05/2016.
 **/
public class Payment {
    private int id;
    private double amount;
    private Date date;

    private String currency;
    private String currencySymbol;

    private int playerId;
    private String playerName;
    private UUID playerUuid;

    private Map<Integer, String> packages;

    public Payment(int id, double amount, Date date, String currency, String currencySymbol, int playerId, String playerName, UUID playerUuid, Map<Integer, String> packages) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.currency = currency;
        this.currencySymbol = currencySymbol;
        this.playerId = playerId;
        this.playerName = playerName;
        this.playerUuid = playerUuid;
        this.packages = packages;
    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    /**
     * Gets the UUID of this Player. May be null if the store is in offline mode
     * @return The Mojang UUID of this Player or null if the store is in offline mode
     * @see {@link Account#isOnlineMode()}
     */
    public UUID getPlayerUuid() {
        return playerUuid;
    }

    public Date getDate() {
        return date;
    }

    /**
     * Gets a {@link Map} with all the bought ids as keys and names as values
     * @return A map containing all the {@link Package} ids and names
     * @see {@link BuycraftApi#getListing()} To get the information of a {@link Package} id
     */

    public Map<Integer, String> getPackages() {
        return packages;
    }
}
