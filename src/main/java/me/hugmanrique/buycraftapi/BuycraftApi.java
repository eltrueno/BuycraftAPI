package me.hugmanrique.buycraftapi;

import me.hugmanrique.buycraftapi.data.*;
import me.hugmanrique.buycraftapi.data.Package;
import me.hugmanrique.buycraftapi.exception.BuycraftException;
import me.hugmanrique.buycraftapi.utils.JsonReader;
import me.hugmanrique.buycraftapi.utils.JsonUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Created by HugmanriqueMC. All Rights Reserved
 * The copy of this file may not be copied in any form without
 * the prior written permission of Hugo Manrique.
 *
 * @author Hugmanrique
 *         Spigot. Created the 14/05/2016.
 **/
public class BuycraftApi {
    private static String url;
    private String secret;

    private static final Pattern datePattern = Pattern.compile("^(\\d{1,4})-(\\d{1,2})-(\\d{1,2})T(\\d{1,2}):(\\d{1,2}):(\\d{1,2})\\+(\\d{1,4})$");

    /**
     * Creates a new instance of {@link BuycraftApi}
     * @param secret The Buycraft's secret key (The server secret key)
     * @throws BuycraftException If the Secret key is not valid
     */

    public BuycraftApi(String secret) throws BuycraftException {
        if (secret==null||secret.length()!=40){
            throw new BuycraftException("The secret key is not valid");
        }

        setSecure(true);
        this.secret = secret;
    }

    private JSONObject jsonGet(String path){
        try {
            return JsonReader.readJsonFromUrl(url + path, secret, false);
        } catch (IOException ignored) {}

        return null;
    }

    private JSONObject jsonArrayGet(String path){
        try {
            return JsonReader.readJsonFromUrl(url + path, secret, true);
        } catch (IOException ignored){}

        return null;
    }

    /**
     * Gets information about this store
     * @return An {@link Information} containing all the values
     */

