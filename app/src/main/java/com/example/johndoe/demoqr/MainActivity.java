package com.example.johndoe.demoqr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Hashtable;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    private Button scanBtn, sobreBtn, codigoBtn, alfaBtn;
    private EditText codigoInput;
    private View codigoView;
    Hashtable<String, RegistroVideo> dicionarioVideos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateTable();

        scanBtn    = (Button)findViewById(R.id.scan_button);
        sobreBtn   = (Button)findViewById(R.id.sobre_button);
        codigoBtn    = (Button)findViewById(R.id.alfa_button);

        codigoView = (View)findViewById(R.id.codigoView);
        codigoInput = (EditText)findViewById(R.id.codigoInput);

        scanBtn.setOnClickListener(this);
        sobreBtn.setOnClickListener(this);
        codigoBtn.setOnClickListener(this);
        //alfaBtn.setOnClickListener(this);

        // http://stackoverflow.com/questions/8225245/enable-and-disable-button-according-to-the-text-in-edittext-in-android
        codigoInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                boolean isReady = codigoInput.getText().toString().length()>0;
                codigoBtn.setEnabled(isReady);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    private void populateTable(){
        dicionarioVideos = new Hashtable<String, RegistroVideo>();

        dicionarioVideos.put("ATRASADO",new RegistroVideo("atrasado.gif","atrasado"));
        dicionarioVideos.put("BAIRRO",new RegistroVideo("bairro.gif","bairro"));
        dicionarioVideos.put("BARATO",new RegistroVideo("barato.gif","barato"));
        dicionarioVideos.put("BICICLETA",new RegistroVideo("bicicleta.gif","bicicleta"));
        dicionarioVideos.put("CIDADE",new RegistroVideo("cidade.gif","cidade"));
        dicionarioVideos.put("CARRO",new RegistroVideo("carro.gif","carro"));
        dicionarioVideos.put("ENGARRAFAMENTO",new RegistroVideo("engarrafamento.gif","engarrafamento"));
        dicionarioVideos.put("ESPERAR",new RegistroVideo("esperar.gif","esperar"));
        dicionarioVideos.put("EUPREFIRO",new RegistroVideo("euprefiro.gif","eu prefiro"));
        dicionarioVideos.put("EUQUERO",new RegistroVideo("euquero.gif","eu quero"));
        dicionarioVideos.put("MOTORISTA",new RegistroVideo("motorista.gif","motorista"));
        dicionarioVideos.put("NAOPODE",new RegistroVideo("naopode.gif","não pode"));
        dicionarioVideos.put("ONDE",new RegistroVideo("onde.gif","onde"));
        dicionarioVideos.put("ONIBUS",new RegistroVideo("onibus.gif","ônibus"));
        dicionarioVideos.put("PASSEAR",new RegistroVideo("passear.gif","passear"));
        dicionarioVideos.put("RAPIDO",new RegistroVideo("rapido.gif","rápido"));
        dicionarioVideos.put("ONIBUSPONTO",new RegistroVideo("pontodebus.gif","ponto de ônibus"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //scan
            case R.id.scan_button: {
                IntentIntegrator scanIntegrator = new IntentIntegrator(this);
                android.app.AlertDialog prompt = scanIntegrator.initiateScan();
                if (prompt != null) {
                    prompt.setTitle("Download necessário");
                    prompt.setMessage("Para ler os códigos do livreto é necessário a instalação do Barcode Scanner. Gostaria de fazer o download do aplicativo?");
                }
                scanIntegrator.addExtra("SCAN_MODE", "QR_CODE_MODE");
                break;
            }

            case R.id.sobre_button: {
                Intent intent = new Intent(this, SobreActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.alfa_button: {
                if (!codigoBtn.getText().equals("Inserir")) {
                    animacaoEntradaInputCodigo();
                    codigoBtn.setText("Inserir");
                    codigoBtn.setEnabled(false);
                } else {
                    if (!codigoInput.getText().toString().matches("")) {
                        RegistroVideo registro = dicionarioVideos.get(codigoInput.getText().toString().toUpperCase());

                        if (registro != null) {
                            Intent i = new Intent(this, VideoRoot.class);
                            i.putExtra("nomeVideo", registro.nomeArquivo);
                            i.putExtra("nomeApresentacao", registro.nomeApresentacao);
                            startActivity(i);
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    getApplicationContext().getString(R.string.erroCodigoInvalido), Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                    break;
                }
            }
        }
    }

    // http://stackoverflow.com/questions/6796139/fade-in-fade-out-android-animation-in-java/6822116#6822116
    public void animacaoEntradaInputCodigo(){
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setStartOffset(100);
        fadeOut.setDuration(500);

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setStartOffset(500);
        fadeIn.setDuration(1000);

        AnimationSet animation = new AnimationSet(false); //change to false
        AnimationSet animation2 = new AnimationSet(false); //change to false

        animation.addAnimation(fadeOut);
        animation2.addAnimation(fadeIn);

        codigoView.setAnimation(animation);
        codigoBtn.setAnimation(animation);

        codigoInput.setAnimation(animation2);
        codigoBtn.setAnimation(animation2);


        codigoView.setVisibility(View.INVISIBLE);
        codigoInput.setVisibility(View.VISIBLE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        System.out.println(scanningResult.getFormatName());


        if(scanningResult.getFormatName() != null){
            if (scanningResult.getFormatName().equals("QR_CODE")){
                //we have a result
                String scanContent = scanningResult.getContents();
                RegistroVideo registro = dicionarioVideos.get(scanContent);

                if( registro != null){
                    Intent i= new Intent(this,VideoRoot.class);
                    i.putExtra("nomeVideo",registro.nomeArquivo);
                    i.putExtra("nomeApresentacao",registro.nomeApresentacao);
                    startActivity(i);
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(),
                            getApplicationContext().getString(R.string.erroCodigoInvalido) , Toast.LENGTH_SHORT);
                    toast.show();
                }

            }else{
                Toast toast = Toast.makeText(getApplicationContext(),
                        getApplicationContext().getString(R.string.erroCodigoLeitura), Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }
}
