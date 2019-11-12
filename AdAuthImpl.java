package com.miscot.springmvc.service;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.miscot.springmvc.configuration.PropertiesPath;
import com.miscot.springmvc.repository.GetQueryImpl;


@Service
public class AdAuthImpl implements AdAuthInterface {

	@Autowired 
	PropertiesPath  pp;
	@Autowired 
	GetQueryImpl getQuery;
	@Override
	public int ISauthenticate(String user, String pass) {
		//System.out.println("ADAuth Start");
	    String returnedAtts[] ={ "sn", "givenName", "mail" };

	    String searchFilter = "(&(objectClass=user)(sAMAccountName=" + user + "))";

	     int found=0;

	    //Create the search controls

	    SearchControls searchCtls = new SearchControls();

	    searchCtls.setReturningAttributes(returnedAtts);

	    String ldapHost=pp.getLdapHost();
	    String domain=pp.getDomain();
	    String searchBase=pp.getSearchBase().replace("#", "=");
	    //Specify the search scope

	    searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);



	    Hashtable env = new Hashtable();

	    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");

	    env.put(Context.PROVIDER_URL, ldapHost);

	    env.put(Context.SECURITY_AUTHENTICATION, "simple");

	    env.put(Context.SECURITY_PRINCIPAL, user + "@" + domain);

	    env.put(Context.SECURITY_CREDENTIALS, pass);



	    LdapContext ctxGC = null;



	    try

	    {
	    	
	    	if(pp.getIsadauth().equals("No"))
	    	{
	    			
	    		String chkuser="";
					try {
						chkuser = getQuery.get_chkuser(user.toUpperCase(), pass);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    		
	    			if(!chkuser.equals(""))
	    			{
	    				
	    				if(Integer.parseInt(chkuser)>0)
	    				{
	    					
	    					found=1;
	    				}
	    			}
	    		
	    		

	    		
	    	}
	    	
	    	else
	    	{
	    	// System.out.println("here");
	      ctxGC = new InitialLdapContext(env, null);
	    //  System.out.println("after");
	      //Search objects in GC using filters

	     
	      NamingEnumeration answer = ctxGC.search(searchBase, searchFilter, searchCtls);
	   //   System.out.println("answer");

	      while (answer.hasMoreElements())
	      {
	        SearchResult sr = (SearchResult) answer.next();
	        Attributes attrs = sr.getAttributes();
	        if (attrs != null)
	        {
	        	found=1;
	        }
	      }
	          return found;
	      }
	    }
	    catch (NamingException ex)

	    {
	    	 ex.printStackTrace();
	    	return found;
	    } catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    return found;
	  }
	
	@Override
	public String GetName(String user)
	  {
		  String Uname="NO";
		
		   String ldapAdServer = pp.getLdapHost();
	       String ldapSearchBase = pp.getSearchBase().replace("#", "=");
	       String ldapUsername =pp.getLdapUsername();
	       String ldapPassword = pp.getLdapPassword();
	       String ldapAccountToLookup = user;
	       Hashtable<String, Object> env = new Hashtable<String, Object>();
	       env.put(Context.SECURITY_AUTHENTICATION, "simple");
	       env.put(Context.SECURITY_PRINCIPAL, ldapUsername);
	       env.put(Context.SECURITY_CREDENTIALS, ldapPassword);
	       env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	       env.put(Context.PROVIDER_URL, ldapAdServer);
	       env.put("java.naming.ldap.attributes.binary", "objectSID");
	        try
		    {
	        LdapContext ctx = new InitialLdapContext(env,null);
	        SearchResult srLdapUser = findAccountByAccountName(ctx, ldapSearchBase, ldapAccountToLookup);
	       // System.out.println("before" );
	        if(srLdapUser !=null) {
	        	Uname=(String)srLdapUser.getAttributes().get("cn").get();
	        	//System.out.println("here" +Uname);
	        }     
	        System.out.println("uname::"+Uname);
	        return Uname;
		    }
	        catch (NamingException ex)

		    {
		      ex.printStackTrace();
		    }
	        return "NO";
		    }
	@Override
	 public SearchResult findAccountByAccountName(DirContext ctx, String ldapSearchBase, String accountName) throws NamingException {

	        String searchFilter = "(&(objectClass=user)(sAMAccountName=" + accountName + "))";

	        SearchControls searchControls = new SearchControls();
	        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
	      
	       
	        NamingEnumeration<SearchResult> results = ctx.search(ldapSearchBase, searchFilter, searchControls);

	        SearchResult searchResult = null;
	        if(results.hasMoreElements()) {
	             searchResult = (SearchResult) results.nextElement();
	            //make sure there is not another item available, there should be only 1 match
	        }
	        
	        return searchResult;
	    }
	@Override
	 public String findGroupBySID(DirContext ctx, String ldapSearchBase, String sid) throws NamingException {
	        
	        String searchFilter = "(&(objectClass=group)(objectSid=" + sid + "))";

	        SearchControls searchControls = new SearchControls();
	        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
	        
	        NamingEnumeration<SearchResult> results = ctx.search(ldapSearchBase, searchFilter, searchControls);

	        if(results.hasMoreElements()) {
	            SearchResult searchResult = (SearchResult) results.nextElement();

	            //make sure there is not another item available, there should be only 1 match
	            if(results.hasMoreElements()) {
	                System.err.println("Matched multiple groups for the group with SID: " + sid);
	                return null;
	            } else {
	                return (String)searchResult.getAttributes().get("sAMAccountName").get();
	            }
	        }
	        return null;
	    }
	@Override
	    public String getPrimaryGroupSID(SearchResult srLdapUser) throws NamingException {
	        byte[] objectSID = (byte[])srLdapUser.getAttributes().get("objectSid").get();
	        String strPrimaryGroupID = (String)srLdapUser.getAttributes().get("primaryGroupID").get();
	        
	        String strObjectSid = decodeSID(objectSID);
	        
	        return strObjectSid.substring(0, strObjectSid.lastIndexOf('-') + 1) + strPrimaryGroupID;
	    }

