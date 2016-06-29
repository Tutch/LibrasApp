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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Hashtable;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    private Button scanBtn, alfaBtn;
    private EditText alfaText;
    Hashtable<String, RegistroVideo> dicionarioVideos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateTable();

        scanBtn    = (Button)findViewById(R.id.scan_button);
        //alfaBtn    = (Button)findViewById(R.id.alfa_button);
        //alfaText   = (EditText) findViewById(R.id.alfa_text);

        scanBtn.setOnClickListener(this);
        //alfaBtn.setOnClickListener(this);

        // http://stackoverflow.com/questions/8225245/enable-and-disable-button-according-to-the-text-in-edittext-in-android
       /* alfaText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                boolean isReady = alfaText.getText().toString().length()>0;
                alfaBtn.setEnabled(isReady);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });*/
    }

    private void populateTable(){
        dicionarioVideos = new Hashtable<String, RegistroVideo>();
        dicionarioVideos.put("vcvaicomo",new RegistroVideo("vcvaicomo.gif","Como você vai?"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View v){
        switch(v.getId()) {
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

            //alfanumerico
            /*case R.id.alfa_button: {
                if(!alfaText.getText().toString().matches("")){
                    RegistroVideo registro = dicionarioVideos.get(alfaText.getText().toString());

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
                }
                break;
            }*/
        }
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
