package com.sbr.extensions;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.text.MessageFormat;
import it.gotoandplay.smartfoxserver.db.*;
import it.gotoandplay.smartfoxserver.data.*;
import it.gotoandplay.smartfoxserver.exceptions.*;
import it.gotoandplay.smartfoxserver.extensions.*;
import it.gotoandplay.smartfoxserver.lib.ActionscriptObject;
import it.gotoandplay.smartfoxserver.lib.MailManager;
import it.gotoandplay.smartfoxserver.events.InternalEventObject;
import org.json.JSONObject; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sbr.model.events.ApplicationEvent;
import com.sbr.model.events.Constants;
import com.sbr.util.DateUtil;
import com.sbr.util.HibernateUtil;
import java.sql.Connection;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.ConcurrentHashMap;  

public class DirectMailExtension extends AbstractExtension
{    
    public static Boolean APPROVE_NEW_USERS = true;
    private static Logger log = LoggerFactory.getLogger(DirectMailExtension.class);
    private static String defaultDateFormat = "EEE MMM d yyyy";
    private static String defaultDateTimeFormat = "EEE MMM d hh:mm:ss Z yyyy";
    
    private static int BATCHSIZE = 200; 
    private static String RESULT_STRING_KEY = "result";
    private static String COMPANY = "Company";
    private static String ZIPCODE = "zipCode";
    private static String CONTACT_ID = "contactId";
    private static String REGISTRATION_ID = "registrationId";
    private static String LOGIN_REQUEST = "loginRequest";    
    private static String USER_LOST = "userLost";
    private static String USER_JOIN = "userJoin";
    
    private ExtensionHelper helper;
    private DbManager db;
    private Zone currentZone;
    
    private int commandCount = 0;
    public void init()
    {
    	trace("Extension initialized");	
    	helper = ExtensionHelper.instance();
    	currentZone = helper.getZone(this.getOwnerZone());
    	db = currentZone.dbManager;
      HibernateUtil util = HibernateUtil.getSingleton();
      clearOldPrices();
    }
    private void clearOldPrices(){
      
    }
    
    public void destroy()
    {
      trace("Extension destroyed");
    }
  /**
     * Handle Client Requests in XML format
     * 
     * @param cmd		the command name
     * @param ao		the actionscript object with the request params
     * @param u			the user who sent the request
     * @param fromRoom	the roomId where the request was generated
     */
    public void handleRequest(String cmd, ActionscriptObject ao, User u, int fromRoom)
    {
      try {
          trace("Handling request " + cmd);
          log.info("Handling request for ao "  +cmd );
          ApplicationEvent currentEvent = ApplicationEvent.getEvent(cmd);
          processRequest(currentEvent, ao, u, fromRoom);
      }catch(Exception e){
          trace(e.getMessage());
      }
    }
    
  /**
     * Handle Client Requests in String format
     * 
     * @param cmd		the command name
     * @param params	an array of String parameters
     * @param u			the user who sent the request
     * @param fromRoom	the roomId where the request was generated
     */
    public void handleRequest(String cmd, String params[], User u, int fromRoom)
    {
      trace("Handling  request  " + cmd);
    }
	
	
  /**
     * Handle Client Requests in JSON format
     * 
     * @param cmd		the command name
     * @param params	a JSONObject with the request parameters
     * @param u			the user who sent the request
     * @param fromRoom	the roomId where the request was generated
     */
  public void handleRequest(String cmd, JSONObject jso, User u, int fromRoom)
  {
    trace("Handling json request  " + cmd);
  }
  
  public void handleInteralRequest(Object params){
    trace("Handling internal request " + params);
  }
  
