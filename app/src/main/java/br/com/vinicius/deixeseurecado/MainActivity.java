package br.com.vinicius.deixeseurecado;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import butterknife.BindView;
import butterknife.ButterKnife;

public class
MainActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.characterRecyclerView)
    RecyclerView mRecyclerView;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.newFloatingActionButton)
    FloatingActionButton mNewFloatingActionButton;



    private Button btnSair;


    FeedAdapter mCharacterAdapter;

    LinearLayoutManager mLayoutManager;

    private ArrayList<Recado> mListRecado;

    private DatabaseReference mDatabaseReference;

    private FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSair = findViewById(R.id.btnSair);
        ButterKnife.bind(this);

        mListRecado = new ArrayList<>();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("character");

        mNewFloatingActionButton.setOnClickListener(v -> {
            Intent intent=new Intent(MainActivity.this, AdicionarActivity.class);
            startActivity(intent);
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

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mCharacterAdapter = new FeedAdapter(mListRecado);
        mRecyclerView.setAdapter(mCharacterAdapter);
        Content();
        deleteSwipe();
        
    }



    private void Content() {

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mListRecado.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Recado recado = postSnapshot.getValue(Recado.class);
                    if (recado != null) {
                        recado.setKey(postSnapshot.getKey());
                    }
                    mListRecado.add(recado);
                }
                mCharacterAdapter.notifyDataSetChanged();
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
                mDatabaseReference.child(mListRecado.get(viewHolder.getAdapterPosition()).getKey()).setValue(null);
                mCharacterAdapter.deleteItem(viewHolder.getAdapterPosition());
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelperCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }


}