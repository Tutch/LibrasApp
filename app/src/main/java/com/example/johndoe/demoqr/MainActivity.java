package com.example.johndoe.demoqr;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Hashtable;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    private Button scanBtn;
    private TextView formatTxt, contentTxt;
    Hashtable<String, String> dicionarioVideos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateTable();

        scanBtn    = (Button)findViewById(R.id.scan_button);
        formatTxt  = (TextView)findViewById(R.id.scan_format);
        contentTxt = (TextView)findViewById(R.id.scan_content);

        scanBtn.setOnClickListener(this);
    }

    private void populateTable(){
        dicionarioVideos = new Hashtable<String,String>();
        dicionarioVideos.put("vcvaicomo","vcvaicomo.gif");
    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.scan_button){
            //scan
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            android.app.AlertDialog prompt = scanIntegrator.initiateScan();
            if(prompt != null) {
                prompt.setTitle("Download necessário");
                prompt.setMessage("Para ler os códigos do livreto é necessário a instalação do Barcode Scanner. Gostaria de fazer o download do aplicativo?");
            }
            scanIntegrator.addExtra("SCAN_MODE","QR_CODE_MODE");
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if ((scanningResult != null) && (scanningResult.getFormatName().equals("QR_CODE"))){
            //we have a result
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();

            String nomeVideo = dicionarioVideos.get(scanContent);

            if( nomeVideo != null){
                Intent i= new Intent(this,VideoRoot.class);
                i.putExtra("nomeVideo",nomeVideo);
                startActivity(i);
            }else{
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Código inválido, certifique-se que está utilizando um código válido.", Toast.LENGTH_SHORT);
                toast.show();
            }

        }else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Erro ao ler código.", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
