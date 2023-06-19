/**
 * DataSenderr.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.unibl.etf.sender;

public interface DataSenderr extends java.rmi.Remote {
    public int daLiPostojiTerminal(java.lang.String terminalName) throws java.rmi.RemoteException;
    public void dodajPodatkeZaDodavanjeFajlovaa(java.lang.String idUlazaIzlaza, int jmb, int[] podaci, java.lang.String filename) throws java.rmi.RemoteException;
    public boolean daLiPostojiAktivanUlazIzlaz(java.lang.String idUlazaIzlaza, int idTerminala) throws java.rmi.RemoteException;
    public java.lang.String imaLiStaZaTestnuAplikaciju(int jmb) throws java.rmi.RemoteException;
    public void dodajPodatkeZaSlanjeNaCarinskuKontrolu(java.lang.String idUlazaIzlaza, int jmb, java.lang.String ime, java.lang.String prezime, boolean daLiJeNaPotjernici) throws java.rmi.RemoteException;
    public void dodajPodatkeZaSlanjeNaPolicijskuKontrolu(java.lang.String idUlazaIzlaza, int jmb, java.lang.String ime, java.lang.String prezime) throws java.rmi.RemoteException;
}
