package me.hugmanrique.buycraftapi.data;

import me.hugmanrique.buycraftapi.BuycraftApi;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by HugmanriqueMC. All Rights Reserved
 * The copy of this file may not be copied in any form without
 * the prior written permission of Hugo Manrique.
 *
 * @author Hugmanrique
 *         Spigot. Created the 14/05/2016.
 **/
public class Category {
    private int id;
    private int order;
    private String name;

    private boolean onlySubCategories;

    private Set<Category> subCategories;
    private Set<Package> packages;

    public Category(int id, int order, String name, Set<Category> subCategories) {
        this.id = id;
        this.order = order;
        this.name = name;
        this.subCategories = subCategories;
        this.packages = new HashSet<>();
        this.onlySubCategories = true;
    }

    public Category(int id, String name, int order, Set<Package> packages) {
        this.id = id;
        this.order = order;
        this.name = name;
        this.packages = packages;
        this.subCategories = new HashSet<>();
        onlySubCategories = false;
    }

    public Category(int id, int order, String name, Set<Category> subCategories, Set<Package> packages) {
        this.id = id;
        this.order = order;
        this.name = name;
        this.subCategories = subCategories;
        this.packages = packages;
        this.onlySubCategories = !subCategories.isEmpty()&&packages.isEmpty();
    }


    public int getId() {
        return id;
    }

    public int getOrder() {
        return order;
    }

    public String getName() {
        return name;
    }

    /**
     * Whether this {@link Category} only has child categories and none packages
     * @return if the {@link #getSubCategories()} is not empty and the {@link #getPackages()} is empty
     */
    public boolean isOnlySubCategories() {
        return onlySubCategories;
    }

    public Set<Category> getSubCategories() {
        return subCategories;
    }

    public Set<Package> getPackages() {
        return packages;
    }

    public Package getPackage(int id){
        return BuycraftApi.filterAndGet(packages.stream(), pack -> id==pack.getId());
    }

    public Package getPackage(String name){
        return BuycraftApi.filterAndGet(packages.stream(), pack -> name.equals(pack.getName()));
    }

    public Category getSubCategory(int id){
        return BuycraftApi.filterAndGet(subCategories.stream(), category -> id==category.getId());
    }

    public Category getSubCategory(String name){
        return BuycraftApi.filterAndGet(subCategories.stream(), category -> name.equals(category.getName()));
    }
}