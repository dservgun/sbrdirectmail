// ActionScript file

import com.sbr.commands.Command;
import com.sbr.forms.Global;
import com.sbr.forms.Notes;

import flash.events.FocusEvent;

import it.gotoandplay.smartfoxserver.SFSEvent;
import it.gotoandplay.smartfoxserver.SmartFoxClient;

import mx.controls.Alert;

private var notesArray:Array;
public var notesId: int = -1;
public var contactId : int = -1;
public function initializeNotes(): void{
	try {

		Global.SFS_CLIENT.addEventListener(SFSEvent.onExtensionResponse,handleNotesResponse);
		this.notesText.addEventListener(FocusEvent.KEY_FOCUS_CHANGE,saveNotes);
	}catch(err :Error){
		Alert.show(" " + err.message + " " + err.name);
	}
}

public function saveNotes(aNotesWindow : Notes) : void{
	var source: Notes = aNotesWindow;
	var sfs: SmartFoxClient = Global.SFS_CLIENT;
	if(sfs == null){
		Alert.show("Client not initialized. ");
		return;
	}
	try {
		var query: Object = new Object();
		if(source.contactId <= 0){
			Alert.show("Invalid contact. Cannot add a note");
			return;
		}
		if(source.notesId == -1){
			query.command = Command.ADD_NOTES;
		}else {		
			query.command = Command.UPDATE_NOTES;
		}
		query.id = source.notesId;
		query.contactId = source.contactId;
		query.notes = source.notesText.text;
		sfs.sendXtMessage(Global.DEFAULT_EXTENSION,
			query.command, query, SmartFoxClient.XTMSG_TYPE_XML, Global.APPLICATION_ROOM_ID);
		source.notesText.text = "";
		source.idText.text = "-1";
		source.notesId= -1;
	}catch(err: Error){
		Alert.show("Error saving notes " + err.message + " " + err.name);
	}	
	
}

public function notesSelected(aNotesWindow : Notes): void{
	aNotesWindow.notesText.text = aNotesWindow.notesList.selectedItem["notes"];
	aNotesWindow.idText.text =aNotesWindow.notesList.selectedItem["id"];
	aNotesWindow.notesId = parseInt(aNotesWindow.idText.text);
}
public function handleNotesResponse(event :SFSEvent): void{
      var type : Object = event.params.type;
      var dataObject : Object = event.params.dataObj;		      
      var error  : String = dataObject.Error;
      if(error != null){
      	Alert.show("Error processing extension event "+ error);
      	return;
      }
      if(dataObject.command == Command.ADD_NOTES){
      	handleAddNotes(dataObject);
      }else if(dataObject.command == Command.UPDATE_NOTES){
      	handleUpdateNotes(dataObject);
      }else if(dataObject.command == Command.QUERY_NOTES){
      	handleQueryNotes(dataObject);
      }	
}
public function handleUpdateNotes(anObject : Object): void{
	if(anObject.Error != null){
		Alert.show(anObject.Exception);
		return;
	}
	if(notesArray == null){
		Alert.show("Should not happen");
		notesArray = new Array();
	}
	for(var i : int = 0; i < this.notesArray.length; i++){
		if(notesArray[i]["id"] == anObject.id){
			notesArray[i]=anObject;
		}
	}
	this.notesList.dataProvider = notesArray;	
}
public function clearNotes(aNotesWindow : Notes): void{
	aNotesWindow.idText.text = "-1";
	aNotesWindow.notesText.text = "";
}
public function handleAddNotes(anObject : Object): void{
	if(anObject.Error != null){
		Alert.show(anObject.Exception);
		return;
	}
	if(notesArray == null){
		notesArray = new Array();
	}	
	notesArray.unshift(anObject);
	this.notesList.dataProvider = notesArray;
	this.notesList.selectedIndex = 0;
}
public function handleQueryNotes(anObject : Object){
	if(anObject.result != null){
		notesArray = new Array();
		for(var i : int = 0; i < anObject.result.length; i++){
			notesArray.push(anObject.result[i]);
		}	
		this.notesList.dataProvider = notesArray;
	}else {
		notesArray = new Array();
		this.notesList.dataProvider = notesArray;
	}	
}