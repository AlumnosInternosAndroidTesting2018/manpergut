package us.manpergut.crashapp2;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;
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

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import us.manpergut.crashapp2.UtilFichero;

/**
 * Created by manuel on 6/03/18.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
@MediumTest
public class TestMultiActivityButton {

    private static final String TAGLOG = "Test1";
    private static final String BASIC_PACKAGE = "us.manpergut.crashapp2";
    private static final long TIMEOUT = 5000;
    private static final String application = "CrashApp2";

    private static UiDevice uiDevice;
    private static final String dir = "./fichero/grafo1.txt";


    private static String TAGFile = "Test - "+application+ " - " +Calendar.getInstance().get(Calendar.HOUR_OF_DAY)+":"+Calendar.getInstance().get(Calendar.MINUTE);

    //No estoy muy seguro
    //Pero algo así como decir a UIAutomator que se debe ejecutar sobre esa clase
    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<MainActivity>(MainActivity.class);

    //Inicialización del test.
    //Decimos que carge todos los parámetros y se inicie desde el lanzador/escritorio.
    @Before
    public void setUp() throws RemoteException {
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
    public void test() throws UiObjectNotFoundException, RemoteException, IOException{

        uiDevice.pressHome();

        UiObject appLaunch = uiDevice.findObject(new UiSelector().description("Apps"));
        UiObject app;
        appLaunch.clickAndWaitForNewWindow(1500);

        UiScrollable appView = new UiScrollable(new UiSelector().scrollable(true));
        appView.setAsHorizontalList();
        app = appView.getChildByText(new UiSelector()
                .className(android.widget.TextView.class.getName()), application);
        app.clickAndWaitForNewWindow(1500);

        BySelector by = By.clazz("android.widget.Button");
        List<UiObject2> l = uiDevice.findObjects(by);
        if(l.size()>0){
            buscaBotonesArbol(l, 0, "CrashApp2");
        }
    }

    private void buscaBotonesArbol(List< UiObject2 > l, int n, String parent) throws
            UiObjectNotFoundException , IOException{
        UtilFichero.escribeFichero("grafo"+TAGFile+".txt", parent+" -id:"+n+" => [");
        for(UiObject2 o : l){
            n++;
            try {
                String t = o.getText();
                boolean r = o.clickAndWait(Until.newWindow(), TIMEOUT / 2);
                if (r) {
                    UtilFichero.escribeFichero("grafo"+TAGFile+".txt", t+" - id:"+n+"]\n");
                    List<UiObject2> l1 = uiDevice.findObjects(By.clazz("android.widget.Button"));
                    buscaBotonesArbol(l1, n, t);
                }
            }catch(Exception e) {
                //e.printStackTrace();
                UtilFichero.escribeFichero("grafo" + TAGFile + ".txt", e.getMessage() + "]\n");
            }
        }
    }


    @After
    public void terminaTest() throws RemoteException{
        //Al terminar el test, sale de la aplicación
        uiDevice.pressHome();
        uiDevice.wait(Until.hasObject(By.pkg(getLauncherPackageName()).depth(0)), TIMEOUT);
        //Bloquea el terminal
        uiDevice.sleep();
    }

}
