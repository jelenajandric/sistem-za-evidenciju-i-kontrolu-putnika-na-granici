/**
 * SomeStupidReceiver.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.unibl.etf.receiver;

public interface SomeStupidReceiver extends java.rmi.Remote {
    public java.lang.String imaLiStaZaPolicijskuKontrolu(java.lang.String idITipUlazaIzlaza) throws java.rmi.RemoteException;
    public java.lang.String imaLiStaZaCarinskuKontrolu(java.lang.String idITipUlazaIzlaza) throws java.rmi.RemoteException;
    public void dodajOdgovorOdPolicijskeKontrole(int jmb, java.lang.String idUlazaIzlaza, boolean daLiProlaziPolicijskuKontrolu) throws java.rmi.RemoteException;
    public void dodajOdgovorOdDodavanjaFajlaa(int jmb, java.lang.String idUlazaIzlaza, boolean daLiJeDodatFajl) throws java.rmi.RemoteException;
    public void dodajOdgovorOdCarinskeKontrole(int jmb, java.lang.String idUlazaIzlaza, boolean daLiProlaziCarinskuKontrolu) throws java.rmi.RemoteException;
    public void dodajOdgovorKadJeObustavaSaobracaja(int jmb, java.lang.String idUlazaIzlaza, java.lang.String poruka) throws java.rmi.RemoteException;
}
