package com.spectra.symfonielabs.activedirectory;
import javax.xml.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class GetProperties {

	public   Properties getProperties() {
		///////////Reading properties from xml/////////////////
		Properties prop = new Properties();
		try { 		
			InputStream in = getClass().getResourceAsStream("/activeDir.xml");	    
			prop.loadFromXML(in);	
			}
		  catch (IOException e) {
			e.printStackTrace();
		}
	return prop;

	}
}