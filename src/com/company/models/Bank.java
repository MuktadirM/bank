package com.company.models;

import org.jetbrains.annotations.Nullable;

/**
 * Bank class
 */
public class Bank {
    private int id;
    private String name;
    private String address;
    private String phone;
    @Nullable
    private String website;

    /**
     * @param name Bank name
     * @param address Bank Address
     * @param phone Bank phone
     */
    public Bank(String name, String address, String phone,@Nullable String website) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.website = website;
    }

    /**
     * @param id Bank id
     * @param name Bank name
     * @param address Bank Address
     * @param phone Bank phone
     */
    public Bank(int id, String name, String address, String phone, @Nullable String website) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.website = website;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public @Nullable String getWebsite() {
        return website;
    }

    public void setWebsite(@Nullable String website) {
        this.website = website;
    }
}
