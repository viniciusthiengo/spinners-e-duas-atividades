package thiengo.com.br.spinnerseduasatividades;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);

        if( getIntent() == null
            || getIntent().getIntExtra(MainActivity.KEY_AGREGADO_G,0) == 0
            || getIntent().getIntExtra(MainActivity.KEY_SLUMP,0) == 0 ){

            Toast.makeText(this, "Dados não enviados, tente novamente.", Toast.LENGTH_LONG).show();
            finish();
        }
        else{
            /*
             * Note que não preciso nenhum cálculo com os números das posições
             * escolhidas nos Spinners "AgragadoG" e "Slump". Os valores retornados
             * são como esperados.
             * */

            int agregadoG = getIntent().getIntExtra(MainActivity.KEY_AGREGADO_G,0);
            int slump = getIntent().getIntExtra(MainActivity.KEY_SLUMP,0);

            int[][] matrizConsumoAgua = {{40, 50, 55, 41, 70}, {45, 51, 30, 42, 43}, {34, 32, 50, 62, 45}};
            int consumoAgua = matrizConsumoAgua[ agregadoG ][ slump ];

            /*
             * O resultado do consumo de água foi colocado em um TextView para que o
             * usuário não possa atualiza-lo e somente visualiza-lo.
             * */
            TextView tvConsumoAgua = findViewById(R.id.tv_consumo_agua);
            tvConsumoAgua.setText( String.valueOf( consumoAgua ) );
        }
    }
}
