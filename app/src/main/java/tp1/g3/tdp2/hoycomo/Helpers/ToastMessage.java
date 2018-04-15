package tp1.g3.tdp2.hoycomo.Helpers;

import android.content.Context;
import android.widget.Toast;

public class ToastMessage {

    public static void toastMessage(Context context, String msg) {
        toastMessageLargo(context, msg);
    }
    public static void toastMessageCorto(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
    public static void toastMessageLargo(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

}
