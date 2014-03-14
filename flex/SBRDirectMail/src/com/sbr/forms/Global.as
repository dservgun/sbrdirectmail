package com.sbr.forms
{
	import it.gotoandplay.smartfoxserver.SmartFoxClient;
	
	import mx.controls.Alert;
	public class Global
	{
		public static var DEFAULT_EXTENSION: String = "DirectMailExtension";
		public static var LOGIN_ID : int = -1;
		public static var APPLICATION_ROOM_ID: int = -1;
		public static var SFS_CLIENT : SmartFoxClient;
		public static var LOGIN_SUCCESSFUL : Boolean;
		public static function showGenericAlert(aMessage : String){
			Alert.show("Inside Generic alert " + aMessage);
		}
		public static function showAlert(anObject : Object, aMessage : String){
			if(anObject.modifiedBy == null){
				if(anObject.createdBy == SFS_CLIENT.myUserName){
					Alert.show(aMessage);
				}
			}else if(anObject.modifiedBy == SFS_CLIENT.myUserName){
				Alert.show(aMessage);
			}
		}
	}
}