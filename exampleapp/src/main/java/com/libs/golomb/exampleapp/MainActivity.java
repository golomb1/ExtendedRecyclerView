package com.libs.golomb.exampleapp;

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
import android.widget.TextView;

import com.libs.golomb.exampleapp.viewholder.StringViewHolder;
import com.libs.golomb.extendedrecyclerview.ExtendedRecycleAdapter;
import com.libs.golomb.extendedrecyclerview.ExtendedRecycleView;
import com.libs.golomb.extendedrecyclerview.ViewHolderGenerator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ExtendedRecycleAdapter.OnClickListener<SampleData>, ExtendedRecycleAdapter.SelectionListener<SampleData> {


    private StringDataExtractor dataExtractor;
    private ExtendedRecycleAdapter<SampleData> adapter;
    private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mText = (TextView) findViewById(R.id.text);
        dataExtractor = new StringDataExtractor(getStringList(), true, true);

        ExtendedRecycleView recyclerView = (ExtendedRecycleView) findViewById(R.id.extended_recycle_view);
        recyclerView.setDefaultLayoutManager();

        StringViewHolderGenerator generator = new StringViewHolderGenerator(this,recyclerView);
        adapter = new ExtendedRecycleAdapter<>(dataExtractor, generator, this);
        adapter.enableMultipleChoice(this);
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
            list.add(new SampleData(i + "Item1"));
            list.add(new SampleData(i + "Item2"));
            list.add(new SampleData(i + "Item3"));
        }
        return list;
    }

    @Override
    public void onClick(View view, SampleData item) {
        Snackbar.make(view, item.toString(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void select(View view, SampleData item) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mText.setText(String.valueOf(adapter.getChosenElements().size()));
            }
        });
    }

    @Override
    public void undoSelection(View view, SampleData item) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mText.setText(String.valueOf(adapter.getChosenElements().size()));
            }
        });
    }

    @Override
    public void enterSelectionMode() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mText.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (adapter.isInActiveSelectionMode()) {
            adapter.exitSelectionMode();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mText.setVisibility(View.GONE);
                }
            });

        } else {
            super.onBackPressed();
        }
    }

}
