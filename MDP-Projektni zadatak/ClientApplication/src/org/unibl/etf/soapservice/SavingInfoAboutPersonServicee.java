/**
 * SavingInfoAboutPersonServicee.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.unibl.etf.soapservice;

public interface SavingInfoAboutPersonServicee extends java.rmi.Remote {
    public boolean savePerson(org.unibl.etf.model.Person person) throws java.rmi.RemoteException;
    public org.unibl.etf.model.Person[] getRecordsOfChackedPersons() throws java.rmi.RemoteException;
}
