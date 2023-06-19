/**
 * StartinggTestAppService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.unibl.etf.communication;

public interface StartinggTestAppService extends java.rmi.Remote {
    public int isTerminalExist(java.lang.String terminalName) throws java.rmi.RemoteException;
    public boolean isEnteranceOrExitExistAndActive(java.lang.String entranceOrExitId, int terminalId) throws java.rmi.RemoteException;
}
