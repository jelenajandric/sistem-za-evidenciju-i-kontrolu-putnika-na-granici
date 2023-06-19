package org.unibl.etf.soapservice;

public class TerminalServiceProxy implements org.unibl.etf.soapservice.TerminalService {
  private String _endpoint = null;
  private org.unibl.etf.soapservice.TerminalService terminalService = null;
  
  public TerminalServiceProxy() {
    _initTerminalServiceProxy();
  }
  
  public TerminalServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initTerminalServiceProxy();
  }
  
  private void _initTerminalServiceProxy() {
    try {
      terminalService = (new org.unibl.etf.soapservice.TerminalServiceServiceLocator()).getTerminalService();
      if (terminalService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)terminalService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)terminalService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (terminalService != null)
      ((javax.xml.rpc.Stub)terminalService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.unibl.etf.soapservice.TerminalService getTerminalService() {
    if (terminalService == null)
      _initTerminalServiceProxy();
    return terminalService;
  }
  
  public boolean createTerminal(org.unibl.etf.model.Terminal t, boolean creatingEnterancesAndExits) throws java.rmi.RemoteException{
    if (terminalService == null)
      _initTerminalServiceProxy();
    return terminalService.createTerminal(t, creatingEnterancesAndExits);
  }
  
  public org.unibl.etf.model.Terminal deleteTerminal(int id) throws java.rmi.RemoteException{
    if (terminalService == null)
      _initTerminalServiceProxy();
    return terminalService.deleteTerminal(id);
  }
  
  public boolean updateTerminal(org.unibl.etf.model.Terminal t) throws java.rmi.RemoteException{
    if (terminalService == null)
      _initTerminalServiceProxy();
    return terminalService.updateTerminal(t);
  }
  
  public org.unibl.etf.model.Terminal[] allTerminals() throws java.rmi.RemoteException{
    if (terminalService == null)
      _initTerminalServiceProxy();
    return terminalService.allTerminals();
  }
  
  public org.unibl.etf.model.Terminal getTerminal(int id) throws java.rmi.RemoteException{
    if (terminalService == null)
      _initTerminalServiceProxy();
    return terminalService.getTerminal(id);
  }
  
  public int isTerminalExisting(java.lang.String name) throws java.rmi.RemoteException{
    if (terminalService == null)
      _initTerminalServiceProxy();
    return terminalService.isTerminalExisting(name);
  }
  
  public boolean isEnteranceOrExitExists(java.lang.String enteranceOrExitId, int terminalId) throws java.rmi.RemoteException{
    if (terminalService == null)
      _initTerminalServiceProxy();
    return terminalService.isEnteranceOrExitExists(enteranceOrExitId, terminalId);
  }
  
  
}