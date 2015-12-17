package com.luxoft.cjp_krakow_2015.cjp.bankapp.models;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.EmailException;

public class Email implements Serializable{
	private static final long serialVersionUID = -2040848718195816153L;
	private String name;
	private String domainName;
	private String domainZone;
	
	public Email(String email) throws EmailException {
		if(!setEmail(email))
			throw new EmailException("Incorrect email");
	}
	
	public boolean setEmail(String email) {
		Pattern pattern = Pattern.compile("(\\w+)@(\\w+).(\\w+)");
		Matcher matcher = pattern.matcher(email);
		
		if(matcher.find()) {
		    this.name = matcher.group(1);
		    this.domainName = matcher.group(2);
		    this.domainZone = matcher.group(3);
		    return true;
		}
		
		return false;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getDomainZone() {
		return domainZone;
	}

	public void setDomainZone(String domainZone) {
		this.domainZone = domainZone;
	}

	@Override
	public String toString() {
		return new StringBuilder(name).append("@").append(domainName).append(".").append(domainZone).toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((domainName == null) ? 0 : domainName.hashCode());
		result = prime * result + ((domainZone == null) ? 0 : domainZone.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Email other = (Email) obj;
		if (domainName == null) {
			if (other.domainName != null)
				return false;
		} else if (!domainName.equals(other.domainName))
			return false;
		if (domainZone == null) {
			if (other.domainZone != null)
				return false;
		} else if (!domainZone.equals(other.domainZone))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
