package me.hugmanrique.buycraftapi.data;

/**
 * Created by HugmanriqueMC. All Rights Reserved
 * The copy of this file may not be copied in any form without
 * the prior written permission of Hugo Manrique.
 *
 * @author Hugmanrique
 *         Spigot. Created the 14/05/2016.
 **/
public class PlayerQueue {
    private boolean executeOffline;
    private int nextCheck;
    private boolean more;

    //TODO Try to see how a Player object is

    public PlayerQueue(boolean executeOffline, int nextCheck, boolean more) {
        this.executeOffline = executeOffline;
        this.nextCheck = nextCheck;
        this.more = more;
    }

    public boolean isExecuteOffline() {
        return executeOffline;
    }

    public int getNextCheck() {
        return nextCheck;
    }

    public boolean isMore() {
        return more;
    }
}
