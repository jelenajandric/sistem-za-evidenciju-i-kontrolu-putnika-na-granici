/**
 * StartinggTestAppServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.unibl.etf.communication;

public class StartinggTestAppServiceServiceLocator extends org.apache.axis.client.Service implements org.unibl.etf.communication.StartinggTestAppServiceService {

    public StartinggTestAppServiceServiceLocator() {
    }


    public StartinggTestAppServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public StartinggTestAppServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for StartinggTestAppService
    private java.lang.String StartinggTestAppService_address = "http://localhost:8080/ClientApplication/services/StartinggTestAppService";

    public java.lang.String getStartinggTestAppServiceAddress() {
        return StartinggTestAppService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String StartinggTestAppServiceWSDDServiceName = "StartinggTestAppService";

    public java.lang.String getStartinggTestAppServiceWSDDServiceName() {
        return StartinggTestAppServiceWSDDServiceName;
    }

    public void setStartinggTestAppServiceWSDDServiceName(java.lang.String name) {
        StartinggTestAppServiceWSDDServiceName = name;
    }

    public org.unibl.etf.communication.StartinggTestAppService getStartinggTestAppService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(StartinggTestAppService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getStartinggTestAppService(endpoint);
    }

    public org.unibl.etf.communication.StartinggTestAppService getStartinggTestAppService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.unibl.etf.communication.StartinggTestAppServiceSoapBindingStub _stub = new org.unibl.etf.communication.StartinggTestAppServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getStartinggTestAppServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setStartinggTestAppServiceEndpointAddress(java.lang.String address) {
        StartinggTestAppService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.unibl.etf.communication.StartinggTestAppService.class.isAssignableFrom(serviceEndpointInterface)) {
                org.unibl.etf.communication.StartinggTestAppServiceSoapBindingStub _stub = new org.unibl.etf.communication.StartinggTestAppServiceSoapBindingStub(new java.net.URL(StartinggTestAppService_address), this);
                _stub.setPortName(getStartinggTestAppServiceWSDDServiceName());
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
        if ("StartinggTestAppService".equals(inputPortName)) {
            return getStartinggTestAppService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://communication.etf.unibl.org", "StartinggTestAppServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://communication.etf.unibl.org", "StartinggTestAppService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("StartinggTestAppService".equals(portName)) {
            setStartinggTestAppServiceEndpointAddress(address);
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
