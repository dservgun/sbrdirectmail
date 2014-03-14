// ActionScript file
import com.sbr.commands.Command;
import com.sbr.forms.Global;
import com.sbr.forms.Reminder;

import flash.events.Event;

import it.gotoandplay.smartfoxserver.SFSEvent;
import it.gotoandplay.smartfoxserver.SmartFoxClient;

import mx.controls.Alert;

private var selectedDate: Date;
private var reminderArray : Array;
private var actionList : Array = ["Email", "Mail", "Call"];
public var contactId : int = -1;
public var reminderId : int = -1;

public function initializeReminders(): void{
	try {

		Global.SFS_CLIENT.addEventListener(SFSEvent.onExtensionResponse,handleReminderResponse);

	}catch(err :Error){
		Alert.show(" " + err.message + " " + err.name);
	}
}

public function handleReminderResponse(event :SFSEvent): void{
      var type : Object = event.params.type;
      var dataObject : Object = event.params.dataObj;		      
      var error  : String = dataObject.Error;
      if(error != null){
      	Alert.show("Error processing extension event "+ error);
      	return;
      }
      if(dataObject.command == Command.ADD_REMINDER){
      	handleAddReminder(dataObject);
      }else if(dataObject.command == Command.UPDATE_REMINDER){
      	handleUpdateReminder(dataObject);
      }else if(dataObject.command == Command.QUERY_REMINDERS){
      	handleQueryReminders(dataObject);
      }			
}
public function handleAddReminder(anObject : Object): void{
	if(anObject.Error != null){
		Alert.show(anObject.Exception);
		return;
	}
	if(reminderArray == null){
		reminderArray = new Array();
	}	
	reminderArray.unshift(anObject);
	this.reminderList.dataProvider = reminderArray;
	this.reminderList.selectedIndex = 0;
	Global.showAlert(anObject,anObject.Message);	
}

public function handleUpdateReminder(anObject : Object): void{
	if(anObject.Error != null){
		Alert.show(anObject.Exception);
		return;
	}
	if(reminderArray == null){
		Alert.show("Should not happen");
		reminderArray = new Array();
	}
	var removeIndex : int = -1;
	var createACopy : Boolean = false;
	for(var i : int = 0; i < this.reminderArray.length; i++){
		if(reminderArray[i]["id"] == anObject.id){
			if(anObject.actionTaken == 'true'){
					reminderArray[i] = null;
					createACopy = true;
			}else {
				reminderArray[i]=anObject;
			}
		}
	}
	var copy:Array = new Array();
	for(var i : int = 0; i < this.reminderArray.length; i++){
		if(this.reminderArray[i] != null){
			copy.push(this.reminderArray[i]);
		}
	}
	this.reminderArray = copy;
	this.reminderList.dataProvider = reminderArray;	
	Global.showAlert(anObject, anObject.Message);
} 
public function handleQueryReminders(anObject : Object) : void{
	if(anObject.result != null){
		reminderArray = new Array();
		for(var i : int = 0; i < anObject.result.length; i++){
			reminderArray.push(anObject.result[i]);
		}	
		this.reminderList.dataProvider = reminderArray;
		this.reminderList.dispatchEvent(new Event("Change"));
	}else {
		reminderArray = new Array();
		this.reminderList.dataProvider = reminderArray;
		this.reminderList.dispatchEvent(new Event("Change"));
	}	
}
public function reminderSelected(aReminderWindow: Reminder): void{
	aReminderWindow.idText.text = aReminderWindow.reminderList.selectedItem["id"];
	aReminderWindow.reminderId = parseInt(aReminderWindow.idText.text);
	aReminderWindow.description.text = aReminderWindow.reminderList.selectedItem["description"];
	aReminderWindow.action.selectedItem = aReminderWindow.reminderList.selectedItem["reminderAction"];
	aReminderWindow.picker.selectedDate = aReminderWindow.reminderList.selectedItem["reminderDate"];
	aReminderWindow.actionTaken.selected = (aReminderWindow.reminderList.selectedItem["actionTaken"]=='true');
}

public function clearReminder(aReminder  : Reminder) : void{
	aReminder.reminderId = -1;
	aReminder.idText.text ="-1";
	aReminder.description.text = "";
	aReminder.actionTaken.selected = false;
}
public function saveReminder(aReminder : Reminder) : void{
	var source: Reminder = aReminder;
	var sfs: SmartFoxClient = Global.SFS_CLIENT;
	if(sfs == null){
		Alert.show("Client not initialized. ");
		return;
	}
	try {
		var query: Object = new Object();
		if(source.reminderId == -1){
			query.command = Command.ADD_REMINDER;
		}else {		
			query.command = Command.UPDATE_REMINDER;
		}
		if(source.contactId <=0){
			Alert.show("Invalid contact id. Not creating a reminder");
			return;
		}
		query.id = source.reminderId;
		query.contactId = source.contactId;
		query.reminderAction = source.action.selectedItem;
		query.actionTaken = source.actionTaken.selected;
		query.reminderDate = source.selectedDate.getTime();
		query.description = source.description.text;
		sfs.sendXtMessage(Global.DEFAULT_EXTENSION,
			query.command, query, SmartFoxClient.XTMSG_TYPE_XML, Global.APPLICATION_ROOM_ID);
		source.idText.text = "";
		source.description.text = "";
		source.reminderId = -1;
		source.actionTaken.selected = false;
	}catch(err: Error){
		Alert.show("Error saving notes " + err.message + " " + err.name);
	}	
	
}
	

public function updateReminder(aReminder : Reminder): void{
	
}
public function addReminder(aReminder : Reminder): void{
	
}