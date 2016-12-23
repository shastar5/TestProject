package com.humanplus.test;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    static final int PICK_CONTACT_REQUEST = 1;
    private int PICK_CONTENT, column, index = 0;
    private String digit, name;
    Contacts contacts[] = new Contacts[1000];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        readContact();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void readContact() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                Uri.parse("content://contacts/people"));

        startActivityForResult(intent, PICK_CONTENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_CONTACT_REQUEST) {
            if(resultCode == RESULT_OK) {
                Uri contactUri = data.getData();
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};

                Cursor cursor = getContentResolver().
                        query(contactUri, projection, null, null, null);
                cursor.moveToFirst();

                while(cursor.moveToNext()) {
                    digit = cursor.getString(cursor.getColumnIndexOrThrow(android.provider.Contacts.People.NUMBER));
                    name = cursor.getString(cursor.getColumnIndexOrThrow(android.provider.Contacts.People.NAME));
                    getContracts(digit, name);
                }
            }
        }
    }

    public void getContracts(String digit, String name) {
        contacts[index].Name = name;
        contacts[index++].Digit = digit;
    }

    public Contacts[] returnContracts() {
        return contacts;
    }

}
