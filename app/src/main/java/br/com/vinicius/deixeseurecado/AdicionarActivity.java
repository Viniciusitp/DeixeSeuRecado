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

    @BindView(R.id.remetenteEditText)
    EditText mRemetenteEditText;

    @BindView(R.id.destinatarioEditText)
    EditText mDestinatarioEditText;

    @BindView(R.id.recadoEditText)
    EditText mRecadoEditText;

    @BindView(R.id.salvarButton)
    Button mSalvarButton;

    private DatabaseReference mDatabaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar);

        ButterKnife.bind(this);

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
        });
    }
}