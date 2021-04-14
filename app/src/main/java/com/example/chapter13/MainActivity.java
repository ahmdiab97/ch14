package com.example.chapter13;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDB;

    EditText productQuantity,productName,productID;

    int x=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bttnAdd=(Button)findViewById(R.id.bttnAdd);
        Button bttnFind=(Button)findViewById(R.id.bttnFind);
        Button bttnDelete=(Button)findViewById(R.id.bttnDelete);

        myDB=new DatabaseHelper(this);



        productQuantity=(EditText)findViewById(R.id.productQuantity);
        productName=(EditText)findViewById(R.id.productName);
        productID=(EditText) findViewById(R.id.productID);



        bttnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!myDB.addData(Integer.parseInt(productID.getText().toString()),productName.getText().toString(),Integer.parseInt(productQuantity.getText().toString())))
                    Toast.makeText(MainActivity.this,"Data was not entered into the table \nPlease check your input!",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"Data was successfully entered into the table",Toast.LENGTH_LONG).show();
            }
        });

        bttnFind.setOnClickListener(new View.OnClickListener() {
            Cursor cur;
            StringBuffer buffer=new StringBuffer();

            @Override
            public void onClick(View v) {
                if (productName.getText().toString().equals("")) {
                    cur = myDB.getListContents();
                } else if (!(productName.getText().toString().equals(""))) {
                    cur = myDB.getSpecificResult(productName.getText().toString());
                }

                if (cur.getCount()==0)
                    Toast.makeText(MainActivity.this, "No results found !", Toast.LENGTH_LONG).show();
                else {

                    while (cur.moveToNext()) {
                        for (int i = 0; i < cur.getColumnCount(); i++) {
                            buffer.append(cur.getColumnName(i) + ": " + cur.getString(i) + "\n");
                        }
                        buffer.append("\n");

                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Results");
                    builder.setMessage(buffer.toString());
                    builder.show();

                    buffer.delete(0, buffer.capacity());

                }
            }
        });

        bttnDelete.setOnClickListener(new View.OnClickListener() {
            //deletes a row specified  by the employee_NAME
            //then display a toast with the count of rows deleted
            //if no rows are found, display a toast saying that nothing was deleted
            @Override
            public void onClick(View v) {

                int result=myDB.dltRow(productName.getText().toString());

                if(result>=1)
                    Toast.makeText(MainActivity.this,+result+"Row(s) were susscessfully deleted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"No rows were deleted \nPlease try again",Toast.LENGTH_LONG).show();


            }
        });
    }
}