package com.app.boink.util;

/*
 * Copyright 1997-2004 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */

import java.io.Serializable;
import java.lang.reflect.Array;

/**
 * A class that holds a list of EventListeners.  A single instance
 * can be used to hold all listeners (of all types) for the instance
 * using the list.  It is the responsiblity of the class using the
 * EventListenerList to provide type-safe API (preferably conforming
 * to the JavaBeans spec) and methods which dispatch event notification
 * methods to appropriate Event Listeners on the list.
 * <p/>
 * The main benefits that this class provides are that it is relatively
 * cheap in the case of no listeners, and it provides serialization for
 * event-listener lists in a single place, as well as a degree of MT safety
 * (when used correctly).
 * <p/>
 * Usage example:
 * Say one is defining a class that sends out FooEvents, and one wants
 * to allow users of the class to register FooListeners and receive
 * notification when FooEvents occur.  The following should be added
 * to the class definition:
 * <pre>
 * EventListenerList listenerList = new EventListenerList();
 * FooEvent fooEvent = null;
 *
 * public void addFooListener(FooListener l) {
 *     listenerList.add(FooListener.class, l);
 * }
 *
 * public void removeFooListener(FooListener l) {
 *     listenerList.remove(FooListener.class, l);
 * }
 *
 *
 * // Notify all listeners that have registered interest for
 * // notification on this event type.  The event instance
 * // is lazily created using the parameters passed into
 * // the fire method.
 *
 * protected void fireFooXXX() {
 *     // Guaranteed to return a non-null array
 *     Object[] listeners = listenerList.getListenerList();
 *     // Process the listeners last to first, notifying
 *     // those that are interested in this event
 *     for (int i = listeners.length-2; i>=0; i-=2) {
 *         if (listeners[i]==FooListener.class) {
 *             // Lazily create the event:
 *             if (fooEvent == null)
 *                 fooEvent = new FooEvent(this);
 *             ((FooListener)listeners[i+1]).fooXXX(fooEvent);
 *         }
 *     }
 * }
 * </pre>
 * foo should be changed to the appropriate name, and fireFooXxx to the
 * appropriate method name.  One fire method should exist for each
 * notification method in the FooListener interface.
 * <p/>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running
 * the same version of Swing.  As of 1.4, support for long term storage
 * of all JavaBeans<sup><font size="-2">TM</font></sup>
 * has been added to the <code>java.beans</code> package.
 *
 * @author Georges Saab
 * @author Hans Muller
 * @author James Gosling
 */
public final class EventListenerList implements Serializable {

    /* A null array to be shared by all empty listener lists*/
    private final static Object[] NULL_ARRAY = new Object[0];

    /* The list of ListenerType - Listener pairs */
    protected transient Object[] listenerList = NULL_ARRAY;

    /**
     * Passes back the event listener list as an array
     * of ListenerType-listener pairs.  Note that for
     * performance reasons, this implementation passes back
     * the actual data structure in which the listener data
     * is stored internally!
     * This method is guaranteed to pass back a non-null
     * array, so that no null-checking is required in
     * fire methods.  A zero-length array of Object should
     * be returned if there are currently no listeners.
     * <p/>
     * WARNING!!! Absolutely NO modification of
     * the data contained in this array should be made -- if
     * any such manipulation is necessary, it should be done
     * on a copy of the array returned rather than the array
     * itself.
     */
    public Object[] getListenerList() {
        return listenerList;
    }

    /**
     * Return an array of all the listeners of the given type.
     *
     * @return all of the listeners of the specified type.
     * @throws ClassCastException if the supplied class
     *                            is not assignable to EventListener
     * @since 1.3
     */
    public <T extends Object> T[] getListeners(T t) {

        Object[] lList = listenerList;
        T[] result = (T[]) Array.newInstance(t.getClass(), getListenerCount(lList, t));

        int j = 0;

        for (int i = lList.length - 2; i >= 0; i -= 2) {
            if (lList[i] == t)
                result[j++] = (T) lList[i + 1];
        }

        return result;
    }

    /**
     * Returns the total number of listeners for this listener list.
     */
    public int getListenerCount() {
        return listenerList.length / 2;
    }

    /**
     * Returns the total number of listeners of the supplied type
     * for this listener list.
     */
    public <T extends Object> int getListenerCount(T t) {

        Object[] lList = listenerList;
        return getListenerCount(lList, t);
    }

    private <T extends Object> int getListenerCount(Object[] list, T t) {

        int count = 0;

        for (int i = 0; i < list.length; i += 2) {
            if (t.equals(list[i]))
                count++;
        }

        return count;
    }

    /**
     * Adds the listener as a listener of the specified type.
     *
     * @param t the type of the listener to be added
     * @param l the listener to be added
     */
    public synchronized <T extends Object> void add(T t, T l) {

        if (l == null)
            throw new NullPointerException();

        if (t.equals(l)) {
            throw new IllegalArgumentException("Listener " + l + " is of type " + t);
        }

        if (listenerList == NULL_ARRAY) {

            // if this is the first listener added,
            // initialize the lists
            listenerList = new Object[]{t, l};

        } else {

            int i = listenerList.length;
            Object[] tmp = new Object[i + 2];

            // Check to see if the key ( l ) and the value ( t ) already exists
            for (int j = 0; j < i / 2; j += 2)
                if (tmp[j].equals(l) && tmp[j + 1].equals(t))
                    return;

            // Otherwise copy the array and add the new listener
            System.arraycopy(listenerList, 0, tmp, 0, i);
            listenerList = null;

            tmp[i] = t;
            tmp[i + 1] = l;

            listenerList = tmp;
        }
    }

    /**
     * Removes the listener as a listener of the specified type.
     *
     * @param t the type of the listener to be removed
     * @param l the listener to be removed
     */
    public synchronized <T extends Object> void remove(T t, T l) {

        if (l == null)
            throw new NullPointerException();

        if (t.equals(l)) {
            throw new IllegalArgumentException("Listener " + l + " is of type " + t);
        }
        // Is l on the list?
        int index = -1;
        for (int i = listenerList.length - 2; i >= 0; i -= 2) {
            if ((listenerList[i] == t) && (listenerList[i + 1].equals(l) == true)) {
                index = i;
                break;
            }
        }

        // If so,  remove it
        if (index != -1) {

            Object[] tmp = new Object[listenerList.length - 2];

            // Copy the list up to index
            System.arraycopy(listenerList, 0, tmp, 0, index);

            // Copy from two past the index, up to
            // the end of tmp (which is two elements
            // shorter than the old list)
            if (index < tmp.length)
                System.arraycopy(listenerList, index + 2, tmp, index, tmp.length - index);

            // set the listener array to the new array or null
            listenerList = (tmp.length == 0) ? NULL_ARRAY : tmp;
        }
    }

    /**
     * Returns a string representation of the EventListenerList.
     */
    public String toString() {

        Object[] lList = listenerList;
        String s = "EventListenerList: ";
        s += lList.length / 2 + " listeners: ";

        for (int i = 0; i <= lList.length - 2; i += 2) {
            s += " type " + ((Class) lList[i]).getName();
            s += " listener " + lList[i + 1];
        }
        return s;
    }
}