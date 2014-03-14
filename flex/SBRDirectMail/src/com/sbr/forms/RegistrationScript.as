// ActionScript file
import com.sbr.commands.Command;
import com.sbr.forms.Global;
import com.sbr.forms.Registration;
import com.sbr.util.Base64;
import com.sbr.util.Util;

import flash.events.Event;
import flash.net.FileReference;
import flash.utils.ByteArray;

import it.gotoandplay.smartfoxserver.SFSEvent;
import it.gotoandplay.smartfoxserver.SmartFoxClient;

import mx.controls.Alert;
import mx.managers.PopUpManager;

private var fileReference: FileReference = new FileReference();
private var imageString : String = "";
private var BREAK_LINES : Boolean = false;
public function initializeRegistration(): void{
	fileReference.addEventListener(Event.SELECT, fileSelected);
	fileReference.addEventListener(Event.COMPLETE,fileLoadComplete);
	Global.SFS_CLIENT.addEventListener(SFSEvent.onExtensionResponse,
		handleExtensionResponse);
}

public function closeWindow(aRegistrationWindow: Registration): void{
	PopUpManager.removePopUp(this);
}

public function fileLoadComplete(event : Event): void{
	this.userImage.data = fileReference.data;
	imageString = Base64.encode64(fileReference.data, BREAK_LINES);
}
public function fileSelected(event: Event): void{
	try {
		fileReference.load();	
	}catch(err : Error){
		Alert.show("Error " + err.name +" " + err.message + " " + err.getStackTrace());
	}	
}
public function uploadImage(aRegistrationWindow: Registration) :void{
	fileReference.browse();
}
public function registerUser(aRegistrationWindow: Registration):void{
	var object = new Object();
	object.command = Command.REGISTER_USER;
	object.registrationId = Util.convertToNumber(this.registrationId.text);
	object.firstName = this.firstName.text;
	object.lastName = this.lastName.text;
	object.loginName = this.loginName.text;
	object.password = this.password.text;
	object.email = this.email.text;
	object.phone = this.phone.text;
	object.fax = this.fax.text;
	object.sms = this.sms.text;
	object.twitterId =this.twitter.text;
	object.userImage = imageString; 
	Global.SFS_CLIENT.sendXtMessage(Global.DEFAULT_EXTENSION, object.command, object,
	SmartFoxClient.XTMSG_TYPE_XML,Global.APPLICATION_ROOM_ID);
}
public function setRegistrationId(anId : int){
	this.registrationId.text = "" + anId;
}
public function clearUser(aRegistrationWindow: Registration) : void{
}
public function handleExtensionResponse(event: SFSEvent): void{
      var type : Object = event.params.type;
      var dataObject : Object = event.params.dataObj;
      var objectKeys : Array = new Array();
      var error  : String = dataObject.Error;
      if(error != null){
      	Alert.show("Error processing extension event "+ error);
      	return;
      }
      if(dataObject.command == Command.REGISTER_USER){
      	handleRegisterUser(dataObject);
      }else if(dataObject.command == Command.QUERY_REGISTRATION){
      	updateRegistrationWindow(dataObject);
      }
}
public function handleRegisterUser(aDataObject : Object) : void{
	if(aDataObject.isApproved){
		Global.LOGIN_SUCCESSFUL = true;
		PopUpManager.removePopUp(this);		
	}else{
		Alert.show("Please contact support to start using the system");
	}	
	
}
public function updateRegistrationWindow(aDataObject : Object): void{
	try {
	if(aDataObject.isApproved == 'true'){
		var objectByteArray: ByteArray = Base64.decode64(aDataObject.userImage);
		this.userImage.data = objectByteArray;		
		this.firstName.text = aDataObject.firstName;
		this.lastName.text = aDataObject.lastName;
		this.loginName.text = aDataObject.loginName;
		this.password.text = aDataObject.password;
		this.phone.text = aDataObject.phone;		
		this.email.text = aDataObject.email;
		this.fax.text = aDataObject.fax;
		this.sms.text = aDataObject.sms;
		this.twitter.text = aDataObject.twitterId;		
	}else{
		Alert.show("Please contact support to start using the system");
	}	
	}catch(err :Error){
		Alert.show("Error " + err.name + " "+ err.message + " " + err.errorID);
	}
}