package com.miscot.springmvc.service;

import java.util.Map;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchResult;

public interface AdAuthInterface {
	int ISauthenticate(String user, String pass);

	String GetName(String user);

	SearchResult findAccountByAccountName(DirContext ctx, String ldapSearchBase, String accountName)
			throws NamingException;

	String findGroupBySID(DirContext ctx, String ldapSearchBase, String sid) throws NamingException;

	String getPrimaryGroupSID(SearchResult srLdapUser) throws NamingException;

	String decodeSID(byte[] sid);

	Map authenticate(String user, String pass);

	String GetNameUser(String user);

	String getIsAdoAuthValue();
}
