package bubtjobs.com.hungama.Others;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class AlertDialogManager {
	
	public void showAlertDialog(final Context context, String title, String message, final boolean status){
		AlertDialog alertDialog=new AlertDialog.Builder(context).create();
		
		//setting dialog title
		alertDialog.setTitle(title);
		
		//setting dialog message
		alertDialog.setMessage(message);
		
		//setting dialog icon
		//alertDialog.setIcon((status)?R.drawable.success:R.drawable.fail);
		
		
		//setting ok button
		alertDialog.setButton("OK",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(status) {

				}
				else{

				}
			}
		});
		
		//showing alert message
		alertDialog.show();
		
	}

}
