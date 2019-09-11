package com.julio.carwashtogo.common;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class UploadFotos {
    //obtiene la extension de la foto
    public static String getFileExtension(Uri uri, FragmentActivity activity) {
        ContentResolver cR = activity.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    public static void fillSpinner(Context context, String[] values, Spinner spinner){
        ArrayAdapter<String>adapter = new ArrayAdapter<>(context,android.R.layout.simple_list_item_1,values);
        spinner.setAdapter(adapter);
    }


    public static Uri subirFoto(FragmentActivity activity,StorageReference storageReference,String nombreCarpeta, String key, Uri uri){
        final Uri[] url = {null};
        final StorageReference storage = storageReference.child(nombreCarpeta+key+"."+getFileExtension(uri,activity));
        storage.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        url[0] = uri;
                    }
                });
            }
        });
        return url[0];
    }
}
