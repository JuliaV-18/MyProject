package com.lian.myproject.model;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.lian.myproject.R;

public class ListUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public class GoToContacts extends  AppCompatActivity {
        private static final int ResultPickContact = 1;
        protected TextView ContactName;
        protected TextView ContactNumber;
        protected Button PickContact;

    }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_list_user);

            ContactName = (TextView) findViewById(R.id.ContactName);
            ContactName = (TextView) findViewById(R.id.ContactNumber);
            ContactName = (Button) findViewById(R.id.PickContact);

            EvanrHandler ();
        }
}

private void EvanrHandler() {
    PickContact.setOnClickListener(new View.OnClickListener()) {
        @Override
                public void onClick(View v){
            Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            startActivityForResult(i, GoToContacts.ResultPickContact);
        }
    });
}


@Override
protected void onActivityResult (int requestCode, int resultCode, Intent data){
    if(resultCode == RESULT_OK && requestCode == 1){
        Uri uri = data.getData();

        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();

        int phoneIndex=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        int nameIndex=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);

        String phoneN = cursor.getString(phoneIndex);
        String name = cursor.getString(nameIndex);

        ContactName.SetTExt(name);
        ContactNumber.setText(phoneN);
    }
}
