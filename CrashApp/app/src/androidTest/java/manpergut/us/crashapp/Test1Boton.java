package manpergut.us.crashapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.content.pm.PackageManager;
import android.support.test.filters.SdkSuppress;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;



/**
 * Created by manuel on 1/03/18.
 */

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
//@LargeTest

public class Test1Boton {

    private static final String TAG = "Test1";
    private static final String BASIC_PACKAGE = "manpergut.us.crashapp";
    private static final long TIMEOUT = 5000;
    private static final String application = "CrashApp";

    private UiDevice uiDevice;

    //No estoy muy seguro
    //Pero algo así como decir a UIAutomator que se debe ejecutar sobre esa clase
    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<MainActivity>(MainActivity.class);

    //Inicialización del test.
    //Decimos que carge todos los parámetros y se inicie desde el lanzador/escritorio.
    @Before
    public void setUp() throws RemoteException{
        //Inicializa la instacia del dispositivo
        uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        //Si el dispostivo está bloqueado (sin protección), desbloqueado.
        if(!uiDevice.isScreenOn()){
            int alturaYIni = uiDevice.getDisplayHeight()-100;
            int alturaYFin = 100;
            int anchuraXPantalla = uiDevice.getDisplayWidth()/2;
            uiDevice.wakeUp();//Enciende la pantalla
            //uiDevice.swipe( uiDevice.getDisplayWidth()/2,uiDevice.getDisplayHeight()-100, uiDevice.getDisplayWidth()/2, 100,180);
            uiDevice.swipe( anchuraXPantalla, alturaYIni, anchuraXPantalla, alturaYFin, 180);//Desliza sobre la pantalla para desbloquearla, de abajo arriba; ~1s
        }
        //Sale al launcher
        uiDevice.pressHome();
        //Espera la respuesta del lanzador.
        uiDevice.wait(Until.hasObject(By.pkg(getLauncherPackageName()).depth(0)), TIMEOUT);
        //Lanza la aplicacion
        Context con =InstrumentationRegistry.getContext();
        final Intent inte = con.getPackageManager().getLaunchIntentForPackage(BASIC_PACKAGE);
        //Limpia las instancias/cierra otros procesos de la app
        inte.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //inicia un nuevo contexto/actividad
        con.startActivity(inte);
        //Espera la aparición de la app
        uiDevice.wait(Until.hasObject(By.pkg(BASIC_PACKAGE).depth(0)), TIMEOUT);

    }

    private String getLauncherPackageName(){
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        PackageManager pm = InstrumentationRegistry.getContext().getPackageManager();

        ResolveInfo res = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return res.activityInfo.packageName;
    }

    @Test
    public void accedeAplicacionYTodosBotonesAuto() throws UiObjectNotFoundException, RemoteException {
        uiDevice.pressHome();

        UiObject appLaunch = uiDevice.findObject(new UiSelector().description("Apps"));

        appLaunch.click();

        UiScrollable appView = new UiScrollable(new UiSelector().scrollable(true));
        appView.setAsHorizontalList();

        UiObject app = appView.getChildByText(new UiSelector()
                .className(android.widget.TextView.class.getName()), application);
        app.clickAndWaitForNewWindow(1500);

        BySelector selBton =By.clazz("android.widget.Button");
        List<UiObject2> lista = uiDevice.findObjects(selBton);//.stream().collect(Collectors.<UiObject2>toList());
        for(UiObject2 o : lista){
            o.clickAndWait(Until.newWindow(), TIMEOUT/2);
            Log.i(TAG, "PRUEBALog"+o.getText());
        }

    }

    @After
    public void terminaTest() throws RemoteException{
        //Al terminar el test, sale de la aplicación
        uiDevice.pressHome();
        //Bloquea el terminal
        uiDevice.sleep();
    }

}