    public Information getInformation() {
        try {
            JSONObject obj = jsonGet("/information");

            checkError(obj);

            JSONObject account = JsonUtils.safeGetObject(obj, "account");
            JSONObject currency = JsonUtils.safeGetObject(account, "currency");

            JSONObject serverObj = JsonUtils.safeGetObject(obj, "server");

            int serverId = JsonUtils.safeGetInt(serverObj, "id");
            String serverName = JsonUtils.safeGetString(serverObj, "name");

            //Analytics (Never heard of this, but including it... :S)
            JSONObject analytics = JsonUtils.safeGetObject(JsonUtils.safeGetObject(obj, "analytics"), "internal");

            String projectAna = JsonUtils.safeGetString(analytics, "project");
            String anaKey = JsonUtils.safeGetString(analytics, "key");

            return new Information(new Account(account, currency), serverId, serverName, projectAna, anaKey);
        } catch (BuycraftException e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Gets all the Categories and Packages your store has
     * @return A {@link Set<Category>} containing all the Categories (also subcategories)
     * @see {@link Category#getSubCategories()} To see the Subcategories of a {@link Category}
     */

    public Set<Category> getListing(){
        try {
            JSONObject obj = jsonGet("/listing");
            checkError(obj);

            JSONArray catArray = JsonUtils.safeGetArray(obj, "categories");
            return loopCategory(catArray);
        } catch (BuycraftException e){
            e.printStackTrace();
        }

        return null;
    }


    private Set<Category> loopCategory(JSONArray array){
        if (array==null){
            return new HashSet<>();
        }
        Set<Category> categories = new HashSet<>();

        for (int i = 0; i<array.length(); i++){
            JSONObject object = JsonUtils.safeGetObject(array, i);
            Category category = parseCategory(object);
            categories.add(category);
        }

        return categories;
    }

    private Category parseCategory(JSONObject obj){
        int id = JsonUtils.safeGetInt(obj, "id");
        int order = JsonUtils.safeGetInt(obj, "order");
        String name = JsonUtils.safeGetString(obj, "name");
        Set<Category> childs = loopCategory(JsonUtils.safeGetArray(obj, "subcategories"));
        Set<Package> packages = parsePackages(obj);

        return new Category(id, order, name, childs, packages);
    }

    private Set<Package> parsePackages(JSONObject object){
        JSONArray array = JsonUtils.safeGetArray(object, "packages");
        Set<Package> packages = new HashSet<>();
        if (array==null){
            return packages;
        }

        for (int i = 0; i < array.length(); i++){
            packages.add(parsePackage(JsonUtils.safeGetObject(array, i)));
        }

        return packages;
    }

    private Package parsePackage(JSONObject object){
        int id = JsonUtils.safeGetInt(object, "id");
        int order = JsonUtils.safeGetInt(object, "order");
        String name = JsonUtils.safeGetString(object, "name");
        double price = Double.parseDouble(JsonUtils.safeGetString(object, "price"));

        JSONObject sale = JsonUtils.safeGetObject(object, "sale");

        boolean saleActive = JsonUtils.safeGetBoolean(sale, "active");
        double discount = Double.parseDouble(JsonUtils.safeGetString(sale, "discount"));

        return new Package(id, order, name, price, saleActive, discount);
    }

    /**
     * Gets a List of queued commands
     * @param page The page number
     * @return A {@link PlayerQueue} containing some settings and all the commands
     */

    public PlayerQueue getQueue(int page){
        try {
            JSONObject obj = jsonGet("/queue?page=" + page);
            checkError(obj);

            JSONObject meta = JsonUtils.safeGetObject(obj, "meta");

            return new PlayerQueue(JsonUtils.safeGetBoolean(meta, "execute_offline"), JsonUtils.safeGetInt(obj, "next_check"), JsonUtils.safeGetBoolean(obj, "more"));
        } catch (BuycraftException e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Gets a {@link Set<OfflineCommand>} of Offline commands that were executed
     * @return A {@link Set<OfflineCommand>} containing all the executed offline commands
     */

    public Set<OfflineCommand> getOfflineCommands(){
        try {
            JSONObject obj = jsonGet("/queue/offline-commands");
            checkError(obj);

            JSONArray array = JsonUtils.safeGetArray(obj, "commands");
            Set<OfflineCommand> offlineCommands = new HashSet<>();

            for (int i = 0; i < (array==null ? 0 : array.length()); i++){
                offlineCommands.add(parseOfflineCmd(JsonUtils.safeGetObject(array, i)));
            }

            return offlineCommands;
        } catch (BuycraftException e){
            e.printStackTrace();
        }

        return null;
    }

    private OfflineCommand parseOfflineCmd(JSONObject obj){
        int id = JsonUtils.safeGetInt(obj, "id");
        String cmd = JsonUtils.safeGetString(obj, "command");
        String payment = JsonUtils.safeGetString(obj, "payment");
        String packageId = JsonUtils.safeGetString(obj, "package");
        int delay = JsonUtils.safeGetInt(JsonUtils.safeGetObject(obj, "conditions"), "delay");

        JSONObject player = JsonUtils.safeGetObject(obj, "player");
        int playerId = JsonUtils.safeGetInt(player, "id");
        String playerName = JsonUtils.safeGetString(player, "name");

        String uuidString = JsonUtils.safeGetString(player, "uuid");
        UUID uuid = uuidString==null||uuidString.isEmpty() ? null : UUID.fromString(uuidString);

        return new OfflineCommand(id, cmd, payment, packageId, delay, playerId, playerName, uuid);
    }

    /**
     * Gets a list of the latest payments in a {@link Payment} package way
     * @param limit The number of {@link Payment}s to retrieve. Buycraft limits this to 100
     * @return {@link Set<Payment>} containing all the payments
     */

    public Set<Payment> getLatestPayments(int limit){
        try {
            JSONObject obj = jsonArrayGet("/payments" + (limit == -1 ? "" : "?limit=" + limit));
            checkError(obj);

            JSONArray payments = JsonUtils.safeGetArray(obj, "main");
            Set<Payment> paymentSet = new HashSet<>();

            for (int i = 0; i<(payments==null ? 0 : payments.length()); i++){
                paymentSet.add(parsePayment(JsonUtils.safeGetObject(payments, i)));
            }

            return paymentSet;
        } catch (BuycraftException e){
            e.printStackTrace();
        }

        return null;
    }

    private Payment parsePayment(JSONObject obj){
        int id = JsonUtils.safeGetInt(obj, "id");
        double amount = Double.parseDouble(JsonUtils.safeGetString(obj, "amount"));

        Date date = parseDate(JsonUtils.safeGetString(obj, "date"));

        JSONObject currencyObj = JsonUtils.safeGetObject(obj, "currency");
        String currency = JsonUtils.safeGetString(currencyObj, "iso_4217");
        String currencySymbol = JsonUtils.safeGetString(currencyObj, "symbol");

        JSONObject playerObj = JsonUtils.safeGetObject(obj, "player");
        int playerId = JsonUtils.safeGetInt(playerObj, "id");
        String playerName = JsonUtils.safeGetString(playerObj, "name");

        String uuidString = JsonUtils.safeGetString(playerObj, "uuid");
        UUID uuid = uuidString==null||uuidString.isEmpty() ? null : UUID.fromString(uuidString);

        JSONArray packages = JsonUtils.safeGetArray(obj, "packages");

        return new Payment(id, amount, date, currency, currencySymbol, playerId, playerName, uuid, getBoughtPackages(packages));
    }

    private Map<Integer, String> getBoughtPackages(JSONArray array){
        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i<(array==null ? 0 : array.length()); i++){
            JSONObject obj = JsonUtils.safeGetObject(array, i);
            map.put(JsonUtils.safeGetInt(obj, "id"), JsonUtils.safeGetString(obj, "name"));
        }

        return map;
    }

    private void checkError(JSONObject obj) throws BuycraftException {
        if (obj == null){
            throw new BuycraftException("Couldn't connect to the Buycraft API");
        }
        if (JsonUtils.safeGetInt(obj, "error_code", false)!=null){
            throw new BuycraftException(JsonUtils.safeGetString(obj, "error_message"));
        }
    }

    /**
     * Utility method to parse Buycraft's date objects
     * @param string The string to parse
     * @return A Date with the parsed string data or a "0" Date
     */

    public Date parseDate(String string){
        int years = 0;
        int month = 0;
        int day = 0;
        int hour = 0;
        int minute = 0;
        int second = 0;
        boolean found = false;

        Matcher m = datePattern.matcher(string);

        while (m.find()){
            if (m.group()!=null){
                if (m.group().isEmpty()){
                    continue;
                }

                for (int i = 0; i<m.groupCount(); ++i){
                    if (m.group(i)!=null&&!m.group(i).isEmpty()){
                        found = true;
                        break;
                    }
                }

                if (!found){
                    continue;
                }

                if (matcherCheck(m, 1)){
                    years = Integer.parseInt(m.group(1));
                }
                if (matcherCheck(m, 2)){
                    month = Integer.parseInt(m.group(2));
                }
                if (matcherCheck(m, 3)){
                    day = Integer.parseInt(m.group(3));
                }
                if (matcherCheck(m, 4)){
                    hour = Integer.parseInt(m.group(4));
                }
                if (matcherCheck(m, 5)){
                    minute = Integer.parseInt(m.group(5));
                }
                if (matcherCheck(m, 6)){
                    second = Integer.parseInt(m.group(6));
                }
            }
        }

        Calendar c = Calendar.getInstance();
        c.set(years, month+1, day, hour, minute, second);
        return new Date(c.toInstant().toEpochMilli());
    }

    private boolean matcherCheck(Matcher matcher, int index){
        return matcher.group(index)!=null&&!matcher.group(index).isEmpty();
    }

    public void setSecure(boolean secure){
        url = "http" + (secure ? "s" : "") + "://plugin.buycraft.net";
    }

    public static <T> T filterAndGet(Stream<T> stream, Predicate<T> predicate){
        try {
            return stream.filter(predicate).findFirst().get();
        } catch (NoSuchElementException e){
            return null;
        }
    }


}
