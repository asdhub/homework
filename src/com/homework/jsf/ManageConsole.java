package com.homework.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.homework.jpa.Nurse;
import com.homework.jpa.Site;
import com.homework.service.ManageService;

@Component
@Scope("session")
public class ManageConsole implements Serializable{
	private Logger log = LoggerFactory.getLogger(ManageConsole.class);
	
	private Nurse nurse;
	private Site site;
	private String siteType;
	private String nurseType;
	private List<Site> selectSite;
	//private List<Nurse> selectNurse;	
	
	//pick
	private DualListModel<Site> pickSites; 
	
	//autocomplete  Name:Number
	private String searchStr;
	
	@Autowired
	private ManageService ms;
	
	public void changeNurse(){
		String[] key=searchStr.split(":");
		int id=Integer.parseInt(key[2]);
		nurse=ms.findFullNurse(id);
		generatePickList();
	}
	
	public List<String> complete(String query){
		List<String> returnValue=new ArrayList<String>();
		List<Nurse> tmp=ms.autoFindNurse(query);
		for(Nurse n:tmp){
			returnValue.add(n.getName()+":"+n.getNumber()+":"+n.getId());
		}
		return returnValue;
	}
	
	/*public void handleTransfer(TransferEvent event){
		
	}*/
	
	public void nurseAdd(){
		if(nurse.getName().length()>0 && nurse.getNumber().length()>0){
			nurse.getSites().addAll(pickSites.getTarget());
			ms.saveNurse(nurse);
			nurse=new Nurse();
			nurse.setSites(new ArrayList<Site>());
			generatePickList();
		}		
	}
	
	public void nurseModify(){
		nurse.setSites(pickSites.getTarget());
		ms.saveNurse(nurse);
	}
	
	public void nurseDel(){
		if(nurse.getId()>0){
			ms.delNurse(nurse);
			nurse=new Nurse();
			nurse.setSites(new ArrayList<Site>());
			searchStr="";
		}
	}
	
	public void siteAdd(){
		if(site.getName().length()>0){
			ms.saveSite(site);
			site=new Site();
			site.setNurses(new ArrayList<Nurse>());
		}		
	}
	
	public void siteModify(){
		if(site.getId()>0){
			ms.saveSite(site);
			site=new Site();
			site.setNurses(new ArrayList<Nurse>());
			selectSite=genSiteList();
		}
	}
	
	public void siteDel(){
		if(site.getId()>0){
			ms.delSite(site);
			site=new Site();
			site.setNurses(new ArrayList<Nurse>());
			selectSite=genSiteList();
		}
	}
	
	public List<Nurse> findNurses(){
		if(site.getId()==0)
			return new ArrayList<Nurse>();
		return ms.findNurseBySite(site);
	}
	
	public List<Site> findSites(){
		if(nurse.getId()==0)
			return new ArrayList<Site>();
		
		return ms.findSiteByNurse(nurse);
	}
	
	public void siteAction(String type){
		siteType=type;
		if("ADD".equals(type)){
			site=new Site();
			site.setNurses(new ArrayList<Nurse>());
		}else{
			selectSite=genSiteList();
		}
		
		/*if("MODIFY".equals(type)){
			selectSite=genSiteList();
		}
		if("DEL".equals(type)){
			selectSite=genSiteList();
		}
		if("SEARCH".equals(type)){
			selectSite=genSiteList();
		}*/
	}
	
	public void nurseAction(String type){
		nurseType=type;
		if("ADD".equals(type)){
			searchStr="";
			nurse=new Nurse();
			nurse.setSites(new ArrayList<Site>());
			generatePickList();
		}		
		if("MODIFY".equals(type)){
			generatePickList();
		}
		/*if("MODIFY".equals(type)){
			selectNurse=getNurseList();
		}
		if("DEL".equals(type)){
			selectNurse=getNurseList();
		}
		if("SEARCH".equals(type)){
			selectNurse=getNurseList();
		}*/
	}
	
	private void generatePickList(){
		List<Site> source=ms.findAllSites();
		List<Site> target;
		if(nurse.getId()==0){
			target=new ArrayList<Site>();
		}else{
			target=ms.findSiteByNurse(nurse);
		}
		
		for(Site s:target){
			source.remove(s);
		}
		pickSites=new DualListModel<Site>(source,target);
	}
	
	/*private List<Nurse> getNurseList(){
		List<Nurse> returnValue=new ArrayList<Nurse>();
		Nurse tmp=new Nurse();
		tmp.setSites(new ArrayList<Site>());
		tmp.setName("請選擇");
		returnValue.add(tmp);
		returnValue.addAll(ms.findAllNurse());
		return returnValue;
	}*/
	
	private List<Site> genSiteList(){
		List<Site> returnValue=new ArrayList<Site>();
		Site tmp=new Site();
		tmp.setNurses(new ArrayList<Nurse>());
		tmp.setName("請選擇");
		returnValue.add(tmp);
		returnValue.addAll(ms.findAllSites());
		return returnValue;
	}
	
	@PostConstruct
	public void init(){
		siteType="ADD";
		nurseType="ADD";
		selectSite=new ArrayList<Site>();
		//selectNurse=new ArrayList<Nurse>();
		nurse=new Nurse();
		nurse.setSites(new ArrayList<Site>());
		site=new Site();
		generatePickList();
	}
	
	@PreDestroy
	public void destory(){
		
	}

	public Nurse getNurse() {
		return nurse;
	}

	public void setNurse(Nurse nurse) {
		this.nurse = nurse;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public String getSiteType() {
		return siteType;
	}

	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}

	public String getNurseType() {
		return nurseType;
	}

	public void setNurseType(String nurseType) {
		this.nurseType = nurseType;
	}

	public List<Site> getSelectSite() {
		return selectSite;
	}

	public void setSelectSite(List<Site> selectSite) {
		this.selectSite = selectSite;
	}

	/*public List<Nurse> getSelectNurse() {
		return selectNurse;
	}

	public void setSelectNurse(List<Nurse> selectNurse) {
		this.selectNurse = selectNurse;
	}*/

	public DualListModel<Site> getPickSites() {
		return pickSites;
	}

	public void setPickSites(DualListModel<Site> pickSites) {
		this.pickSites = pickSites;
	}

	public String getSearchStr() {
		return searchStr;
	}

	public void setSearchStr(String searchStr) {
		this.searchStr = searchStr;
	}
}
