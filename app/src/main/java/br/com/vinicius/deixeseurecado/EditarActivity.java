package br.com.vinicius.deixeseurecado;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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

public class EditarActivity extends AppCompatActivity {


    private EditText mRemetenteEditText;


    private EditText mDestinatarioEditText;


    private EditText mRecadoEditText;


    private Button mSalvarButton;

    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        inicializaComponentes();
        String mKey= Objects.requireNonNull(getIntent().getExtras()).getString("key");

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("character").child(mKey);

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Recado charater = dataSnapshot.getValue(Recado.class);

                if (charater.getRemetente() != null) {
                    mRemetenteEditText.setText(charater.getRemetente());
                }

                if (charater.getDestinatario()!= null) {
                    mDestinatarioEditText.setText(charater.getDestinatario());
                }

                if (charater.getRecado() != null) {
                    mRecadoEditText.setText(charater.getRecado());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(EditarActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        mSalvarButton.setOnClickListener(v -> {
            mDatabaseReference.child("remetente").setValue(mRemetenteEditText.getText().toString());
            mDatabaseReference.child("destinatario").setValue(mDestinatarioEditText.getText().toString());
            mDatabaseReference.child("recado").setValue(mRecadoEditText.getText().toString());
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