package thiengo.com.br.spinnerseduasatividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String TAG_DEBUG = "DEBUG_LOG";

    public static final String KEY_AGREGADO_G = "KEY_AGREGADO_G";
    public static final String KEY_SLUMP = "KEY_SLUMP";

    private Spinner spDesvio;
    private String[] desvio = {"4 MPa", "5 MPa", "7 MPa"};

    private Spinner spAgregadoG;
    private String[] agregadoG = {"9 mm", "11 mm", "12 mm", "15 mm", "16 mm"};

    private Spinner spSlump;
    private String[] slump = {"1 mm", "2 mm", "3 mm"};

    private EditText etFck;
    private EditText etMf;
    private EditText etMg;
    private EditText etMm;
    private EditText etMgg;
    private EditText etMc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciandoCampos();
        iniciandoBotaoMostrar();
        iniciandoBotaoProsseguir();
    }

    private void iniciandoCampos(){
        // Spinners
            spDesvio = (Spinner) findViewById(R.id.sp_desvio);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, desvio);
            spDesvio.setAdapter(adapter);

            spAgregadoG = (Spinner) findViewById(R.id.sp_agregado_g);
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, agregadoG);
            spAgregadoG.setAdapter(adapter);

            spSlump = (Spinner) findViewById(R.id.sp_slump);
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, slump);
            spSlump.setAdapter(adapter);

        // EditTexts
            etFck = (EditText) findViewById(R.id.et_fck);
            etMf = (EditText) findViewById(R.id.et_mf);
            etMg = (EditText) findViewById(R.id.et_mg);
            etMm = (EditText) findViewById(R.id.et_mm);
            etMgg = (EditText) findViewById(R.id.et_mgg);
            etMc = (EditText) findViewById(R.id.et_mc);
    }

    private void iniciandoBotaoMostrar(){
        View bt = findViewById(R.id.bt_mostrar);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                 * Padrão Cláusula de Guarda. Mais sobre ele no link a seguir:
                 * https://www.thiengo.com.br/padrao-de-projeto-clausula-de-guarda
                 * */
                if( etFck.getText().toString().isEmpty() ){
                    Toast.makeText(MainActivity.this, "Foneça uma entrada em Fck", Toast.LENGTH_SHORT).show();

                    /*
                     * Para garantir que o processamento não continuará, pois o
                     * Fcj não fornecido.
                     */
                    return;
                }

                double fcj = calculaFcj();
                apresentaFcj( fcj );
            }
        });
    }

    private double calculaFcj(){
        double fck;
        double fcj = 0;

        fck = Double.parseDouble( etFck.getText().toString() );

        /*
         * No Android Studio não se utiliza System.out e sim Log.
         * Mais sobre Log no link a seguir:
         * https://developer.android.com/studio/debug/am-logcat?hl=pt-br
         * */
        Log.i(TAG_DEBUG, String.format("%.2f", fcj) ); // Aqui sempre é 0

        switch( spDesvio.getSelectedItemPosition() ){
            case 0:
                fcj = fck + (1.65 * 4);
                break;
            case 1:
                fcj = fck + (1.65 * 5);
                break;
            default: // case 2:
                fcj = fck + (1.65 * 7);
        }

        return fcj;
    }

    private void apresentaFcj( double fcj ){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(MainActivity.this);

        dialogo.setTitle("Fcj");
        dialogo.setMessage("Seu Fcj é de: " + String.valueOf(fcj) + " " + "MPa");
        dialogo.setNegativeButton("OK", null);
        dialogo.show();
    }

    private void iniciandoBotaoProsseguir(){
        View bt = findViewById(R.id.bt_prosseguir);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if( ehVazio( etFck )
                    || ehVazio( etMf )
                    || ehVazio( etMg )
                    || ehVazio( etMm )
                    || ehVazio( etMgg )
                    || ehVazio( etMc ) ){

                    Toast.makeText(MainActivity.this,"Insira todos os campos",Toast.LENGTH_SHORT).show();
                    return; // Para parar o processamento, pois algum dados não foi fornecido.
                }

                invocarAtividade2();
            }
        });
    }

    /*
     * Método para minimizar todo o algoritmo de verificação
     * de campos preenchidos antes de invocar a Main2Activity.
     * */
    private boolean ehVazio( EditText et ){
        return et.getText().toString().isEmpty();
    }

    /*
     * Invoca a atividade que apresentará o consumo de água. Para entender mais
     * sobre como utilizar a Intent e como passar dado de uma atividade a outra,
     * estude os conteúdos dos links a seguir:
     *
     * https://www.thiengo.com.br/classe-intent-e-o-conceito-de-pilha-em-android
     *
     * https://www.thiengo.com.br/passagem-de-parametros-de-uma-atividade-para-a-outra-em-android
     *
     * https://www.thiengo.com.br/entendendo-e-utilizando-intentfilter-no-android
     * */
    private void invocarAtividade2(){
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);

        intent.putExtra( KEY_AGREGADO_G, spAgregadoG.getSelectedItemPosition() );
        intent.putExtra( KEY_SLUMP, spSlump.getSelectedItemPosition() );

        startActivity(intent);
    }
}
