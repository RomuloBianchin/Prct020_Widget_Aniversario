package com.example.prct020_widget_aniversario;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.Calendar;

//1 - Editar a classe MainActivity, a mesma tem de estender a classe AppWid-getProvider
public class MainActivity extends AppWidgetProvider {

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int appWidgetIds) {

        //Precisamos obter a data atual.
        final Calendar c = Calendar.getInstance(); // Criando um obejto do tipo calendário.
        int mYear = c.get(Calendar.YEAR); // Capturando o ano atual do sistema.
        int mMonth = c.get(Calendar.MONTH); // Capturando o mês atual do sistema.
        int mDay = c.get(Calendar.DAY_OF_MONTH); // Capturando o dia atual do sistema.

        // Obtendo as datas guardadas no Sharedpreferences que foram passadas na classe Config.
        // para tal feito, criamos um novo objeto do tipo SharedPreferences,
        // e junto com o método PreferencesManager e as chaves de acesso, conseguimos recuperar essas informações.
        SharedPreferences datautilizador = PreferenceManager.getDefaultSharedPreferences(context);
        int anoutilizador = datautilizador.getInt("ano",0);
        int mesutilizador = datautilizador.getInt("mes",0)+1;
        int diautilizador = datautilizador.getInt("dia",0);


        final Calendar aniversario = Calendar.getInstance();

        if(mMonth > mesutilizador - 1){
            mYear = mYear + 1;
        }

        if(mMonth == mesutilizador -1 && mDay > diautilizador){
            mYear = mYear + 1;
        }

        aniversario.set(mYear,mesutilizador-1,diautilizador);

        //vamos agora calcular a difererença entre as duas datas em dias.
        long milis1 = c.getTimeInMillis();
        long milis2 = aniversario.getTimeInMillis();
        //Calcular a diferença entre as dudas datas.
        long diff = milis2 - milis1;
        //Convertendo a diferença para dias.
        long diffDays = diff / (24 * 60 * 60 * 1000);

        //Associamos o RemoteViews ao nosso layout do widget.
        RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.activity_main);

        //Colocamos o valor pretendito a caixa de texto do layout.
        views.setTextViewText(R.id.textView2,""+diffDays+ " dias para " + diautilizador+"/"+mesutilizador+"/"+mYear);
        views.setTextColor(R.id.textView2, Color.parseColor("#FF0000"));

        //Após todas as ações, precisamos atualizar o widget.
        appWidgetManager.updateAppWidget(appWidgetIds,views);

        //Um simples alerta para toda vez que um novo wiget for adicioando.
        Toast.makeText(context,"new widget added",Toast.LENGTH_SHORT).show();
        appWidgetManager.updateAppWidget(appWidgetIds,views);

    }
}

