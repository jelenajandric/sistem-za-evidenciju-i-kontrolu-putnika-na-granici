package org.unibl.etf.soapservice;

public class SavingInfoAboutPersonServiceeProxy implements org.unibl.etf.soapservice.SavingInfoAboutPersonServicee {
  private String _endpoint = null;
  private org.unibl.etf.soapservice.SavingInfoAboutPersonServicee savingInfoAboutPersonServicee = null;
  
  public SavingInfoAboutPersonServiceeProxy() {
    _initSavingInfoAboutPersonServiceeProxy();
  }
  
  public SavingInfoAboutPersonServiceeProxy(String endpoint) {
    _endpoint = endpoint;
    _initSavingInfoAboutPersonServiceeProxy();
  }
  
  private void _initSavingInfoAboutPersonServiceeProxy() {
    try {
      savingInfoAboutPersonServicee = (new org.unibl.etf.soapservice.SavingInfoAboutPersonServiceeServiceLocator()).getSavingInfoAboutPersonServicee();
      if (savingInfoAboutPersonServicee != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)savingInfoAboutPersonServicee)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)savingInfoAboutPersonServicee)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (savingInfoAboutPersonServicee != null)
      ((javax.xml.rpc.Stub)savingInfoAboutPersonServicee)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.unibl.etf.soapservice.SavingInfoAboutPersonServicee getSavingInfoAboutPersonServicee() {
    if (savingInfoAboutPersonServicee == null)
      _initSavingInfoAboutPersonServiceeProxy();
    return savingInfoAboutPersonServicee;
  }
  
  public boolean savePerson(org.unibl.etf.model.Person person) throws java.rmi.RemoteException{
    if (savingInfoAboutPersonServicee == null)
      _initSavingInfoAboutPersonServiceeProxy();
    return savingInfoAboutPersonServicee.savePerson(person);
  }
  
  public org.unibl.etf.model.Person[] getRecordsOfChackedPersons() throws java.rmi.RemoteException{
    if (savingInfoAboutPersonServicee == null)
      _initSavingInfoAboutPersonServiceeProxy();
    return savingInfoAboutPersonServicee.getRecordsOfChackedPersons();
  }
  
  
}