  public void handleInternalEvent(InternalEventObject ieo){
    trace("Handling internal request  " + ieo.getEventName());
    if(ieo.getEventName().equalsIgnoreCase(LOGIN_REQUEST)){
        handleLoginRequest(ieo);
    }else if(ieo.getEventName().equalsIgnoreCase(USER_JOIN)){      
    }
    else if(ieo.getEventName().equalsIgnoreCase(USER_LOST)){        
    }
  }	
  public void handleLoginRequest(InternalEventObject ieo){
    trace("Processing request " + ieo.getEventName());
    SocketChannel chan = (SocketChannel)ieo.getObject("chan");
    String nickName = ieo.getParam("nick");
    nickName = nickName.toLowerCase();
    String userName = ieo.getParam("user");
    if(userName == null){
        userName = "";
    }
    userName = userName.toLowerCase();
    String password = ieo.getParam("pass");    
    String zoneName = ieo.getParam("zone");
    trace(MessageFormat.format("Logging into {0}:{1}:{2}:{3}", nickName, userName, password, zoneName));
    Connection connection = null;
    try {
        connection = db.getConnection();
        com.sbr.model.dto.UserTable userTable = com.sbr.model.queries.UserTable.findUser(connection, nickName);        
        ActionscriptObject ao = new ActionscriptObject();
        ao.put("command", Constants.VALIDATE_LOGIN);      
        if(userTable != null){
          if(!password.equals(userTable.getPassword())){
            throw new RuntimeException(MessageFormat.format("Invalid login {0}: {1}: {2}", nickName, userName, zoneName));
          }
          userTable.setLastLogin(new Date());
          userTable.save(connection);
          userTable.updateAO(ao);
          ao.put("success", "true");
        }        
        LinkedList result = new LinkedList();
        result.add(chan);
        User user = helper.getUserByChannel(chan);
        if(user == null){
          user = helper.canLogin(nickName, "", chan, zoneName);
          if(user == null){
            throw new RuntimeException(MessageFormat.format("Invalid login {0}: {1}: {2}", nickName, userName, zoneName));
          }
        }
        helper.sendRoomList(chan);
        sendResponse(ao, -1, (User)null, result);        
    }catch(Exception e){
      log.error("Invalid login ", e);      
    }finally {
        if(connection != null){
      try {
          connection.close();
      }catch(Exception e2){};
        }        
    }
  }
  
  
  public void processRequest(ApplicationEvent event, ActionscriptObject ao, User u, int roomNumber){
  	if(currentZone == null){
  	    throw new RuntimeException("Current zone not initialized");
  	}
    	Room fromRoom = currentZone.getRoom(roomNumber);
    	switch(event){
      case AddContact: {
        processAddContact(ao, u, fromRoom);
        break;
      }
      case UpdateContact:{
        processUpdateContact(ao, u, fromRoom);
        break;
      }
      case DeleteContact:{
        processDeleteContact(ao, u, fromRoom);
        break;
      }
      case QueryContact:{
        processQueryContact(ao, u, fromRoom);
        break;
      }
      case QueryAllContacts: {
        processQueryAllContacts(ao, u, fromRoom);
        break;
      }
      case SearchContacts: {
        processSearchContacts(ao, u, fromRoom);
        break;
      }
      case QueryCityByZip: {
        processQueryCityByZip(ao, u, fromRoom);
        break;
      }
      case QueryAllStates: {
        processQueryAllStates(ao, u, fromRoom);
        break;
      }
      case QueryAllCategories:{
        processQueryAllCategories(ao, u, fromRoom);
        break;
      }
      case AddNotes : {
        processAddNote(ao, u, fromRoom);
        break;
      }
      case DeleteNotes : {
        processDeleteNote(ao, u, fromRoom);
        break;
      }
      case UpdateNotes: {
        processUpdateNote(ao, u, fromRoom);
        break;
      }
      case QueryNotes : {
        processQueryNotes(ao, u, fromRoom);
        break;
      }
      case AddReminder : {
        processAddReminder(ao, u, fromRoom);
        break;
      }
      case UpdateReminder:{
        processUpdateReminder(ao, u, fromRoom);
        break;
      }
      case DeleteReminder : {
        processDeleteReminder(ao, u, fromRoom);
        break;
      }
      case QueryReminder : {
        processQueryReminders(ao, u, fromRoom);
        break;
      }
      case QueryAllReminders: {
        processQueryAllReminders(ao, u, fromRoom);
        break;
      }
      case RegisterUser:{
        processRegisterUser(ao, u, fromRoom);
        break;
      }
      case QueryRegistration: {
        processQueryRegistration(ao, u, fromRoom);
        break;
      }      
    	default: throw new RuntimeException("Invalid application event " + event);
    	}
  }
  
