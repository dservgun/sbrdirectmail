package com.sbr.forms
{
	import com.sbr.commands.Command;
	import com.sbr.model.Contact;
	
	import flash.events.Event;
	import flash.events.FocusEvent;
	import flash.events.IEventDispatcher;
	
	import it.gotoandplay.smartfoxserver.SFSEvent;
	import it.gotoandplay.smartfoxserver.SmartFoxClient;
	
	import mx.containers.Form;
	import mx.controls.Alert;
	import mx.controls.CheckBox;
	import mx.controls.ComboBox;
	import mx.controls.DataGrid;
	import mx.controls.DateChooser;
	import mx.controls.Text;
	import mx.controls.TextArea;
	import mx.controls.TextInput;

	public class ContactForm extends Form implements IEventDispatcher
	{	
		public static var FORM_INITIALIZED: String = "FormInitialized";
		public static var FORM_NOT_INITIALIZED: String = "FormNotInitialized";
		public static var WILD_CARD = "%";
		private var sfs: SmartFoxClient;
		private var formState : String;
		private var categoriesQueried : Boolean = false;
		private var statesQueried : Boolean = false;
		private var idText : TextInput;
		private var categoryList: ComboBox;
		private var sicCode: TextInput;
		private var firstName : TextInput;
		private var lastName : TextInput;
		private var company : TextInput;
		private var address : TextArea;
		private var city : TextInput;
		private var state : TextInput;
		private var zipCode : TextInput;
		private var phone : TextInput;
		private var fax : TextInput;
		private var notes : TextArea;
		private var brochureSent : CheckBox;
		private var isCustomer : CheckBox;
		private var brochureSentDate : DateChooser;
		private var contactList : DataGrid;
		private var globalReminderList: DataGrid;
		private var contactListStatus : Text
		private var debugText : TextInput;
		private var notesList : Notes;
		private var reminderList : Reminder;
		public var globalReminderArray : Array;
		[Bindable]
		public var contactArray : Array;
		[Bindable]
		public var contact: Contact;
		
		public function ContactForm()
		{
			super();
			contact = new Contact();
			contactArray = new Array();
			formState = FORM_NOT_INITIALIZED;
		}
		
		public function initializeSFS(anSFS:SmartFoxClient) : void{
			sfs = anSFS;
			sfs.addEventListener(SFSEvent.onJoinRoom, this.enableForm);
			sfs.addEventListener(SFSEvent.onExtensionResponse, this.handleExtensionResponse);
		}
		public function save(anId: String): void{
			var object: Object = new Object();
			var id: int = parseInt(anId);
			contact.id = id;
			contact.category="Environment";
			if(id <= 0){
				object.command = Command.ADD_CONTACT;
			}else{
				object.command = Command.UPDATE_CONTACT;
			}
			try {
			if(Global.APPLICATION_ROOM_ID == -1){
				Alert.show("Room not initialized. Returning.");
				return;
			}
			var sendContact: Object = convertFromBindable(contact);
			object.data = sendContact;
			sfs.sendXtMessage(Global.DEFAULT_EXTENSION,object.command, 
				object,
				SmartFoxClient.XTMSG_TYPE_XML,
				Global.APPLICATION_ROOM_ID);
			}catch(err: Error){
				Alert.show("Error sending object " + err);
			}
		}
		
		public function enableForm(sfsEvent: SFSEvent): void{
			this.enabled = true;
			if(Global.LOGIN_SUCCESSFUL){
				this.queryAllContacts(sfsEvent);
				this.queryAllReminders(sfsEvent);
			}
		}
		public function queryAllCategories(sfsEvent: SFSEvent):void {
			var queryAllCategories : Object = new Object();
			queryAllCategories.command = Command.QUERY_ALL_CATEGORIES;						
		}
		public function queryAllStates(sfsEvent : SFSEvent): void{
			var queryAllStates: Object = new Object();
			queryAllStates.command = Command.QUERY_ALL_STATES;				
		}
		public function queryAllReminders(sfsEvent: Event): void{
			try {
			var object : Object = new Object();
			object.command = Command.QUERY_ALL_REMINDERS;
			this.globalReminderArray = new Array();
			this.globalReminderList.dataProvider = this.globalReminderArray;			
			sfs.sendXtMessage(Global.DEFAULT_EXTENSION,
				object.command, 
				object,
				SmartFoxClient.XTMSG_TYPE_XML,
				Global.APPLICATION_ROOM_ID);
			}catch(err: Error){
				Alert.show("Error querying contacts " + err.message + " " + err.errorID);
			}					
			
		}
		public function queryAllContacts(sfsEvent: Event): void{
			try {
			var object : Object = new Object();
			object.command = Command.QUERY_ALL_CONTACTS;
			this.contactArray = new Array();
			this.contactList.dataProvider = this.contactArray;			
			sfs.sendXtMessage(Global.DEFAULT_EXTENSION,
				object.command, 
				object,
				SmartFoxClient.XTMSG_TYPE_XML,
				Global.APPLICATION_ROOM_ID);
			}catch(err: Error){
				Alert.show("Error querying contacts " + err.message + " " + err.errorID);
			}					
		}
		
		public function sendQueryNotesByContactId(aContactId : String){
			var query : Object = new Object();
			query.command = Command.QUERY_NOTES;
			query.contactId = parseInt(aContactId);
			try {
				sfs.sendXtMessage(Global.DEFAULT_EXTENSION,
					query.command,
					query,
					SmartFoxClient.XTMSG_TYPE_XML,
					Global.APPLICATION_ROOM_ID);
			}catch(err : Error){
				Alert.show("Error sending " + query.command);
			}
		}
		public function sendQueryRemindersByContactId(aContactId : String){
			var query : Object = new Object();
			query.command = Command.QUERY_REMINDERS;
			query.contactId = parseInt(aContactId);
			try{
				sfs.sendXtMessage(Global.DEFAULT_EXTENSION,
					query.command,
					query,
					SmartFoxClient.XTMSG_TYPE_XML,
					Global.APPLICATION_ROOM_ID);
			}catch(err: Error){
				Alert.show("Error sending " + query.command);
			}
		}
		public function sendQueryContactByContactId(aContactId: int){
			var query: Object = new Object();
			query.command = Command.QUERY_CONTACT;
			query.contactId = aContactId;
			if(query.company !== null || query.company !== ""){
			sfs.sendXtMessage(Global.DEFAULT_EXTENSION, query.command,
				query,
				SmartFoxClient.XTMSG_TYPE_XML,
				Global.APPLICATION_ROOM_ID);
			}			
			
		}
		public function sendQueryContactByCompanyName(aCompanyName : String){
			var query: Object = new Object();
			query.command = Command.QUERY_CONTACT;
			query.Company = aCompanyName;
			if(query.company !== null || query.company !== ""){
			sfs.sendXtMessage(Global.DEFAULT_EXTENSION, query.command,
				query,
				SmartFoxClient.XTMSG_TYPE_XML,
				Global.APPLICATION_ROOM_ID);
			}			
		}
		public function sendQueryContact(event: Event):void{
			var query : Object = new Object();
			query.command = Command.SEARCH_CONTACTS;
			query.Company = (this.company.text + WILD_CARD);
			var oldCompanyText : String = this.company.text;
			this.clear();
			this.company.text = oldCompanyText;
			this.contactArray = new Array();			
			sfs.sendXtMessage(Global.DEFAULT_EXTENSION,
				query.command,
				query,
				SmartFoxClient.XTMSG_TYPE_XML,
				Global.APPLICATION_ROOM_ID);
		}
		public function sendQueryCityByZip(anEvent : FocusEvent){
			var query = new Object();
			query.command = Command.QUERY_CITY_BY_ZIP;
			query.zipCode = this.zipCode.text;
			sfs.sendXtMessage(Global.DEFAULT_EXTENSION,
				query.command,
				query,
				SmartFoxClient.XTMSG_TYPE_XML,
				Global.APPLICATION_ROOM_ID);
		}
		
		public function handleExtensionResponse(event: SFSEvent) :void{
		      var type : Object = event.params.type;
		      var dataObject : Object = event.params.dataObj;
		      var objectKeys : Array = new Array();
		      var error  : String = dataObject.Error;
		      if(error != null){
		      	Alert.show("Error processing extension event "+ error);
		      	return;
		      }

		      if(dataObject.command == Command.QUERY_ALL_STATES){
		      	handleQueryAllStates(dataObject);
		      }else if(dataObject.command == Command.QUERY_ALL_CATEGORIES){
		      	handleQueryAllCategories(dataObject);
		      }else if(dataObject.command == Command.ADD_CONTACT){
		      	handleMaintainContact(dataObject);
		      	updateContactArray(dataObject);
		      }else if(dataObject.command == Command.UPDATE_CONTACT){
		      	handleMaintainContact(dataObject);
		      }else if(dataObject.command == Command.DELETE_CONTACT){
		      	handleDeleteContact(dataObject);
		      }else if(dataObject.command == Command.QUERY_CONTACT){
		      	handleQueryContact(dataObject);
		      }else if(dataObject.command == Command.QUERY_ALL_CONTACTS){
		      	handleQueryAllContacts(dataObject);
		      }else if(dataObject.command == Command.SEARCH_CONTACTS){
		      	handleSearchContacts(dataObject);
		      }else if(dataObject.command == Command.QUERY_CITY_BY_ZIP){
		      	handleQueryCityByZip(dataObject);
		      }else if(dataObject.command == Command.QUERY_ALL_REMINDERS){
		      	handleQueryAllReminders(dataObject);
		      }else if(dataObject.command == Command.ADD_REMINDER){
		      	handleAddReminder(dataObject);
		      }else if(dataObject.command == Command.UPDATE_REMINDER){
		      	handleUpdateReminder(dataObject);
		      }else if(dataObject.command == Command.REGISTER_USER){
		      	if(dataObject.isApproved){
		      		Global.LOGIN_SUCCESSFUL = true;
		      		this.enableForm(event);
		      	}
		      }
		}
		public function handleQueryCityByZip(anObject : Object): void {
			if(anObject.result != null){
				this.city.text = anObject.result.city;
				this.state.text = anObject.result.stateAbbreviation;
				this.zipCode.text = anObject.result.zipCode;
			}else {
				Alert.show("Zipcode for " + this.zipCode.text + " not found");
			}
		}
		public function handleQueryContact(anObject : Object): void{
			if(anObject.result != null){
				this.idText.text = "" + anObject.result.id;
				this.address.text = anObject.result.Address;
				this.brochureSent.selected = (anObject.result.brochureSent == 'true');	
				this.categoryList.selectedItem = anObject.result.category;
				this.city.text = anObject.result.City;
				this.sicCode.text = anObject.result.sicCode;
				this.firstName.text = anObject.result.firstName;
				this.lastName.text = anObject.result.lastName;
				this.company.text = anObject.result.Company;
				this.address.text = anObject.result.Address;
				this.city.text = anObject.result.City;
				this.state.text = anObject.result.State;
				this.zipCode.text = anObject.result.Zip;
				this.phone.text = anObject.result.Phone;
				this.fax.text = anObject.result.Fax;
				this.notes.text = anObject.result.notes;
				this.isCustomer.selected = (anObject.result.isCustomer == 'true');
				updateContactListSelection(this.company.text);
				this.notesList.contactId = parseInt(this.idText.text);
				this.reminderList.contactId = parseInt(this.idText.text);
			}else{
				if(anObject.Message != null){
					Alert.show(anObject.Message);
				}
				idText.text = "-1";
			}
				
		}
		public function handleQueryAllContacts(anObject : Object) : void{
			if(anObject.result != null){
				for(var index: int = 0; index < anObject.result.length; index++){
					this.contactArray.push(anObject.result[index]);
				}
				this.contactListStatus.text = "Loaded " + this.contactArray.length;
				this.contactList.dataProvider = this.contactArray;
			}
		}
		public function handleQueryAllReminders(anObject : Object) : void{
			if(anObject.result != null){
				for(var index: int = 0; index < anObject.result.length; index++){
					this.globalReminderArray.push(anObject.result[index]);
				}
				this.globalReminderList.dataProvider = this.globalReminderArray;
			}
			
		}
		public function handleSearchContacts(anObject : Object): void{
			handleQueryAllContacts(anObject);
		}
		public function handleQueryAllCategories(anObject: Object) :void{
			
		}
		public function handleQueryAllStates(anObject: Object) : void{
			
		}
		public function handleMaintainContact(anObject : Object) : void{
			try {
			if(anObject.Error != null){
				Alert.show(anObject.Error);
				return;
			}
			contact.id = anObject.id;
			idText.text  = "" + (contact.id);
			Global.showAlert(anObject, "Contact successfully saved " + anObject.Company);
			}catch(err : Error){
				Alert.show(err.message);
			}
			updateContactArray(anObject);
		}
		public function handleUpdateContact(anObject : Object) : void{
			
		}
		public function handleDeleteContact(anObject : Object): void{
			
		}
		public function handleAddReminder(anObject : Object) : void{
			this.globalReminderArray.unshift(anObject);
			this.globalReminderList.dataProvider = this.globalReminderArray;
		}
		public function handleUpdateReminder(anObject : Object): void{
			for(var i : int = 0; i < this.globalReminderArray.length; i++){
				if(anObject.id == this.globalReminderArray[i]["id"]){
					this.globalReminderArray[i] = anObject;
				}	
			}
		}
		
		public function contactSelected(): void{
			sendQueryContactByCompanyName(this.contactList.selectedItem["Company"]);
			sendQueryNotesByContactId(this.contactList.selectedItem["id"]);
			sendQueryRemindersByContactId(this.contactList.selectedItem["id"]);
			this.notesList.clearNotes(this.notesList);
			this.reminderList.clearReminder(this.reminderList);						
			
		}
		public function reminderSelected(): void{
			this.sendQueryContactByContactId(this.globalReminderList.selectedItem["contactId"]);
			sendQueryNotesByContactId(this.globalReminderList.selectedItem["contactId"]);
			sendQueryRemindersByContactId(this.globalReminderList.selectedItem["contactId"]);
			this.notesList.clearNotes(this.notesList);
			this.reminderList.clearReminder(this.reminderList);						
			
		}
		
		private function convertToBindable(anObject: Object): Contact{
			var result : Contact = new Contact();
			result.id = anObject.id;
			result.Address = anObject.Address;
			result.category = anObject.category;
			result.brochureSent = anObject.brochureSent;
			result.isCustomer = anObject.isCustomer;
			result.brochureSentDate = anObject.brochureSentDate();
			result.City = anObject.City;
			result.Company = anObject.Company;
			result.Fax = anObject.Fax;
			result.firstName = anObject.firstName;
			result.lastName = anObject.lastName;
			result.notes = anObject.notes;
			result.Phone = anObject.Phone;
			result.sicCode = anObject.sicCode;
			result.State = anObject.State;
			result.Zip = anObject.Zip;
			return result;
		}
		private function convertFromBindable(aContact: Contact): Object{
			var result: Object = new Object();
			result.category = this.categoryList.selectedItem;
			result.id = parseInt(this.idText.text);
			result.Company = this.company.text;
			result.firstName = this.firstName.text;
			result.lastName = this.lastName.text;
			result.Phone = this.phone.text;
			result.Fax = this.fax.text;
			result.sicCode = this.sicCode.text;
			result.City = this.city.text;
			result.State = this.state.text;
			result.Zip = this.zipCode.text;
			result.Address = this.address.text;
			result.brochureSent = this.brochureSent.selected;
			result.isCustomer = this.isCustomer.selected;
			result.notes = this.notes.text
			return result;
		}
		public function updateContainer(aTextComponent : TextInput,
		aCategoryList: ComboBox,anSicCode: TextInput,
		aFirstName : TextInput,
		aLastName: TextInput,aCompany : TextInput,anAddress:  TextArea,
		aCity: TextInput, aState : TextInput, aZipCode: TextInput,
		aPhone : TextInput, aFax : TextInput, aNotes : TextArea,
		aBrochureSent : CheckBox, isACustomer: CheckBox, 
		anAdvancedDataGrid: DataGrid,
		aGlobalReminderList : DataGrid,
		aStatusText : Text, aNotesList: Notes,
		aReminderList: Reminder):void{
			try {
			this.idText = aTextComponent;
			this.categoryList = aCategoryList;
			this.sicCode = anSicCode;
			this.firstName = aFirstName;
			this.lastName = aLastName;
			this.address = anAddress;
			this.city = aCity;
			this.state = aState;
			this.zipCode = aZipCode;
			this.zipCode.addEventListener(FocusEvent.KEY_FOCUS_CHANGE, sendQueryCityByZip);
			this.phone = aPhone;
			this.fax = aFax;
			this.notes = aNotes;
			this.brochureSent = aBrochureSent;
			this.isCustomer = isACustomer;
			this.company = aCompany;
			this.company.addEventListener(FocusEvent.KEY_FOCUS_CHANGE, sendQueryContact);
			this.contactList = anAdvancedDataGrid;
			this.globalReminderList = aGlobalReminderList;
			this.contactList.dataProvider = contactArray;
			this.contactListStatus = aStatusText;
			this.notesList = aNotesList;
			this.reminderList = aReminderList;
			}catch(err: Error)
			{
				Alert.show("Error updating container " + err.message + " " + err.name + " " + err.getStackTrace());
			}
		}
		
		private function logDebug(aString : String):void{
			//this.debugText.htmlText += aString + "\n";
		}
		private function updateContactListSelection(aCompanyName : String): void{
			var foundIndex : int = 0;
			this.contactList.selectedIndex = foundIndex;
			
			for(var index : int = 0; index < this.contactArray.length; index++){
				if(this.contactArray[index]["Company"] == aCompanyName){
					foundIndex = index;					
					break;
				}				
			}			 
			this.contactList.selectedIndex = foundIndex;
			this.contactList.scrollToIndex(this.contactList.selectedIndex);		
		}		
		private function updateContactArray(anObject : Object){
			if(anObject.command == Command.UPDATE_CONTACT){
			
			for(var i : int = 0; i < this.contactArray.length; i++){
				if(this.contactArray[i]["id"] == anObject.id){
					this.contactArray[i] = anObject;
				}
			}
			}else if(anObject.command == Command.ADD_CONTACT){
				this.contactArray.push(anObject);		
			}
			var currentSelectedItem : Object = this.contactList.selectedItem;
			this.contactList.dataProvider = this.contactArray;
			for( i  = 0; i < contactArray.length; i++){
				if(contactArray[i]["id"] == currentSelectedItem["id"]){
					this.contactList.selectedIndex = i;
					this.contactList.scrollToIndex(this.contactList.selectedIndex);
					break;
				}
			}			
		}
		public function clear(): void{
			this.address.text = "";
			this.brochureSent.selected = false;
			this.isCustomer.selected = false;
			this.city.text = "";
			this.company.text = "";
			this.fax.text = "";
			this.firstName.text ="";
			this.idText.text = "-1";
			this.lastName.text = "";
			this.notes.text = "";
			this.phone.text = "";
			this.sicCode.text  ="";
			this.state.text = "";
			this.zipCode.text = "";
		}
		
	}
}