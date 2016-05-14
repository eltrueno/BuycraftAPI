package me.hugmanrique.buycraftapi.data;

import me.hugmanrique.buycraftapi.utils.JsonUtils;
import org.json.JSONObject;

/**
 * Created by HugmanriqueMC. All Rights Reserved
 * The copy of this file may not be copied in any form without
 * the prior written permission of Hugo Manrique.
 *
 * @author Hugmanrique
 *         Spigot. Created the 14/05/2016.
 **/
public class Account {
    private int id;
    private String domain;
    private String name;

    private String currencyName;
    private String currencySymbol;

    private boolean onlineMode;

    public Account(int id, String domain, String name, String currencyName, String currencySymbol, boolean onlineMode) {
        this.id = id;
        this.domain = domain;
        this.name = name;
        this.currencyName = currencyName;
        this.currencySymbol = currencySymbol;
        this.onlineMode = onlineMode;
    }

    public Account(JSONObject object, JSONObject currency){
        this(JsonUtils.safeGetInt(object, "id"), JsonUtils.safeGetString(object, "domain"), JsonUtils.safeGetString(object, "name"), JsonUtils.safeGetString(currency, "iso_4217"), JsonUtils.safeGetString(currency, "symbol"), JsonUtils.safeGetBoolean(object, "online_mode"));
    }

    public int getId() {
        return id;
    }

    /**
     * Gets the Domain of this Store
     * @return A {@link String} containing the complete URL
     */

    public String getDomain() {
        return domain;
    }

    public String getName() {
        return name;
    }

    /**
     * Gets the currency name following the ISO 4217 standard
     * @return A {@link String} containing the currency name
     */
    public String getCurrencyName() {
        return currencyName;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public boolean isOnlineMode() {
        return onlineMode;
    }
}
