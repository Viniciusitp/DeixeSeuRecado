package br.com.vinicius.deixeseurecado;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import br.com.vinicius.deixeseurecado.model.Recado;

public class AdicionarActivity extends AppCompatActivity {

    private EditText remetenteEditText;
    private EditText destinatarioEditText;
    private EditText recadoEditText;
    private Button salvarButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar);

        inicializaComponentes();
        databaseReference = FirebaseDatabase.getInstance().getReference("recado");

        salvarButton.setOnClickListener(v -> {
            String remetente = remetenteEditText.getText().toString();
            String destinatario = destinatarioEditText.getText().toString();
            String recado = recadoEditText.getText().toString();

            Recado mRecado = new Recado(remetente, destinatario, recado);
            String id = databaseReference.push().getKey();
            if (id != null) { databaseReference.child(id).setValue(mRecado); }

            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
    private void inicializaComponentes(){
        remetenteEditText = (EditText) findViewById(R.id.remetenteEditText);
        destinatarioEditText = (EditText) findViewById(R.id.destinatarioEditText);
        recadoEditText = (EditText) findViewById(R.id.recadoEditText);
        salvarButton = (Button) findViewById(R.id.salvarButton);
    }
}