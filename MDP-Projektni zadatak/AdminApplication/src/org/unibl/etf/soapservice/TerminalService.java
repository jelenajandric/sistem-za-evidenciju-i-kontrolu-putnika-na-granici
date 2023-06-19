/**
 * TerminalService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.unibl.etf.soapservice;

public interface TerminalService extends java.rmi.Remote {
    public boolean createTerminal(org.unibl.etf.model.Terminal t, boolean creatingEnterancesAndExits) throws java.rmi.RemoteException;
    public org.unibl.etf.model.Terminal deleteTerminal(int id) throws java.rmi.RemoteException;
    public boolean updateTerminal(org.unibl.etf.model.Terminal t) throws java.rmi.RemoteException;
    public org.unibl.etf.model.Terminal[] allTerminals() throws java.rmi.RemoteException;
    public org.unibl.etf.model.Terminal getTerminal(int id) throws java.rmi.RemoteException;
    public int isTerminalExisting(java.lang.String name) throws java.rmi.RemoteException;
    public boolean isEnteranceOrExitExists(java.lang.String enteranceOrExitId, int terminalId) throws java.rmi.RemoteException;
}