  public void processAddContact(ActionscriptObject data, User u, Room room){
  	log.info("Processing adding contact " + u);
    ActionscriptObject ao = data.getObj("data");    
  	Connection dbConnection = db.getConnection();
  	try {
  	    int id = (int)(ao.getNumber(com.sbr.model.dto.Contact.ID));
  	    if(id > 0){
          throw new RuntimeException("Invalid id passed for insert "+ id);
  	    }   
        debugAO(ao);
  	    com.sbr.model.dto.Contact contact
  		= new com.sbr.model.dto.Contact();
  	    contact.setId(id);      
        contact.setCategory(ao.getString(com.sbr.model.dto.Contact.CATEGORY));
        log.info("Category " + contact.getCategory() + ":AO" + ao.getString(com.sbr.model.dto.Contact.CATEGORY));
        contact.setSicCode(ao.getString(com.sbr.model.dto.Contact.SICCODE));
        log.info("Sic code " + contact.getSicCode());
        contact.setFirstName(ao.getString(com.sbr.model.dto.Contact.FIRSTNAME));
        contact.setLastName(ao.getString(com.sbr.model.dto.Contact.LASTNAME));
        contact.setCompany(ao.getString(com.sbr.model.dto.Contact.COMPANY));
        contact.setAddress(ao.getString(com.sbr.model.dto.Contact.ADDRESS));
        contact.setCity(ao.getString(com.sbr.model.dto.Contact.CITY));
        contact.setState(ao.getString(com.sbr.model.dto.Contact.STATE));
        contact.setZip(ao.getString(com.sbr.model.dto.Contact.ZIP));
        contact.setPhone(ao.getString(com.sbr.model.dto.Contact.PHONE));
        contact.setFax(ao.getString(com.sbr.model.dto.Contact.FAX));
        contact.setNotes(ao.getString(com.sbr.model.dto.Contact.NOTES));
        contact.setBrochureSent(ao.getBool(com.sbr.model.dto.Contact.BROCHURESENT));
        contact.setIsCustomer(ao.getBool(com.sbr.model.dto.Contact.ISCUSTOMER));
        String dateString = ao.getString(com.sbr.model.dto.Contact.BROCHURESENTDATE);
        
        if(dateString != null && !dateString.equals("")){
          contact.setBrochureSentDate(DateUtil.getDate(defaultDateFormat, dateString));
        }
        contact.setCreatedBy(u.getName());
        contact.setCreationIP(u.getIpAddress());
  	    contact.save(dbConnection);
  	    contact.updateAO(ao);
        ao.put("command", Constants.ADD_CONTACT);
  	    ao.put(Constants.MESSAGE, "Added contact successfully");
  	    boolean publish = true;
  	    sendStatusMessage(ao, u, room, publish);
  	}catch(Exception e){
        ao.put("command", Constants.ADD_CONTACT);        
  	    processException(e, ao, u, room, "Error adding user table");
  	}finally {
  	    if(dbConnection != null){
  		try{
  		    dbConnection.close();
  		}catch(Exception e){};
  	    }
  	}  
  }
  public void processUpdateContact(ActionscriptObject data, User u, Room room){
  	log.info("Processing adding user table " + u);
  	Connection dbConnection = db.getConnection();
    ActionscriptObject ao = data.getObj("data");
  	try {
  	    int id = (int)(ao.getNumber(com.sbr.model.dto.Contact.ID));
  	    if(id < 0){
          throw new RuntimeException("Invalid id passed for update "+ id);
  	    }        
  	    com.sbr.model.dto.Contact contact
  		= com.sbr.model.queries.Contact.loadDTOObject(dbConnection, id);
        contact.setCategory(ao.getString(com.sbr.model.dto.Contact.CATEGORY));
        contact.setSicCode("" + ao.getString(com.sbr.model.dto.Contact.SICCODE));
        contact.setFirstName("" + ao.getString(com.sbr.model.dto.Contact.FIRSTNAME));
        contact.setLastName("" + ao.getString(com.sbr.model.dto.Contact.LASTNAME));
        contact.setCompany("" + ao.getString(com.sbr.model.dto.Contact.COMPANY));
        contact.setAddress("" + ao.getString(com.sbr.model.dto.Contact.ADDRESS));
        contact.setCity("" + ao.getString(com.sbr.model.dto.Contact.CITY));
        contact.setState("" + ao.getString(com.sbr.model.dto.Contact.STATE));
        contact.setZip("" + ao.getString(com.sbr.model.dto.Contact.ZIP));
        contact.setPhone("" + ao.getString(com.sbr.model.dto.Contact.PHONE));
        contact.setFax("" + ao.getString(com.sbr.model.dto.Contact.FAX));
        contact.setNotes("" + ao.getString(com.sbr.model.dto.Contact.NOTES));
        contact.setBrochureSent(ao.getBool(com.sbr.model.dto.Contact.BROCHURESENT));
        contact.setIsCustomer(ao.getBool(com.sbr.model.dto.Contact.ISCUSTOMER));  
        contact.setModifiedBy(u.getName());        
        contact.setModificationIP(u.getIpAddress());
        String dateString = ao.getString(com.sbr.model.dto.Contact.BROCHURESENTDATE);
        if(dateString != null && !dateString.equals("")){
          contact.setBrochureSentDate(DateUtil.getDate(defaultDateFormat, dateString));
        }
  	    contact.save(dbConnection);
  	    contact.updateAO(ao);
        ao.put("command", Constants.UPDATE_CONTACT);
  	    ao.put(Constants.MESSAGE, "Added contact successfully");
  	    boolean publish = true;
  	    sendStatusMessage(ao, u, room, publish);
  	}catch(Exception e){
  	    processException(e, ao, u, room, "Error adding user table");
  	}finally {
  	    if(dbConnection != null){
  		try{
  		    dbConnection.close();
  		}catch(Exception e){};
  	    }
  	}  
  
  }
  
