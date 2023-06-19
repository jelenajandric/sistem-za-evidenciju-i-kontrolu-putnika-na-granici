/**
 * SavingInfoAboutPersonServiceeServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.unibl.etf.soapservice;

public class SavingInfoAboutPersonServiceeServiceLocator extends org.apache.axis.client.Service implements org.unibl.etf.soapservice.SavingInfoAboutPersonServiceeService {

    public SavingInfoAboutPersonServiceeServiceLocator() {
    }


    public SavingInfoAboutPersonServiceeServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SavingInfoAboutPersonServiceeServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SavingInfoAboutPersonServicee
    private java.lang.String SavingInfoAboutPersonServicee_address = "http://localhost:8080/CentralRegistry/services/SavingInfoAboutPersonServicee";

    public java.lang.String getSavingInfoAboutPersonServiceeAddress() {
        return SavingInfoAboutPersonServicee_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SavingInfoAboutPersonServiceeWSDDServiceName = "SavingInfoAboutPersonServicee";

    public java.lang.String getSavingInfoAboutPersonServiceeWSDDServiceName() {
        return SavingInfoAboutPersonServiceeWSDDServiceName;
    }

    public void setSavingInfoAboutPersonServiceeWSDDServiceName(java.lang.String name) {
        SavingInfoAboutPersonServiceeWSDDServiceName = name;
    }

    public org.unibl.etf.soapservice.SavingInfoAboutPersonServicee getSavingInfoAboutPersonServicee() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SavingInfoAboutPersonServicee_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSavingInfoAboutPersonServicee(endpoint);
    }

    public org.unibl.etf.soapservice.SavingInfoAboutPersonServicee getSavingInfoAboutPersonServicee(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.unibl.etf.soapservice.SavingInfoAboutPersonServiceeSoapBindingStub _stub = new org.unibl.etf.soapservice.SavingInfoAboutPersonServiceeSoapBindingStub(portAddress, this);
            _stub.setPortName(getSavingInfoAboutPersonServiceeWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSavingInfoAboutPersonServiceeEndpointAddress(java.lang.String address) {
        SavingInfoAboutPersonServicee_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.unibl.etf.soapservice.SavingInfoAboutPersonServicee.class.isAssignableFrom(serviceEndpointInterface)) {
                org.unibl.etf.soapservice.SavingInfoAboutPersonServiceeSoapBindingStub _stub = new org.unibl.etf.soapservice.SavingInfoAboutPersonServiceeSoapBindingStub(new java.net.URL(SavingInfoAboutPersonServicee_address), this);
                _stub.setPortName(getSavingInfoAboutPersonServiceeWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("SavingInfoAboutPersonServicee".equals(inputPortName)) {
            return getSavingInfoAboutPersonServicee();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://soapservice.etf.unibl.org", "SavingInfoAboutPersonServiceeService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://soapservice.etf.unibl.org", "SavingInfoAboutPersonServicee"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SavingInfoAboutPersonServicee".equals(portName)) {
            setSavingInfoAboutPersonServiceeEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
