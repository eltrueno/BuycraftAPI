package me.hugmanrique.buycraftapi.data;

/**
 * Created by HugmanriqueMC. All Rights Reserved
 * The copy of this file may not be copied in any form without
 * the prior written permission of Hugo Manrique.
 *
 * @author Hugmanrique
 *         Spigot. Created the 14/05/2016.
 **/
public class Information {
    private Account account;

    private int serverId;
    private String serverName;

    private String analyticsProject;
    private String analyticsKey;

    public Information(Account account, int serverId, String serverName, String analyticsProject, String analyticsKey) {
        this.account = account;
        this.serverId = serverId;
        this.serverName = serverName;
        this.analyticsProject = analyticsProject;
        this.analyticsKey = analyticsKey;
    }

    public Account getAccount() {
        return account;
    }

    public int getServerId() {
        return serverId;
    }

    public String getServerName() {
        return serverName;
    }

    public String getAnalyticsProject() {
        return analyticsProject;
    }

    public String getAnalyticsKey() {
        return analyticsKey;
    }
}
