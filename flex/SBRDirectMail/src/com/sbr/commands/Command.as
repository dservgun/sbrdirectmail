package com.sbr.commands
{
	/**
	 * A class that contains all the commands to interact with the server. 
	 */
	public class Command
	{
		public static var ADD_CONTACT : String = "AddContact";
		public static var DELETE_CONTACT : String = "DeleteContact";
		public static var UPDATE_CONTACT : String = "UpdateContact";
		public static var QUERY_CONTACT : String = "QueryContact";
		public static var QUERY_ALL_CONTACTS : String = "QueryAllContacts";
		public static var QUERY_ALL_STATES : String = "QueryAllStates";
		public static var QUERY_CITY_BY_ZIP : String = "QueryCityByZip";
		public static var SEARCH_CONTACTS : String = "SearchContacts";
		public static var QUERY_ALL_CATEGORIES : String = "QueryAllCategories";
		public static var END_QUERY_ALL_CONTACTS: String = "EndQueryAllContacts";
		public static var BEGIN_QUERY_ALL_CONTACTS : String = "BeginQueryAllContacts";
		public static var ADD_NOTES :String = "AddNotes";
		public static var UPDATE_NOTES : String = "UpdateNotes";
		public static var QUERY_NOTES :String = "QueryNotes";
		public static var ADD_REMINDER : String = "AddReminder";
		public static var UPDATE_REMINDER : String = "UpdateReminder";
		public static var QUERY_REMINDERS : String = "QueryReminder";
		public static var QUERY_ALL_REMINDERS : String ="QueryAllReminders";
		public static var VALIDATE_LOGIN : String = "ValidateLogin";
		public static var REGISTER_USER : String = "RegisterUser";
		public static var QUERY_REGISTRATION : String = "QueryRegistration";
	}
}