package com.dariogabriellsilva.sqliteapp;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
//import android.view.Menu;
//import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper meuBD;
    EditText editNome, editSobrenome, editMarks, editTextId;
    Button btnAddData, btnVisualizarTodos, btnDelete, btnViewUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        meuBD = new DatabaseHelper(this);

        editNome = findViewById(R.id.editText_name);
        editSobrenome = findViewById(R.id.editText_surname);
        editMarks = findViewById(R.id.editText_Marks);
        editTextId = findViewById(R.id.editText_id);
        btnAddData = findViewById(R.id.button_add);
        btnVisualizarTodos = findViewById(R.id.button_viewAll);
        btnViewUpdate = findViewById(R.id.button_update);
        btnDelete = findViewById(R.id.button_delete);

        AddData();
        visualizarTodos();
        UpdateData();
        DeleteData();
    }

    public void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = meuBD.insertData(editNome.getText().toString(),
                                editSobrenome.getText().toString(),
                                editMarks.getText().toString());
                        if (isInserted)
                            Toast.makeText(MainActivity.this, "Informação guardada.", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Informação não foi guardada.", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void visualizarTodos() {
        btnVisualizarTodos.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = meuBD.getAllData();
                        if (res.getCount() == 0) {
                            // show message
                            showMessage("Error", "Nothing found");
                            return;
                        }

                        StringBuilder buffer = new StringBuilder();
                        while (res.moveToNext()) {
                            buffer.append("Id :").append(res.getString(0)).append("\n");
                            buffer.append("Name :").append(res.getString(1)).append("\n");
                            buffer.append("Surname :").append(res.getString(2)).append("\n");
                            buffer.append("Marks :").append(res.getString(3)).append("\n\n");
                        }

                        // Show all data
                        showMessage("Data", buffer.toString());
                    }
                }
        );
    }

    public void UpdateData() {
        btnViewUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = meuBD.updateData(editTextId.getText().toString(),
                                editNome.getText().toString(),
                                editSobrenome.getText().toString(), editMarks.getText().toString());
                        if (isUpdate)
                            Toast.makeText(MainActivity.this, "Data Update", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void DeleteData() {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = meuBD.deleteData(editTextId.getText().toString());
                        if (deletedRows > 0)
                            Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data not Deleted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}