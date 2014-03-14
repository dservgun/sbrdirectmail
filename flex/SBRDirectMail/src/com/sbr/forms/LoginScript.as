// ActionScript file
import com.sbr.forms.Global;
import com.sbr.forms.Login;

import it.gotoandplay.smartfoxserver.SFSEvent;

import mx.controls.Alert;
import mx.managers.PopUpManager;
public function closeLoginWindow(aWindow: Login): void{	
	PopUpManager.removePopUp(this);
}

public function loginToServer():void{
	if(this.userName.text == ""){
		Alert.show("Please enter a valid user name");
	}
	Global.SFS_CLIENT.login(Global.SFS_CLIENT.defaultZone,
	this.userName.text, this.password.text);
	Global.SFS_CLIENT.addEventListener(SFSEvent.onJoinRoom,joinRoom);
}
public function clear(): void{
	this.userName.text="";
	this.password.text ="";
}
public function joinRoom(event: SFSEvent){
	PopUpManager.removePopUp(this);
}