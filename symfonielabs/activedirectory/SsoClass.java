package com.spectra.symfonielabs.activedirectory;

/** *********************************************************
 * Process SSO (Single Sign On, Active Directoty / LDAP)
 * 
 *   @version March  12, 2017
 * 
 * **********************************************************
 */

import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

public class SsoClass  {
	String app;	
	Attributes attrs = null;
            
	public Attributes get_attributes(String username,Properties prop)
    {
		
            final String ATTRIBUTE_FOR_USER = prop.getProperty("ATTRIBUTE_FOR_USER");
            final String _domain  = prop.getProperty("_domain");
            final String host 	  = prop.getProperty("host");
            final String dn		  = prop.getProperty("dn");
              String returnedAtts[] ={"telephonenumber" };
                  String searchFilter = "(&(objectClass=user)(" + ATTRIBUTE_FOR_USER + "=" + username + "))";
                  //Create the search controls
                  SearchControls searchCtls = new SearchControls();
                  searchCtls.setReturningAttributes(returnedAtts);
                  //Specify the search scope
                  searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
                  String searchBase = dn;
                  Hashtable environment = new Hashtable();
                  environment.put(Context.INITIAL_CONTEXT_FACTORY, prop.getProperty("INITIAL_CONTEXT_FACTORY"));
                  environment.put(Context.PROVIDER_URL, prop.getProperty("PROVIDER_URL"));
                  environment.put(Context.SECURITY_AUTHENTICATION, prop.getProperty("SECURITY_AUTHENTICATION"));
                  environment.put(Context.SECURITY_PRINCIPAL, prop.getProperty("SECURITY_PRINCIPAL"));
                  environment.put(Context.SECURITY_CREDENTIALS,prop.getProperty("SECURITY_CREDENTIALS"));
                  LdapContext ctxGC = null;
                  try
                  {
                         ctxGC = new InitialLdapContext(environment, null);
                          
                        NamingEnumeration answer = ctxGC.search(searchBase, searchFilter, searchCtls);
                        while (answer.hasMoreElements())
                        {
                              SearchResult sr = (SearchResult)answer.next();
                              this.attrs = sr.getAttributes();
                              if (attrs != null)
                              {
                                    return attrs;
                              }
                        }
                   }

                  catch (NamingException e)
                  {
                        System.out.println("#####################################################");
                        System.out.println("### ERROR, Unable to get <InitialLdapContext>.... ###");
                        System.out.println("#####################################################");
                        e.printStackTrace();
                  }
                  return null;
    }        
        public String parse_attributes()
        {
        	String cn = "";
        	try {
				cn=this.attrs.get("telephonenumber").get().toString();
			} catch (NamingException e) {
				e.printStackTrace();
			}
			if (cn.length() == 4) {
				  return cn;
				} else if (cn.length() > 4) {
				  return cn.substring(cn.length() - 4);
				} else {
				  // whatever is appropriate in this case
					return "";
			//	  throw new IllegalArgumentException("word has less than 3 characters!");
				}
        }
    } 


	
