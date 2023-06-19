/**
 * SomeStupidReceiverServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.unibl.etf.receiver;

public class SomeStupidReceiverServiceLocator extends org.apache.axis.client.Service implements org.unibl.etf.receiver.SomeStupidReceiverService {

    public SomeStupidReceiverServiceLocator() {
    }


    public SomeStupidReceiverServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SomeStupidReceiverServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SomeStupidReceiver
    private java.lang.String SomeStupidReceiver_address = "http://localhost:8080/CommunicationSOAPServer/services/SomeStupidReceiver";

    public java.lang.String getSomeStupidReceiverAddress() {
        return SomeStupidReceiver_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SomeStupidReceiverWSDDServiceName = "SomeStupidReceiver";

    public java.lang.String getSomeStupidReceiverWSDDServiceName() {
        return SomeStupidReceiverWSDDServiceName;
    }

    public void setSomeStupidReceiverWSDDServiceName(java.lang.String name) {
        SomeStupidReceiverWSDDServiceName = name;
    }

    public org.unibl.etf.receiver.SomeStupidReceiver getSomeStupidReceiver() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SomeStupidReceiver_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSomeStupidReceiver(endpoint);
    }

    public org.unibl.etf.receiver.SomeStupidReceiver getSomeStupidReceiver(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.unibl.etf.receiver.SomeStupidReceiverSoapBindingStub _stub = new org.unibl.etf.receiver.SomeStupidReceiverSoapBindingStub(portAddress, this);
            _stub.setPortName(getSomeStupidReceiverWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSomeStupidReceiverEndpointAddress(java.lang.String address) {
        SomeStupidReceiver_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.unibl.etf.receiver.SomeStupidReceiver.class.isAssignableFrom(serviceEndpointInterface)) {
                org.unibl.etf.receiver.SomeStupidReceiverSoapBindingStub _stub = new org.unibl.etf.receiver.SomeStupidReceiverSoapBindingStub(new java.net.URL(SomeStupidReceiver_address), this);
                _stub.setPortName(getSomeStupidReceiverWSDDServiceName());
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
        if ("SomeStupidReceiver".equals(inputPortName)) {
            return getSomeStupidReceiver();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://receiver.etf.unibl.org", "SomeStupidReceiverService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://receiver.etf.unibl.org", "SomeStupidReceiver"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SomeStupidReceiver".equals(portName)) {
            setSomeStupidReceiverEndpointAddress(address);
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
