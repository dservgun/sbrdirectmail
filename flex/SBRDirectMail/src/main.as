/**
 *	SmartFoxClient Actionscript 3.0 code template
 *	version 1.0.0
 * 
 * (c) 2004-2007 gotoAndPlay()
 * www.smartfoxserver.com
 * www.gotoandplay.it 
*/
import com.sbr.commands.Command;
import com.sbr.forms.Global;
import com.sbr.forms.Login;
import com.sbr.forms.Registration;
import com.sbr.util.Base64;

import flash.events.Event;
import flash.events.TimerEvent;
import flash.utils.Timer;

import it.gotoandplay.smartfoxserver.SFSEvent;
import it.gotoandplay.smartfoxserver.SmartFoxClient;

import mx.controls.Alert;
import mx.managers.PopUpManager;
private const NEWLINE:String = "\n";
private var sfs:SmartFoxClient;

private var keepAliveTimer : Timer;
private var keepAliveTimerInterval : int;
public var categories = ["Environment", "Heating Oil Company", "Tank cleaning","Marinas","Cruises"];
private var loginWindow  : Login = new Login();
private var registrationWindow : Registration = new Registration();

public function main():void
{
	sfs = new SmartFoxClient(true);
	Global.SFS_CLIENT = sfs;
	keepAliveTimerInterval = 3000;
	keepAliveTimer = new Timer(keepAliveTimerInterval);
	keepAliveTimer.addEventListener(TimerEvent.TIMER, keepAliveHandler);
	// Register for SFS events
	sfs.addEventListener(SFSEvent.onConnection, onConnection);
	sfs.addEventListener(SFSEvent.onConnectionLost, onConnectionLost);
	sfs.addEventListener(SFSEvent.onLogin, onLogin);
	sfs.addEventListener(SFSEvent.onRoomListUpdate, onRoomListUpdate);
	sfs.addEventListener(SFSEvent.onJoinRoom, onJoinRoom);
	sfs.addEventListener(SFSEvent.onJoinRoomError, onJoinRoomError);
	sfs.addEventListener(SFSEvent.onExtensionResponse, handleExtensionResponse);
	
	// Register for generic errors
	sfs.addEventListener(SecurityErrorEvent.SECURITY_ERROR, onSecurityError)
	sfs.addEventListener(IOErrorEvent.IO_ERROR, onIOError);
	sfs.loadConfig("config.xml", false);
	if (!sfs.isConnected){
		sfs.connect(sfs.ipAddress, sfs.port);
	}
	else {
		debugTrace("You are already connected!");
	}
	this.contactForm.initializeSFS(sfs);
	this.notesList.initializeNotes();
	this.reminder.initializeReminders();
	this.registrationWindow.initializeRegistration();

}

/**
 * Handles the button click
 * Establishes a connection to the local server (127.0.0.1), default TCP port (9339)
 */
public function bt_connect_click(evt:Event):void
{
	if (!sfs.isConnected)
		sfs.connect("127.0.0.1");
	else
		debugTrace("You are already connected!");
}

/**
 * Handle connection
 */
public function onConnection(evt:SFSEvent):void
{
	var success:Boolean = evt.params.success;
	
	if (success)
	{
		PopUpManager.addPopUp(loginWindow,this,true);
		PopUpManager.centerPopUp(loginWindow);
	}
	else
	{
		debugTrace("Connection failed!");	
	}
}

/**
 * Handle connection lost
 */
public function onConnectionLost(evt:SFSEvent):void
{
	Alert.show("Connection lost!");
	if(sfs.isConnected){
		sfs.disconnect();
		sfs.connect(sfs.ipAddress, sfs.port);
	}
}

/**
 * Handle login response
 */
public function onLogin(evt:SFSEvent):void
{
	var returnedObject = evt.params.dataObj;
	
	if (returnedObject.success != null)
	{
		PopUpManager.removePopUp(loginWindow);
		debugTrace("Successfully logged in");
	}
	else
	{
		Alert.show("Login failed " + returnedObject.command);
	}
}

/**
 * Handle room list
 */
public function onRoomListUpdate(evt:SFSEvent):void
{
	debugTrace("Room list received");
	
	// Tell the server to auto-join us in the default room for this Zone
	sfs.autoJoin();
}

/**
 * Handle successfull join
 */
public function onJoinRoom(evt:SFSEvent):void
{
	Global.APPLICATION_ROOM_ID = evt.params.room.getId();
	Global.SFS_CLIENT = sfs;
	debugTrace("Successfully joined room: " + evt.params.room.getId());	
	keepAliveTimer.start();
}

/**
 * Handle problems with join
 */
public function onJoinRoomError(evt:SFSEvent):void
{
	Global.LOGIN_SUCCESSFUL = false;
	Global.showGenericAlert("Error joining room ");	
}

/**
 * Handle a Security Error
 */
public function onSecurityError(evt:SecurityErrorEvent):void
{
	debugTrace("Security error: " + evt.text);
}

/**
 * Handle an I/O Error
 */
public function onIOError(evt:IOErrorEvent):void
{
	debugTrace("I/O Error: " + evt.text);
}

/**
 * Trace messages to the debug text area
 */
public function debugTrace(msg:String):void
{
	//Alert.show("--> " + msg + NEWLINE);
}
public function getSFS() : SmartFoxClient{
	return sfs;
}
public function keepAliveHandler(event : Event): void{
	sfs.sendPublicMessage("Keep alive message...");
}
public function saveNotesForCustomer(aCustomerId: String, aNotesText: String) : void{
	
}
public function handleExtensionResponse(event : SFSEvent): void{
	var dataObject = event.params.dataObj;
	if(dataObject.command == Command.VALIDATE_LOGIN){
		if(dataObject.success == null){
			var modal : Boolean = false;
			PopUpManager.addPopUp(registrationWindow,this,modal);
			PopUpManager.centerPopUp(registrationWindow);
		}else{
			this.userID.text = dataObject.loginName;
			this.loggedInUser.data = Base64.decode64(dataObject.userImage);
			Global.LOGIN_SUCCESSFUL = true;
			Global.LOGIN_ID = dataObject.id;
			
		}
	}else if(dataObject.command == Command.REGISTER_USER){
		this.userID.text = dataObject.loginName;
		this.loggedInUser.data = Base64.decode64(dataObject.userImage);		
	}
}
public function updateRegistration(): void{
	try {
	PopUpManager.addPopUp(this.registrationWindow, this, true);
	PopUpManager.centerPopUp(registrationWindow);
	this.registrationWindow.setRegistrationId(Global.LOGIN_ID);
	var object : Object = new Object();
	object.command = Command.QUERY_REGISTRATION;
	object.registrationId = Global.LOGIN_ID;
	sfs.sendXtMessage(Global.DEFAULT_EXTENSION,
		object.command, object, SmartFoxClient.XTMSG_TYPE_XML,
		Global.APPLICATION_ROOM_ID);	
	}catch(err: Error){
		Alert.show("Error " + err.message + " "+ err.name + " " + err.errorID + " " + err.getStackTrace());
	}
}

