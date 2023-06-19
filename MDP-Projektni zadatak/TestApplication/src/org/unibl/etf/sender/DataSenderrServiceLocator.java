/**
 * DataSenderrServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.unibl.etf.sender;

public class DataSenderrServiceLocator extends org.apache.axis.client.Service implements org.unibl.etf.sender.DataSenderrService {

    public DataSenderrServiceLocator() {
    }


    public DataSenderrServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public DataSenderrServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for DataSenderr
    private java.lang.String DataSenderr_address = "http://localhost:8080/CommunicationSOAPServer/services/DataSenderr";

    public java.lang.String getDataSenderrAddress() {
        return DataSenderr_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String DataSenderrWSDDServiceName = "DataSenderr";

    public java.lang.String getDataSenderrWSDDServiceName() {
        return DataSenderrWSDDServiceName;
    }

    public void setDataSenderrWSDDServiceName(java.lang.String name) {
        DataSenderrWSDDServiceName = name;
    }

    public org.unibl.etf.sender.DataSenderr getDataSenderr() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(DataSenderr_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getDataSenderr(endpoint);
    }

    public org.unibl.etf.sender.DataSenderr getDataSenderr(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.unibl.etf.sender.DataSenderrSoapBindingStub _stub = new org.unibl.etf.sender.DataSenderrSoapBindingStub(portAddress, this);
            _stub.setPortName(getDataSenderrWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setDataSenderrEndpointAddress(java.lang.String address) {
        DataSenderr_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.unibl.etf.sender.DataSenderr.class.isAssignableFrom(serviceEndpointInterface)) {
                org.unibl.etf.sender.DataSenderrSoapBindingStub _stub = new org.unibl.etf.sender.DataSenderrSoapBindingStub(new java.net.URL(DataSenderr_address), this);
                _stub.setPortName(getDataSenderrWSDDServiceName());
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
        if ("DataSenderr".equals(inputPortName)) {
            return getDataSenderr();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://sender.etf.unibl.org", "DataSenderrService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://sender.etf.unibl.org", "DataSenderr"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("DataSenderr".equals(portName)) {
            setDataSenderrEndpointAddress(address);
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
