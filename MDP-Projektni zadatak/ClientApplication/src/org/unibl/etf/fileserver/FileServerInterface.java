package org.unibl.etf.fileserver;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FileServerInterface extends Remote {

	public void saveFile(Integer[] readedBytes, int jmb, String fileName) throws RemoteException;
}