  public void processDeleteContact(ActionscriptObject ao, User u, Room aRoom){
  }
  
  public void processQueryCityByZip(ActionscriptObject ao, User u, Room room){
    Connection dbConnection = db.getConnection();
    try{
      ActionscriptObject zipCodeAO = new ActionscriptObject();
      com.sbr.model.dto.ZipCode zipCode = com.sbr.model.queries.ZipCode.queryCityByZip(dbConnection, ao.getString(ZIPCODE));
      if(zipCode != null){
        zipCode.updateAO(zipCodeAO);
        ao.put(RESULT_STRING_KEY, zipCodeAO);        
      }
      boolean publish = false;
      sendStatusMessage(ao, u, room, publish);
    }catch(Exception e){
      processException(e, ao, u, room, "Error querying city by zip");
    }finally {
      if(dbConnection != null){
        try {
          dbConnection.close();
        }catch(Exception e){}
      }
    }
  }
  public void processSearchContacts(ActionscriptObject ao, User u, Room room){
    log.info("Processiong search contacts");
    processQueryAllContacts(ao, u, room);
  }
  public void processQueryAllContacts(ActionscriptObject ao, User u, Room room){
    log.info("Processing query all contacts ");
    Connection dbConnection = db.getConnection();
    try {
      List<com.sbr.model.dto.Contact> contactList = 
        com.sbr.model.queries.Contact.queryAllContacts(dbConnection, ao.getString(COMPANY));
      log.info("Sending " + contactList.size() + " contacts");
      ActionscriptObject result = new ActionscriptObject();
      int index = 0;
      for(com.sbr.model.dto.Contact aContact : contactList){
        ActionscriptObject aoContact = new ActionscriptObject();        
        aContact.updateAO(aoContact);
        result.put(index, aoContact);
        index++;
        if(index == BATCHSIZE){
          ao.put(RESULT_STRING_KEY, result);
          boolean publish = false;
          sendStatusMessage(ao, u, room, publish);
          result = new ActionscriptObject();
          index = 0;
        }
      }
      ao.put(RESULT_STRING_KEY, result);
      boolean publish = false;
      sendStatusMessage(ao, u, room, publish);
    }catch(Exception e){
      processException(e, ao, u, room, "Error querying all contacts");
    }finally{
      if(dbConnection != null){
        try {
          dbConnection.close();
        }catch(Exception e){}
      }
    }
  }
  public void processQueryContact(ActionscriptObject ao, User u, Room room){
    Connection dbConnection = db.getConnection();
    try {
      String companyQuery = ao.getString(com.sbr.model.dto.Contact.COMPANY);
      com.sbr.model.dto.Contact contact = null;
      if(companyQuery != null && !companyQuery.equals("")){
        log.info("Processing query contact for " + ao.get(com.sbr.model.dto.Contact.COMPANY));
        contact = com.sbr.model.queries.Contact.queryContact(dbConnection, companyQuery);
      }else{        
        int contactId = (int) ao.getNumber(CONTACT_ID);        
        contact = com.sbr.model.queries.Contact.loadDTOObject(dbConnection, contactId);
      }
      log.info("Returning contact " + contact + " " + companyQuery);
      ActionscriptObject result = new ActionscriptObject();
      
      if(contact != null){
        contact.updateAO(result);
      }else {
        result.put(com.sbr.model.dto.Contact.ID, -1);      
      }
      ao.put(RESULT_STRING_KEY, result);
      boolean publish = false;
      sendStatusMessage(ao, u, room, publish);
    }catch(Exception e){
      processException(e, ao, u, room, "Error querying company");
    } finally {
      if(dbConnection != null){
        try {
          dbConnection.close();
        }catch(Exception e){
          
        }
      }
    }
  
  }
  public void processQueryAllStates(ActionscriptObject ao, User u, Room aRoom){
  }
  
