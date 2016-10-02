package com.libs.golomb.extendedrecyclerview.example;

import android.app.Dialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.libs.golomb.extendedrecyclerview.ExtendedRecycleAdapter;
import com.libs.golomb.extendedrecyclerview.ExtendedRecycleView;
import com.libs.golomb.extendedrecyclerview.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ExtendedRecycleAdapter.OnClickListener<String> {


    private StringDataExtractor dataExtractor;
    private ExtendedRecycleAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataExtractor = new StringDataExtractor(getStringList(), true, true);

        ExtendedRecycleView recyclerView = (ExtendedRecycleView) findViewById(R.id.extended_recycle_view);
        StringViewHolderGenerator generator = new StringViewHolderGenerator(this);
        adapter = new ExtendedRecycleAdapter<>(dataExtractor, generator, this);
        recyclerView.initializeDefault(this, LinearLayoutManager.VERTICAL, adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Click","Click");
                // floating button was pressed.
                createDialog();

            }
        });
    }

    private void createDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.setTitle("Add Dialog");

        Button btn = (Button) dialog.findViewById(R.id.dialog_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) dialog.findViewById(R.id.editText);
                dataExtractor.addItem(editText.getText().toString());
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public List<String> getStringList() {
        List<String> list = new ArrayList<>();
        for(char i = 'a'; i <= 'z'; i ++){
            list.add( i + "Item");
        }
        return list;
    }

    @Override
    public void onClick(View view, String item) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
