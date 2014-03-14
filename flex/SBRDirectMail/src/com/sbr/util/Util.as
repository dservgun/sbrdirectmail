package com.sbr.util
{
	import mx.controls.Alert;
	public class Util
	{
		public function Util()
		{
		}
		public static function convertToNumber(aString: String): Number{
			try {
				var result: int = parseInt(aString);
				return result;
			}catch(err: Error){
				Alert.show("Invalid number " + aString);
				return -1;
			}
			return -1;
		}


	}
}