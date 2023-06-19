package org.unibl.etf.wantedlistregister;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RegistarPotjernicaInterface extends Remote{
	public boolean isThePersonOnTheWantedList(int jmb) throws RemoteException;
	public String getNameAndSurname(int jmb) throws RemoteException;

}
