package com.app.boink.model.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by goof_troop on 9/12/13.
 */
public class Profile extends BoinkObject  {

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

    public String getUUID() {
        return uuid;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {

        if (commonName == null) {
            // logger
            throw new NullPointerException();
        }

        this.commonName = commonName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {

        if (firstName == null) {
            // logger
            throw new NullPointerException();
        }

        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {

        if (lastName == null) {
            // logger
            throw new NullPointerException();
        }

        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {

        if (middleName == null) {
            // logger
            throw new NullPointerException();
        }

        this.middleName = middleName;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {

        if (salutation == null) {
            // logger
            throw new NullPointerException();
        }

        this.salutation = salutation;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {

        if (suffix == null) {
            // logger
            throw new NullPointerException();
        }

        this.suffix = suffix;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {

        if (email == null) {
            // logger
            throw new NullPointerException();
        }

        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {

        if (password == null) {
            // logger
            throw new NullPointerException();
        }

        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {

        if (userName == null) {
            // logger
            throw new NullPointerException();
        }

        this.userName = userName;
    }

    public void update(String var, String value) {

        if (var == null) {
            // logger
            throw new NullPointerException();
        }

        if ("firstName".equals(var)) {
            setFirstName(value);
        } else if ("lastName".equals(var)) {
            setLastName(value);
        } else if ("middleName".equals(var)) {
            setMiddleName(value);
        } else if ("salutation".equals(var)) {
            setSalutation(value);
        } else if ("suffix".equals(var)) {
            setSuffix(value);
        } else if ("email".equals(var)) {
            setEmail(value);
        } else if ("userName".equals(var)) {
            setUserName(value);
        } else if ("password".equals(var)) {
            setPassword(value);
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
