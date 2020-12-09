package br.com.vinicius.deixeseurecado;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Objects;
import br.com.vinicius.deixeseurecado.model.Recado;

public class DetalhesActivity extends AppCompatActivity {

    private TextView remetenteTextView;
    private TextView destinatarioTextView;
    private TextView recadoTextView;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        inicializaComponentes();
        String mKey= Objects.requireNonNull(getIntent().getExtras()).getString("key");

        databaseReference = FirebaseDatabase.getInstance().getReference("recado").child(mKey);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Recado recado = dataSnapshot.getValue(Recado.class);

                if (recado.getRemetente() != null) {
                    remetenteTextView.setText(recado.getRemetente());
                }

                if (recado.getDestinatario() != null) {
                    destinatarioTextView.setText(recado.getDestinatario());
                }

                if (recado.getRecado() != null) {
                    recadoTextView.setText(recado.getRecado());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(DetalhesActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void inicializaComponentes(){
        remetenteTextView = (TextView) findViewById(R.id.remetenteTextView);
        destinatarioTextView = (TextView) findViewById(R.id.destinatarioTextView);
        recadoTextView = (TextView) findViewById(R.id.recadoTextView);
    }
}