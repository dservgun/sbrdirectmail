package com.sbr.model.events;

public enum ApplicationEvent{
  AddContact(Constants.ADD_CONTACT),
  DeleteContact(Constants.DELETE_CONTACT),
  UpdateContact(Constants.UPDATE_CONTACT),
  QueryContact(Constants.QUERY_CONTACT),
  QueryAllContacts(Constants.QUERY_ALL_CONTACTS),
  SearchContacts(Constants.SEARCH_CONTACTS),
  QueryCityByZip(Constants.QUERY_CITY_BY_ZIP),
  QueryAllCategories(Constants.QUERY_ALL_CATEGORIES),
  QueryAllStates(Constants.QUERY_ALL_STATES),
  AddNotes(Constants.ADD_NOTES),
  DeleteNotes(Constants.DELETE_NOTES),
  UpdateNotes(Constants.UPDATE_NOTES),
  QueryNotes(Constants.QUERY_NOTES),
  AddReminder(Constants.ADD_REMINDER),
  DeleteReminder(Constants.DELETE_REMINDER),
  UpdateReminder(Constants.UPDATE_REMINDER),
  QueryReminder(Constants.QUERY_REMINDER),
  QueryAllReminders(Constants.QUERY_ALL_REMINDERS),
  RegisterUser(Constants.REGISTER_USER),
  QueryRegistration(Constants.QUERY_REGISTRATION);  
  
  ApplicationEvent(String anEventConstant){
    this.identifier = anEventConstant;
  }
  
  public static ApplicationEvent getEvent(String anEvent){
    for(ApplicationEvent event : ApplicationEvent.values()){
      if(event.identifier.equals(anEvent)){
        return event;
      }
    }
    throw new RuntimeException("Event not found " + anEvent);
  }
  
  private String identifier;
}