  public void processQueryAllCategories(ActionscriptObject ao, User u, Room aRoom){
    
  }
  public void processAddNote(ActionscriptObject ao, User u, Room room) {
   	log.info("Processing add note " + u);
  	Connection dbConnection = db.getConnection();
  	try {
  	    int id = (int)(ao.getNumber(com.sbr.model.dto.Note.ID));
        if(id > 0){
          throw new RuntimeException("Invalid id passed for Add Note " + id);
        }
  	    com.sbr.model.dto.Note note = new com.sbr.model.dto.Note();
  	    note.setId(id);        
        note.setContactId((int)ao.getNumber(com.sbr.model.dto.Note.CONTACTID));
        note.setNotes(ao.getString(com.sbr.model.dto.Note.NOTES));
  	    note.save(dbConnection);
  	    note.updateAO(ao);
        ao.put("command", Constants.ADD_NOTES);
  	    ao.put(Constants.MESSAGE, "Added note successfully");
  	    boolean publish = true;
  	    sendStatusMessage(ao, u, room, publish);
  	}catch(Exception e){
        ao.put("command", Constants.ADD_NOTES);        
  	    processException(e, ao, u, room, "Error adding user table");
  	}finally {
  	    if(dbConnection != null){
  		try{
  		    dbConnection.close();
  		}catch(Exception e){};
  	    }
  	}   
  }
  public void processUpdateNote(ActionscriptObject ao, User u, Room room) {
   	log.info("Processing updating note" + u);
  	Connection dbConnection = db.getConnection();
  	try {
  	    int id = (int)(ao.getNumber(com.sbr.model.dto.Note.ID));
        if(id < 0){
          throw new RuntimeException("Invalid id passed for Update Note " + id);
        }
  	    com.sbr.model.dto.Note note = 
          com.sbr.model.queries.Note.loadDTOObject(dbConnection, id);
        if(note == null){
          throw new RuntimeException(MessageFormat.format("Note with {0}: not found", id));
        }
        note.setContactId((int)ao.getNumber(com.sbr.model.dto.Note.CONTACTID));
        note.setNotes(ao.getString(com.sbr.model.dto.Note.NOTES));
  	    note.save(dbConnection);
  	    note.updateAO(ao);
        ao.put("command", Constants.UPDATE_NOTES);
  	    ao.put(Constants.MESSAGE, "Updated note successfully");
  	    boolean publish = true;
  	    sendStatusMessage(ao, u, room, publish);
  	}catch(Exception e){
        ao.put("command", Constants.UPDATE_NOTES);        
  	    processException(e, ao, u, room, "Error adding user table");
  	}finally {
  	    if(dbConnection != null){
  		try{
  		    dbConnection.close();
  		}catch(Exception e){};
  	    }
  	}   
  }

