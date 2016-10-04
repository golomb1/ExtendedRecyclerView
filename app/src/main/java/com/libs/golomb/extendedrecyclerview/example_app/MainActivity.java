package com.libs.golomb.extendedrecyclerview.example_app;

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
import com.libs.golomb.extendedrecyclerview.example.SampleData;
import com.libs.golomb.extendedrecyclerview.example.StringDataExtractor;
import com.libs.golomb.extendedrecyclerview.example.StringViewHolderGenerator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ExtendedRecycleAdapter.OnClickListener<SampleData> {


    private StringDataExtractor dataExtractor;
    private ExtendedRecycleAdapter<SampleData> adapter;

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

    public List<SampleData> getStringList() {
        List<SampleData> list = new ArrayList<>();
        for(char i = 'a'; i <= 'z'; i ++){
            list.add(new SampleData(i + "Item"));
            list.add(new SampleData(i + "Item"));
            list.add(new SampleData(i + "Item"));
        }
        return list;
    }

    @Override
    public void onClick(View view, SampleData item) {
        Snackbar.make(view, item.toString(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
