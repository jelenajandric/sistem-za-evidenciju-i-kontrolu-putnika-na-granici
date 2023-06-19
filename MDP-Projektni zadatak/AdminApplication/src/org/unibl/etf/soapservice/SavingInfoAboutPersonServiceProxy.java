package org.unibl.etf.soapservice;

public class SavingInfoAboutPersonServiceProxy implements org.unibl.etf.soapservice.SavingInfoAboutPersonService {
  private String _endpoint = null;
  private org.unibl.etf.soapservice.SavingInfoAboutPersonService savingInfoAboutPersonService = null;
  
  public SavingInfoAboutPersonServiceProxy() {
    _initSavingInfoAboutPersonServiceProxy();
  }
  
  public SavingInfoAboutPersonServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initSavingInfoAboutPersonServiceProxy();
  }
  
  private void _initSavingInfoAboutPersonServiceProxy() {
    try {
      savingInfoAboutPersonService = (new org.unibl.etf.soapservice.SavingInfoAboutPersonServiceServiceLocator()).getSavingInfoAboutPersonService();
      if (savingInfoAboutPersonService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)savingInfoAboutPersonService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)savingInfoAboutPersonService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (savingInfoAboutPersonService != null)
      ((javax.xml.rpc.Stub)savingInfoAboutPersonService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.unibl.etf.soapservice.SavingInfoAboutPersonService getSavingInfoAboutPersonService() {
    if (savingInfoAboutPersonService == null)
      _initSavingInfoAboutPersonServiceProxy();
    return savingInfoAboutPersonService;
  }
  
  public boolean savePerson(org.unibl.etf.model.Person person) throws java.rmi.RemoteException{
    if (savingInfoAboutPersonService == null)
      _initSavingInfoAboutPersonServiceProxy();
    return savingInfoAboutPersonService.savePerson(person);
  }
  
  public org.unibl.etf.model.Person[] getRecordsOfChackedPersons() throws java.rmi.RemoteException{
    if (savingInfoAboutPersonService == null)
      _initSavingInfoAboutPersonServiceProxy();
    return savingInfoAboutPersonService.getRecordsOfChackedPersons();
  }
  
  
}