  public void processDeleteNote(ActionscriptObject ao, User u, Room room){
   	log.info("Processing updating note" + u);
  	Connection dbConnection = db.getConnection();
  	try {
  	    int id = (int)(ao.getNumber(com.sbr.model.dto.Note.ID));
        if(id < 0){
          throw new RuntimeException("Invalid id passed for Update Note " + id);
        }
  	    com.sbr.model.dto.Note note = 
          com.sbr.model.queries.Note.loadDTOObject(dbConnection, id);
        if(note == null){
          throw new RuntimeException(MessageFormat.format("Note with {0}: not found", id));
        }
        note.setDeleted(true);
  	    note.save(dbConnection);
  	    note.updateAO(ao);
        ao.put("command", Constants.DELETE_NOTES);
  	    ao.put(Constants.MESSAGE, "Updated note successfully");
  	    boolean publish = true;
  	    sendStatusMessage(ao, u, room, publish);
  	}catch(Exception e){
        ao.put("command", Constants.DELETE_NOTES);        
  	    processException(e, ao, u, room, "Error adding user table");
  	}finally {
  	    if(dbConnection != null){
  		try{
  		    dbConnection.close();
  		}catch(Exception e){};
  	    }
  	}   
  
  }
  public void processQueryNotes(ActionscriptObject ao, User u, Room room){
    log.info("Processing query notes for a contact " + (int)ao.getNumber(CONTACT_ID));
    Connection dbConnection = db.getConnection();
    try {
      List<com.sbr.model.dto.Note> notesList = 
        com.sbr.model.queries.Note.queryAllNotes(dbConnection, (int)ao.getNumber(CONTACT_ID));
      if(notesList != null){
        log.info("Sending " + notesList.size() + " Notes");
        ActionscriptObject result = new ActionscriptObject();
        int index = 0;
        for(com.sbr.model.dto.Note aNote : notesList){
          ActionscriptObject aoNote = new ActionscriptObject();        
          aNote.updateAO(aoNote);
          result.put(index, aoNote);
          index++;
          if(index == BATCHSIZE){
            ao.put(RESULT_STRING_KEY, result);
            boolean publish = false;
            sendStatusMessage(ao, u, room, publish);
            result = new ActionscriptObject();
            index = 0;
          }
      }
      ao.put(RESULT_STRING_KEY, result);
      }
      boolean publish = false;
      sendStatusMessage(ao, u, room, publish);
    }catch(Exception e){
      processException(e, ao, u, room, "Error querying all contacts");
    }finally{
      if(dbConnection != null){
        try {
          dbConnection.close();
        }catch(Exception e){}
      }
    }
  }
  public void processAddReminder(ActionscriptObject ao, User u, Room room){
   	log.info("Processing add reminder " + ao.getNumber(com.sbr.model.dto.Reminder.REMINDERDATE));
  	Connection dbConnection = db.getConnection();
  	try {
  	    int id = (int)(ao.getNumber(com.sbr.model.dto.Reminder.ID));
        if(id >0 ){
          throw new RuntimeException("Invalid id passed for Add reminder " + id);
        }
  	    com.sbr.model.dto.Reminder reminder = new com.sbr.model.dto.Reminder();
  	    reminder.setId(id);      
        reminder.setContactId((int)ao.getNumber(com.sbr.model.dto.Note.CONTACTID));
        reminder.setDescription(ao.getString(com.sbr.model.dto.Reminder.DESCRIPTION));
        reminder.setReminderAction(ao.getString(com.sbr.model.dto.Reminder.REMINDERACTION));
        reminder.setActionTaken(ao.getBool(com.sbr.model.dto.Reminder.ACTIONTAKEN));
        reminder.setReminderDate(
          new Date((long)ao.getNumber(com.sbr.model.dto.Reminder.REMINDERDATE)));
  	    reminder.save(dbConnection);
  	    reminder.updateAO(ao);
        ao.put("command", Constants.ADD_REMINDER);
  	    ao.put(Constants.MESSAGE, "Updated note successfully");
  	    boolean publish = true;
  	    sendStatusMessage(ao, u, room, publish);
  	}catch(Exception e){
        ao.put("command", Constants.ADD_REMINDER);        
  	    processException(e, ao, u, room, "Error adding user table");
  	}finally {
  	    if(dbConnection != null){
  		try{
  		    dbConnection.close();
  		}catch(Exception e){};
  	    }
  	}   
    
  }
  public void processUpdateReminder(ActionscriptObject ao, User u,Room room){
   	log.info("Processing updating reminder " + ao.getNumber(com.sbr.model.dto.Reminder.REMINDERDATE));
  	Connection dbConnection = db.getConnection();
  	try {
  	    int id = (int)(ao.getNumber(com.sbr.model.dto.Reminder.ID));
        if(id < 0){
          throw new RuntimeException("Invalid id passed for Update reminder " + id);
        }
  	    com.sbr.model.dto.Reminder reminder = 
          com.sbr.model.queries.Reminder.loadDTOObject(dbConnection, id);
        if(reminder == null){
          throw new RuntimeException(MessageFormat.format("Reminder with {0}: not found", id));
        }
  	    reminder.setId(id);      
        reminder.setContactId((int)ao.getNumber(com.sbr.model.dto.Note.CONTACTID));
        reminder.setDescription(ao.getString(com.sbr.model.dto.Reminder.DESCRIPTION));
        reminder.setReminderAction(ao.getString(com.sbr.model.dto.Reminder.REMINDERACTION));
        reminder.setActionTaken(ao.getBool(com.sbr.model.dto.Reminder.ACTIONTAKEN));        
        reminder.setReminderDate(
          new Date((long)ao.getNumber(com.sbr.model.dto.Reminder.REMINDERDATE)));
  	    reminder.save(dbConnection);
  	    reminder.updateAO(ao);
        ao.put("command", Constants.UPDATE_REMINDER);
  	    ao.put(Constants.MESSAGE, "Updated note successfully");
  	    boolean publish = true;
  	    sendStatusMessage(ao, u, room, publish);
  	}catch(Exception e){
        ao.put("command", Constants.UPDATE_REMINDER);        
  	    processException(e, ao, u, room, "Error adding user table");
  	}finally {
  	    if(dbConnection != null){
  		try{
  		    dbConnection.close();
  		}catch(Exception e){};
  	    }
  	}   
  
  }
  public void processDeleteReminder(ActionscriptObject ao, User u, Room room){
   	log.info("Processing updating reminder " + u);
  	Connection dbConnection = db.getConnection();
  	try {
  	    int id = (int)(ao.getNumber(com.sbr.model.dto.Reminder.ID));
        if(id < 0){
          throw new RuntimeException("Invalid id passed for Update reminder " + id);
        }
  	    com.sbr.model.dto.Reminder reminder = 
          com.sbr.model.queries.Reminder.loadDTOObject(dbConnection, id);
        if(reminder == null){
          throw new RuntimeException(MessageFormat.format("Reminder with {0}: not found", id));
        }
        reminder.setDeleted(true);
  	    reminder.save(dbConnection);
  	    reminder.updateAO(ao);
        ao.put("command", Constants.DELETE_REMINDER);
  	    ao.put(Constants.MESSAGE, "Updated note successfully");
  	    boolean publish = true;
  	    sendStatusMessage(ao, u, room, publish);
  	}catch(Exception e){
        ao.put("command", Constants.DELETE_REMINDER);        
  	    processException(e, ao, u, room, "Error adding user table");
  	}finally {
  	    if(dbConnection != null){
  		try{
  		    dbConnection.close();
  		}catch(Exception e){};
  	    }
  	}   
  
  }
  public void processQueryAllReminders(ActionscriptObject ao,User u, Room room){
    log.info("Processing query all reminders" );
    Connection dbConnection = db.getConnection();
    try {
      List<com.sbr.model.dto.Reminder> reminderList = 
        com.sbr.model.queries.Reminder.queryAllActiveReminders(dbConnection);
      if(reminderList != null){
        log.info("Sending " + reminderList.size() + " Notes");
        ActionscriptObject result = new ActionscriptObject();
        int index = 0;
        for(com.sbr.model.dto.Reminder aReminder: reminderList){
          ActionscriptObject aoReminder = new ActionscriptObject();        
          aReminder.updateAO(aoReminder);
          result.put(index, aoReminder);
          index++;
          if(index == BATCHSIZE){
            ao.put(RESULT_STRING_KEY, result);
            boolean publish = false;
            sendStatusMessage(ao, u, room, publish);
            result = new ActionscriptObject();
            index = 0;
          }
      }
      ao.put(RESULT_STRING_KEY, result);
      }
      boolean publish = false;
      sendStatusMessage(ao, u, room, publish);
    }catch(Exception e){
      processException(e, ao, u, room, "Error querying all contacts");
    }finally{
      if(dbConnection != null){
        try {
          dbConnection.close();
        }catch(Exception e){}
      }
    }    
  }
  public void processQueryRegistration(ActionscriptObject ao, User u, Room room){
    log.info("Processing register user ");
    int id = (int)ao.getNumber(REGISTRATION_ID);
    Connection dbConnection = db.getConnection();
    try {
      com.sbr.model.dto.UserTable userTable = null;
      if(id == -1){
        userTable = new com.sbr.model.dto.UserTable();
      }else {
        userTable = com.sbr.model.queries.UserTable.loadDTOObject(dbConnection, id);
        if(userTable == null){
          throw new RuntimeException("No user found for " + id);
        }
      }
      userTable.setLastLogin(new Date());
      userTable.save(dbConnection);
      userTable.updateAO(ao);
      ao.put("command", Constants.QUERY_REGISTRATION);
      ao.put(Constants.MESSAGE, "User registered successfully.");
      boolean publish = true;
      sendStatusMessage(ao, u, room, publish);
      
    }catch(Exception e){
      processException(e, ao, u, room, "Error registration user");
    }finally{
      if(dbConnection != null){
        try {
          dbConnection.close();
        }catch(Exception e){}
      }
    }
    
  }
  /**
      * Register the user if this is a new user. By default the user is active and therefore the registration will not 
     * fail. 
     */
  public void processRegisterUser(ActionscriptObject ao, User u, Room room){
    log.info("Processing register user ");
    int id = (int)ao.getNumber(REGISTRATION_ID);
    Connection dbConnection = db.getConnection();
    try {
      com.sbr.model.dto.UserTable userTable = null;
      if(id == -1){
        userTable = new com.sbr.model.dto.UserTable();
      }else {
        userTable = com.sbr.model.queries.UserTable.loadDTOObject(dbConnection, id);
      }
      userTable.setId(id);
      userTable.setFirstName(ao.getString(com.sbr.model.dto.UserTable.FIRSTNAME));
      userTable.setLastName(ao.getString(com.sbr.model.dto.UserTable.LASTNAME));
      userTable.setLoginName(ao.getString(com.sbr.model.dto.UserTable.LOGINNAME));
      userTable.setPassword(ao.getString(com.sbr.model.dto.UserTable.PASSWORD));
      userTable.setEmail(ao.getString(com.sbr.model.dto.UserTable.EMAIL));
      userTable.setPhone(ao.getString(com.sbr.model.dto.UserTable.PHONE));
      userTable.setFax(ao.getString(com.sbr.model.dto.UserTable.FAX));
      userTable.setSms(ao.getString(com.sbr.model.dto.UserTable.SMS));
      userTable.setTwitterId(ao.getString(com.sbr.model.dto.UserTable.TWITTERID));
      userTable.setUserImage(ao.getString(com.sbr.model.dto.UserTable.USERIMAGE));
      userTable.setIsApproved(APPROVE_NEW_USERS);
      userTable.save(dbConnection);
      userTable.updateAO(ao);
      ao.put("command", Constants.REGISTER_USER);
      ao.put(Constants.MESSAGE, "User registered successfully.");
      boolean publish = true;
      sendStatusMessage(ao, u, room, publish);
      
    }catch(Exception e){
      processException(e, ao, u, room, "Error registration user");
    }finally{
      if(dbConnection != null){
        try {
          dbConnection.close();
        }catch(Exception e){}
      }
    }
    
  }
  
