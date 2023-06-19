package org.unibl.etf.sender;

public class DataSenderrProxy implements org.unibl.etf.sender.DataSenderr {
  private String _endpoint = null;
  private org.unibl.etf.sender.DataSenderr dataSenderr = null;
  
  public DataSenderrProxy() {
    _initDataSenderrProxy();
  }
  
  public DataSenderrProxy(String endpoint) {
    _endpoint = endpoint;
    _initDataSenderrProxy();
  }
  
  private void _initDataSenderrProxy() {
    try {
      dataSenderr = (new org.unibl.etf.sender.DataSenderrServiceLocator()).getDataSenderr();
      if (dataSenderr != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)dataSenderr)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)dataSenderr)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (dataSenderr != null)
      ((javax.xml.rpc.Stub)dataSenderr)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.unibl.etf.sender.DataSenderr getDataSenderr() {
    if (dataSenderr == null)
      _initDataSenderrProxy();
    return dataSenderr;
  }
  
  public int daLiPostojiTerminal(java.lang.String terminalName) throws java.rmi.RemoteException{
    if (dataSenderr == null)
      _initDataSenderrProxy();
    return dataSenderr.daLiPostojiTerminal(terminalName);
  }
  
  public void dodajPodatkeZaDodavanjeFajlovaa(java.lang.String idUlazaIzlaza, int jmb, int[] podaci, java.lang.String filename) throws java.rmi.RemoteException{
    if (dataSenderr == null)
      _initDataSenderrProxy();
    dataSenderr.dodajPodatkeZaDodavanjeFajlovaa(idUlazaIzlaza, jmb, podaci, filename);
  }
  
  public boolean daLiPostojiAktivanUlazIzlaz(java.lang.String idUlazaIzlaza, int idTerminala) throws java.rmi.RemoteException{
    if (dataSenderr == null)
      _initDataSenderrProxy();
    return dataSenderr.daLiPostojiAktivanUlazIzlaz(idUlazaIzlaza, idTerminala);
  }
  
  public java.lang.String imaLiStaZaTestnuAplikaciju(int jmb) throws java.rmi.RemoteException{
    if (dataSenderr == null)
      _initDataSenderrProxy();
    return dataSenderr.imaLiStaZaTestnuAplikaciju(jmb);
  }
  
  public void dodajPodatkeZaSlanjeNaCarinskuKontrolu(java.lang.String idUlazaIzlaza, int jmb, java.lang.String ime, java.lang.String prezime, boolean daLiJeNaPotjernici) throws java.rmi.RemoteException{
    if (dataSenderr == null)
      _initDataSenderrProxy();
    dataSenderr.dodajPodatkeZaSlanjeNaCarinskuKontrolu(idUlazaIzlaza, jmb, ime, prezime, daLiJeNaPotjernici);
  }
  
  public void dodajPodatkeZaSlanjeNaPolicijskuKontrolu(java.lang.String idUlazaIzlaza, int jmb, java.lang.String ime, java.lang.String prezime) throws java.rmi.RemoteException{
    if (dataSenderr == null)
      _initDataSenderrProxy();
    dataSenderr.dodajPodatkeZaSlanjeNaPolicijskuKontrolu(idUlazaIzlaza, jmb, ime, prezime);
  }
  
  
}