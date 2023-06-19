package org.unibl.etf.communication;

public class StartinggTestAppServiceProxy implements org.unibl.etf.communication.StartinggTestAppService {
  private String _endpoint = null;
  private org.unibl.etf.communication.StartinggTestAppService startinggTestAppService = null;
  
  public StartinggTestAppServiceProxy() {
    _initStartinggTestAppServiceProxy();
  }
  
  public StartinggTestAppServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initStartinggTestAppServiceProxy();
  }
  
  private void _initStartinggTestAppServiceProxy() {
    try {
      startinggTestAppService = (new org.unibl.etf.communication.StartinggTestAppServiceServiceLocator()).getStartinggTestAppService();
      if (startinggTestAppService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)startinggTestAppService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)startinggTestAppService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (startinggTestAppService != null)
      ((javax.xml.rpc.Stub)startinggTestAppService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.unibl.etf.communication.StartinggTestAppService getStartinggTestAppService() {
    if (startinggTestAppService == null)
      _initStartinggTestAppServiceProxy();
    return startinggTestAppService;
  }
  
  public int isTerminalExist(java.lang.String terminalName) throws java.rmi.RemoteException{
    if (startinggTestAppService == null)
      _initStartinggTestAppServiceProxy();
    return startinggTestAppService.isTerminalExist(terminalName);
  }
  
  public boolean isEnteranceOrExitExistAndActive(java.lang.String entranceOrExitId, int terminalId) throws java.rmi.RemoteException{
    if (startinggTestAppService == null)
      _initStartinggTestAppServiceProxy();
    return startinggTestAppService.isEnteranceOrExitExistAndActive(entranceOrExitId, terminalId);
  }
  
  
}