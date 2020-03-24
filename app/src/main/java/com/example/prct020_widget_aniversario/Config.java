package com.example.prct020_widget_aniversario;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;

public class Config extends AppCompatActivity {

    private int mAppWidgetId;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_CANCELED);
        setContentView(R.layout.configuracao);

        //Associamos o date picker do layout a váriavel data utilizador.
        final DatePicker datautilizador = (DatePicker) findViewById(R.id.datePicker1);
        //Iniciamos a data do date picker em 1980.
        datautilizador.init(1980,0,1,null);

        //Precisamos obter o ID do widget.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null){
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        //Se não obtivermos o id, fechamos a atividade.
        if(mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID){
            finish();
        }

        //Associamos o obejto Button
        Button bt1 = (Button) findViewById(R.id.botao);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Armazenando as datas do DatePicker assim que o botão for clicado.
                int year = datautilizador.getYear();
                int month = datautilizador.getMonth();
                int day = datautilizador.getDayOfMonth();
                final Context context = Config.this;

                //Guardando a data no objeto da Classe SharePreferences.
                SharedPreferences preferences  = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("ano", year);
                editor.putInt("mes", month);
                editor.putInt("dia", day);
                editor.apply();
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);


                //Vamos agora para a Classe MainActivity para obtermos
                // a data do utilizador, rea-lizarmos os cálculos e apresentarmos o resultado nas textview.
                MainActivity InstanciaMainActivity = new MainActivity();
                InstanciaMainActivity.onUpdate(context,appWidgetManager,mAppWidgetId);
                Intent resultValue = new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,mAppWidgetId);
                setResult(RESULT_OK,resultValue);
                finish();
            }
        });


    }
}
