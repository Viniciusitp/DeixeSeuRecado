package br.com.vinicius.deixeseurecado;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetalhesActivity extends AppCompatActivity {


    private TextView mRemetenteTextView;



    private TextView mDestinatarioTextView;


    private TextView mRecadoTextView;

    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        inicializaComponentes();
        String mKey= Objects.requireNonNull(getIntent().getExtras()).getString("key");

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("character").child(mKey);

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Recado charater = dataSnapshot.getValue(Recado.class);

                if (charater.getRemetente() != null) {
                    mRemetenteTextView.setText(charater.getRemetente());
                }

                if (charater.getDestinatario() != null) {
                    mDestinatarioTextView.setText(charater.getDestinatario());
                }

                if (charater.getRecado() != null) {
                    mRecadoTextView.setText(charater.getRecado());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(DetalhesActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void inicializaComponentes(){
        mRemetenteTextView = (TextView) findViewById(R.id.remetenteTextView);
        mDestinatarioTextView = (TextView) findViewById(R.id.destinatarioTextView);
        mRecadoTextView = (TextView) findViewById(R.id.recadoTextView);


    }
}