	@Override
	    public  String decodeSID(byte[] sid) {
	        
	        final StringBuilder strSid = new StringBuilder("S-");

	        // get version
	        final int revision = sid[0];
	        strSid.append(Integer.toString(revision));
	        
	        //next byte is the count of sub-authorities
	        final int countSubAuths = sid[1] & 0xFF;
	        
	        //get the authority
	        long authority = 0;
	        //String rid = "";
	        for(int i = 2; i <= 7; i++) {
	           authority |= ((long)sid[i]) << (8 * (5 - (i - 2)));
	        }
	        strSid.append("-");
	        strSid.append(Long.toHexString(authority));
	        
	        //iterate all the sub-auths
	        int offset = 8;
	        int size = 4; //4 bytes for each sub auth
	        for(int j = 0; j < countSubAuths; j++) {
	            long subAuthority = 0;
	            for(int k = 0; k < size; k++) {
	                subAuthority |= (long)(sid[offset + k] & 0xFF) << (8 * k);
	            }
	            
	            strSid.append("-");
	            strSid.append(subAuthority);
	            
	            offset += size;
	        }
	        
	        return strSid.toString();    
	    }
	@Override
	  public Map authenticate(String user, String pass)

	  {

	    String returnedAtts[] ={ "sn", "givenName", "mail" };

	    String searchFilter = "(&(objectClass=user)(sAMAccountName=" + user + "))";



	    //Create the search controls

	    SearchControls searchCtls = new SearchControls();

	    searchCtls.setReturningAttributes(returnedAtts);



	    //Specify the search scope

	    searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);



	    Hashtable env = new Hashtable();

	    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");

	    env.put(Context.PROVIDER_URL, pp.getLdapHost());

	    env.put(Context.SECURITY_AUTHENTICATION, "simple");

	    env.put(Context.SECURITY_PRINCIPAL, user + "@" + pp.getDomain());

	    env.put(Context.SECURITY_CREDENTIALS, pass);



	    LdapContext ctxGC = null;



	    try

	    {

	      ctxGC = new InitialLdapContext(env, null);

	      //Search objects in GC using filters

	      NamingEnumeration answer = ctxGC.search(pp.getSearchBase(), searchFilter, searchCtls);

	      while (answer.hasMoreElements())

	      {

	        SearchResult sr = (SearchResult) answer.next();

	        Attributes attrs = sr.getAttributes();

	        Map amap = null;


	        if (attrs != null)

	        {

	          amap = new HashMap();

	          NamingEnumeration ne = attrs.getAll();

	          while (ne.hasMore())

	          {

	            Attribute attr = (Attribute) ne.next();

	            amap.put(attr.getID(), attr.get());

	          }

	          ne.close();

	        }

	          return amap;

	      }

	    }

	    catch (NamingException ex)

	    {

	      ex.printStackTrace();

	    }
	    return null;

	  }
	@Override
	public String GetNameUser(String user) {
		String Uname = "NO";
		String srLdapUser = "";
		try {
			srLdapUser =getQuery.GetUserName( user );
			if (!srLdapUser.equalsIgnoreCase("")) {
				Uname ="YES";
			}
			else
			{
				Uname = "NO";
			}
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return Uname;
	}
@Override
	public String getIsAdoAuthValue() {
		// TODO Auto-generated method stub
	
		return pp.getIsadauth();
	}
}
