package br.com.vinicius.deixeseurecado;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.vinicius.deixeseurecado.model.Recado;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AdicionarActivity extends AppCompatActivity {

    private EditText mRemetenteEditText;

    private EditText mDestinatarioEditText;

    private EditText mRecadoEditText;

    private Button mSalvarButton;

    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar);

        inicializaComponentes();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("character");

        mSalvarButton.setOnClickListener(v -> {
            String remetente = mRemetenteEditText.getText().toString();
            String destinatario = mDestinatarioEditText.getText().toString();
            String recado = mRecadoEditText.getText().toString();

            Recado mRecado = new Recado(remetente, destinatario, recado);
            String id = mDatabaseReference.push().getKey();
            if (id != null) { mDatabaseReference.child(id).setValue(mRecado); }

            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
    private void inicializaComponentes(){
        mRemetenteEditText = (EditText) findViewById(R.id.remetenteEditText);
        mDestinatarioEditText = (EditText) findViewById(R.id.destinatarioEditText);
        mRecadoEditText = (EditText) findViewById(R.id.recadoEditText);
        mSalvarButton = (Button) findViewById(R.id.salvarButton);

    }


}