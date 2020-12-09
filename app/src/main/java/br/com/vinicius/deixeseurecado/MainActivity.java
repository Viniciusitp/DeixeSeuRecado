package br.com.vinicius.deixeseurecado;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import br.com.vinicius.deixeseurecado.model.Recado;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton newFloatingActionButton;
    private ImageButton btnSair;
    private FeedAdapter characterAdapter;
    private LinearLayoutManager layoutManager;
    private ArrayList<Recado> listRecado;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializaComponentes();

        listRecado = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("recado");

        newFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, AdicionarActivity.class);
                startActivity(intent);

            }
        });

        Recycler();

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Você saiu!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });
    }

    public void Recycler() {
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        characterAdapter = new FeedAdapter(listRecado);
        recyclerView.setAdapter(characterAdapter);
        Content();
        deleteSwipe();
    }

    private void Content() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listRecado.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Recado recado = postSnapshot.getValue(Recado.class);
                    if (recado != null) {
                        recado.setKey(postSnapshot.getKey());
                    }
                    listRecado.add(recado);
                }
                characterAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteSwipe() {
        ItemTouchHelper.SimpleCallback touchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                databaseReference.child(listRecado.get(viewHolder.getAdapterPosition()).getKey()).setValue(null);
                characterAdapter.deleteItem(viewHolder.getAdapterPosition());
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void inicializaComponentes(){
        recyclerView = (RecyclerView) findViewById(R.id.characterRecyclerView);
        newFloatingActionButton = (FloatingActionButton) findViewById(R.id.newFloatingActionButton);
        btnSair = (ImageButton) findViewById(R.id.btnSair);
    }
}