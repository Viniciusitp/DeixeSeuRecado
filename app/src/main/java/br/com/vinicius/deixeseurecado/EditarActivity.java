package br.com.vinicius.deixeseurecado;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import br.com.vinicius.deixeseurecado.model.Recado;

public class EditarActivity extends AppCompatActivity {

    private EditText remetenteEditText;
    private EditText destinatarioEditText;
    private EditText recadoEditText;
    private Button salvarButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        inicializaComponentes();
        String mKey = Objects.requireNonNull(getIntent().getExtras()).getString("key");

        databaseReference = FirebaseDatabase.getInstance().getReference("recado").child(mKey);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Recado recado = dataSnapshot.getValue(Recado.class);

                if (recado.getRemetente() != null) {
                    remetenteEditText.setText(recado.getRemetente());
                }

                if (recado.getDestinatario() != null) {
                    destinatarioEditText.setText(recado.getDestinatario());
                }

                if (recado.getRecado() != null) {
                    recadoEditText.setText(recado.getRecado());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(EditarActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        salvarButton.setOnClickListener(v -> {
            databaseReference.child("remetente").setValue(remetenteEditText.getText().toString());
            databaseReference.child("destinatario").setValue(destinatarioEditText.getText().toString());
            databaseReference.child("recado").setValue(recadoEditText.getText().toString());
            finish();
        });
    }

    private void inicializaComponentes() {
        remetenteEditText = (EditText) findViewById(R.id.remetenteEditText);
        destinatarioEditText = (EditText) findViewById(R.id.destinatarioEditText);
        recadoEditText = (EditText) findViewById(R.id.recadoEditText);
        salvarButton = (Button) findViewById(R.id.salvarButton);
    }
}