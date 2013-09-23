package com.app.boink.model.data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Created by goof_troop on 9/12/13.
 */
public class Account extends BoinkObject {

    private static final long serialVersionUID = 0;

    private String accountNumber;
    private String accountName;

    private HashMap<Integer, Transaction> account;
    private LinkedList<Task> autoTasks;

    public Account() {

        super();

        account = new HashMap<Integer, Transaction>();
        autoTasks = new LinkedList<Task>();
        uuid = UUID.randomUUID().toString();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {

        if (accountNumber == null)
            throw new NullPointerException();

        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {

        if (accountName == null)
            throw new NullPointerException();

        this.accountName = accountName;
    }

    public void addTask(Task t) {

        if (t != null)
            autoTasks.add(t);
    }

    public Transaction getTransaction(int id) {

        return account.get(id);
    }

    public List<Transaction> getTransactionsByDate(String date) {

        if (date == null)
            throw new NullPointerException();

        return null;
    }

    public List<Transaction> getTransactionsBeforeDate(String date) {

        if (date == null)
            throw new NullPointerException();

        return null;
    }

    public List<Transaction> getTransactionsAfterDate(String date) {

        if (date == null)
            throw new NullPointerException();

        return null;
    }

    public List<Transaction> getTransactionsBeforeOnDate(String date) {

        if (date == null)
            throw new NullPointerException();

        return null;
    }

    public List<Transaction> getTransactionsAfterOnDate(String date) {

        if (date == null)
            throw new NullPointerException();

        return null;
    }

    public List<Transaction> getTransactionsByAmount(int amount) {

        return null;
    }

    public List<Transaction> getTransactionsByLesserAmount(int amount) {

        return null;
    }

    public List<Transaction> getTransactionsByGreaterAmount(int amount) {

        return null;
    }

    public List<Transaction> getTransactionsByLesserEqualsAmount(int amount) {

        return null;
    }

    public List<Transaction> getTransactionsByGreaterEqualsAmount(int amount) {

        return null;
    }

    public List<Transaction> getTransactionsByType(String type) {

        if (type == null)
            throw new NullPointerException();

        return null;
    }
}
