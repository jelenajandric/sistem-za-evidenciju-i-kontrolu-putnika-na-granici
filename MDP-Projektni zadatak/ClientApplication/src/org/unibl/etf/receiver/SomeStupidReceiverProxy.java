package org.unibl.etf.receiver;

public class SomeStupidReceiverProxy implements org.unibl.etf.receiver.SomeStupidReceiver {
  private String _endpoint = null;
  private org.unibl.etf.receiver.SomeStupidReceiver someStupidReceiver = null;
  
  public SomeStupidReceiverProxy() {
    _initSomeStupidReceiverProxy();
  }
  
  public SomeStupidReceiverProxy(String endpoint) {
    _endpoint = endpoint;
    _initSomeStupidReceiverProxy();
  }
  
  private void _initSomeStupidReceiverProxy() {
    try {
      someStupidReceiver = (new org.unibl.etf.receiver.SomeStupidReceiverServiceLocator()).getSomeStupidReceiver();
      if (someStupidReceiver != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)someStupidReceiver)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)someStupidReceiver)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (someStupidReceiver != null)
      ((javax.xml.rpc.Stub)someStupidReceiver)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.unibl.etf.receiver.SomeStupidReceiver getSomeStupidReceiver() {
    if (someStupidReceiver == null)
      _initSomeStupidReceiverProxy();
    return someStupidReceiver;
  }
  
  public void dodajOdgovorOdPolicijskeKontrole(int jmb, java.lang.String idUlazaIzlaza, boolean daLiProlaziPolicijskuKontrolu) throws java.rmi.RemoteException{
    if (someStupidReceiver == null)
      _initSomeStupidReceiverProxy();
    someStupidReceiver.dodajOdgovorOdPolicijskeKontrole(jmb, idUlazaIzlaza, daLiProlaziPolicijskuKontrolu);
  }
  
  public void dodajOdgovorOdDodavanjaFajlaa(int jmb, java.lang.String idUlazaIzlaza, boolean daLiJeDodatFajl) throws java.rmi.RemoteException{
    if (someStupidReceiver == null)
      _initSomeStupidReceiverProxy();
    someStupidReceiver.dodajOdgovorOdDodavanjaFajlaa(jmb, idUlazaIzlaza, daLiJeDodatFajl);
  }
  
  public void dodajOdgovorOdCarinskeKontrole(int jmb, java.lang.String idUlazaIzlaza, boolean daLiProlaziCarinskuKontrolu) throws java.rmi.RemoteException{
    if (someStupidReceiver == null)
      _initSomeStupidReceiverProxy();
    someStupidReceiver.dodajOdgovorOdCarinskeKontrole(jmb, idUlazaIzlaza, daLiProlaziCarinskuKontrolu);
  }
  
  public void dodajOdgovorKadJeObustavaSaobracaja(int jmb, java.lang.String idUlazaIzlaza, java.lang.String poruka) throws java.rmi.RemoteException{
    if (someStupidReceiver == null)
      _initSomeStupidReceiverProxy();
    someStupidReceiver.dodajOdgovorKadJeObustavaSaobracaja(jmb, idUlazaIzlaza, poruka);
  }
  
  public java.lang.String imaLiStaZaPolicijskuKontrolu(java.lang.String idITipUlazaIzlaza) throws java.rmi.RemoteException{
    if (someStupidReceiver == null)
      _initSomeStupidReceiverProxy();
    return someStupidReceiver.imaLiStaZaPolicijskuKontrolu(idITipUlazaIzlaza);
  }
  
  public java.lang.String imaLiStaZaCarinskuKontrolu(java.lang.String idITipUlazaIzlaza) throws java.rmi.RemoteException{
    if (someStupidReceiver == null)
      _initSomeStupidReceiverProxy();
    return someStupidReceiver.imaLiStaZaCarinskuKontrolu(idITipUlazaIzlaza);
  }
  
  
}