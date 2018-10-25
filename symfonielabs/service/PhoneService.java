package com.spectra.symfonielabs.service;

import javax.telephony.Address;
import javax.telephony.Call;
import javax.telephony.CallListener;
import javax.telephony.JtapiPeer;
import javax.telephony.JtapiPeerFactory;
import javax.telephony.PlatformException;
import javax.telephony.Provider;
import javax.telephony.ProviderEvent;
import javax.telephony.ProviderListener;
import javax.telephony.Terminal;
import javax.telephony.TerminalConnection;

import sunw.io.Serializable;

import com.avaya.jtapi.tsapi.adapters.ProviderListenerAdapter;
import com.spectra.symfonielabs.domainobject.PhoneCallData;

public class PhoneService  extends ProviderListenerAdapter implements java.io.Serializable  {
	private PhoneCallData data;

	private Provider provider;

	private Terminal terminal;
	
	private CallListener listener;
	
	public  PhoneService(PhoneCallData data, CallListener listener) {
		this.data = data;
		this.listener = listener;
	}

	public String[] getServices() throws Exception {
		try {
			// create JtapiPeer
			final JtapiPeer jtapiPeer = JtapiPeerFactory
					.getJtapiPeer(isNull(data.getPeer()) ? null : data.getPeer());
			return jtapiPeer.getServices();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void getProvider() throws Exception {
		try {
			String providerString = data.getService() + ";loginID=" + data.getLogin()
				+ ";passwd=" + data.getPassword();
			// create JtapiPeer
			JtapiPeer jtapiPeer = JtapiPeerFactory
					.getJtapiPeer(isNull(data.getPeer()) ? null : data.getPeer());

			// create Provider
			provider = jtapiPeer.getProvider(providerString);
			// add a ProviderListener to the Provider to be notified when it is
			// in service
			provider.addProviderListener(this);

			// wait to be notified when the Provider is in service --
			// corresponding notify is in the providerChangedEvent() method
			synchronized (this) {
				wait();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private boolean isNull(String peer) {
		return (peer == null || "".equals(peer));
	}
	
	public void providerInService(ProviderEvent event)
	{
		synchronized (this) {
			notify(); // registerRouteCallback() is waiting on this
						// event
		}
	}

	public void makeCall() {
		Address address = null;
		Call call = null;

		if (provider != null) {
			try {
				try {
					// In order to make a call, we need to obtain an Address and
					// a Terminal
					// object that represent the dialing extension. In Avaya's
					// implementation
					// of JTAPI, there is a one-to-one relationship between the
					// Terminal and
					// Address objects that represent an extension number.

					// create Address
					address = provider.getAddress(data.getCaller());
					terminal = provider.getTerminal(data.getCaller());
				} catch (Exception e) {
					e.printStackTrace();
					e.printStackTrace();
					throw (e);
				}
				try {
					// add calllistener to terminal
					terminal.addCallListener(listener);

					// create Call
					call = provider.createCall();
				} catch (Exception e) {
					e.printStackTrace();
					throw (e);
					
				}
				try {
					// makecall by using the connect method of the Call object
					call.connect(terminal, address, data.getCallee());
				} catch (Exception e) {
					System.out.println("exception \nPlease verify that the phone that you're " +
							"dialing from is ready to initiate calls.\n\n");
					e.printStackTrace();
					throw (e);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		} else {
			System.out.println("Unable to make call - provider was not created " +
					"successfully.\n\n");
		}
		return;
	}

	/**
	 * This method disconnects the specified terminalConnection.
	 */
	public void hangup() { 
		if ( (terminal.getTerminalConnections()) != null) {
			TerminalConnection terminalConnection = terminal.getTerminalConnections()[0];
			try {
				// drop existing connection at the terminal
				if (terminalConnection != null) {
					terminalConnection.getConnection().disconnect();
				}		 
			}
				catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
	}

	/**
	 * This method removes the CallListener on the Terminal and shuts down the
	 * Provider.
	 */
	public synchronized void shutProvider() {
		CallListener[] callListener = null;
		ProviderListener[] providerListener = null;
		if (provider != null) {

			if (provider.getState() == Provider.IN_SERVICE) {
				if ((providerListener = provider.getProviderListeners()) != null) {
					provider.removeProviderListener(providerListener[0]);
				}

				if ((callListener = terminal.getCallListeners()) != null) {
					terminal.removeCallListener(callListener[0]);
				}
				try {
					provider.shutdown();
				} catch (PlatformException e) {
					e.printStackTrace();
				}
			}
			provider = null;
		}
	}
	public void updateData(PhoneCallData testData) {
		this.data = testData;
	}
	public void shutdown(){
		if (provider!=null){
			provider.shutdown();
			provider = null;
		}
	}
}
