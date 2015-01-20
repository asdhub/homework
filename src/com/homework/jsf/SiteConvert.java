package com.homework.jsf;

import java.util.ArrayList;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.homework.jpa.Nurse;
import com.homework.jpa.Site;
import com.homework.service.ManageService;

@Component("siteConvert")
@Scope("request")
public class SiteConvert implements Converter {
	private Logger log = LoggerFactory.getLogger(SiteConvert.class);
	
	@Autowired
	private ManageService ms;

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if("0".equals(arg2)){
			Site site=new Site();
			site.setNurses(new ArrayList<Nurse>());
			return site;
		}
		
		int id=Integer.parseInt(arg2);
		return ms.findSite(id);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		Site obj= (Site)arg2;log.error("STRING:{}",obj.getId());
		return Integer.toString(obj.getId());
	}

}
