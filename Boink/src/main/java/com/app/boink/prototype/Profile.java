package com.app.boink.prototype;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by goof_troop on 9/12/13.
 */
public class Profile extends BoinkObject {

    private static final long serialVersionUID = 0;

    private String firstName;
    private String lastName;
    private String middleName;
    private String salutation;
    private String suffix;
    private String email;

    //encrypted
    private String password;

    private String userName;

    private HashMap<String, Task> taskList;

    public Profile() {

        super();

        taskList = new HashMap<String, Task>();
        uuid = UUID.randomUUID().toString();
    }

    public Profile(final String userName, final String password) {

        this();

        this.userName = userName;
        this.password = password;

    }

    public String getUUID() {
        return uuid;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {

        if (commonName == null) {
            // logger
            // exception
        }

        this.commonName = commonName;
    }

    public String getFirstName() {
        return firstName;
    }

    public boolean setFirstName(String firstName) {

        if (firstName == null) {
            // logger
            return false;
        }

        this.firstName = firstName;
        return true;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean setLastName(String lastName) {

        if (lastName == null) {
            // logger
            return false;
        }

        this.lastName = lastName;
        return true;
    }

    public String getMiddleName() {
        return middleName;
    }

    public boolean setMiddleName(String middleName) {

        if (middleName == null) {
            // logger
            return false;
        }

        this.middleName = middleName;
        return true;
    }

    public String getSalutation() {
        return salutation;
    }

    public boolean setSalutation(String salutation) {

        if (salutation == null) {
            // logger
            return false;
        }

        this.salutation = salutation;
        return true;
    }

    public String getSuffix() {
        return suffix;
    }

    public boolean setSuffix(String suffix) {

        if (suffix == null) {
            // logger
            return false;
        }

        this.suffix = suffix;
        return true;
    }

    public String getEmail() {
        return email;
    }

    public boolean setEmail(String email) {

        if (email == null) {
            // logger
            return false;
        }

        this.email = email;
        return true;
    }

    public String getPassword() {
        return password;
    }

    public boolean setPassword(String password) {

        if (password == null) {
            // logger
            return false;
        }

        this.password = password;
        return true;
    }

    public String getUserName() {
        return userName;
    }

    public boolean setUserName(String userName) {

        if (userName == null) {
            // logger
            return false;
        }

        this.userName = userName;
        return true;
    }

    public boolean update(String var, String value) {

        if ("firstName".equals(var))
            return setFirstName(value);
        else if ("lastName".equals(var))
            return setLastName(value);
        else if ("middleName".equals(var))
            return setMiddleName(value);
        else if ("salutation".equals(var))
            return setSalutation(value);
        else if ("suffix".equals(var))
            return setSuffix(value);
        else if ("email".equals(var))
            return setEmail(value);
        else if ("userName".equals(var))
            return setUserName(value);
        else
            return "password".equals(var) && setPassword(value);

    }
}