  public void processQueryReminders(ActionscriptObject ao, User u, Room room){
    log.info("Processing query reminders for a contact " + (int)ao.getNumber(CONTACT_ID));
    Connection dbConnection = db.getConnection();
    try {
      List<com.sbr.model.dto.Reminder> reminderList = 
        com.sbr.model.queries.Reminder.queryAllReminders(dbConnection, (int)ao.getNumber(CONTACT_ID));
      if(reminderList != null){
        log.info("Sending " + reminderList.size() + " Notes");
        ActionscriptObject result = new ActionscriptObject();
        int index = 0;
        for(com.sbr.model.dto.Reminder aReminder: reminderList){
          ActionscriptObject aoReminder = new ActionscriptObject();        
          aReminder.updateAO(aoReminder);
          result.put(index, aoReminder);
          index++;
          if(index == BATCHSIZE){
            ao.put(RESULT_STRING_KEY, result);
            boolean publish = false;
            sendStatusMessage(ao, u, room, publish);
            result = new ActionscriptObject();
            index = 0;
          }
      }
      ao.put(RESULT_STRING_KEY, result);
      }
      boolean publish = false;
      sendStatusMessage(ao, u, room, publish);
    }catch(Exception e){
      processException(e, ao, u, room, "Error querying all contacts");
    }finally{
      if(dbConnection != null){
        try {
          dbConnection.close();
        }catch(Exception e){}
      }
    }

  }
  private void sendStatusMessage(ActionscriptObject ao, User u, Room room, boolean publish){
    	// Prepare a list of recipients and put the user that requested the command
    	LinkedList recipients = new LinkedList();
    	if(publish){
            User[] users = room.getAllUsers();
            for(User aUser : users){
            recipients.add(aUser.getChannel());
    	    }
    	}else {
    	    if(u != null){
    		recipients.add(u.getChannel());
    	    }
    	}
    	if(recipients.size() > 0){
    	    // Send data to client
    	    trace("Sending " + ao.get("command") + ": Command count" + commandCount);
          commandCount++;
    	    sendResponse(ao, room.getId(), u, recipients);    
    	}else {
    	    trace("No recipients " + new Date());
    	}
    }
  
  private void processException(Exception e, ActionscriptObject ao, User u, Room room, String aMessage){
      	StringWriter writer = new StringWriter();
      	PrintWriter pw = new PrintWriter(writer);
      	e.printStackTrace(pw);      
      	log.error("Error ", e);
      	ao.put(Constants.EXCEPTION_MESSAGE, writer.toString());
      	sendExceptionMessage(ao, u, room);
  }
  
  private void sendExceptionMessage(ActionscriptObject ao, User u, Room room){
  	LinkedList recipients = new LinkedList();
  	recipients.add(u.getChannel());
    log.info("Sending exception " + ao.get(Constants.MESSAGE));
  	sendResponse(ao, room.getId(), u, recipients);
  }
  
  public void trace(String aMessage){
    super.trace(new Date() + aMessage);
  }
  private void debugAO(ActionscriptObject ao){
    log.info("Actionscript Object begins------" + ao.size());
    log.info("Actionscript object ends--------");
  }
  
  
}