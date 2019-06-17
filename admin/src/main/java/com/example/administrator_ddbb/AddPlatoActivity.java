package com.example.administrator_ddbb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator_ddbb.Interfaces.Plato;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;

public class AddPlatoActivity extends AppCompatActivity {

    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Intent intent;
    Plato plato;
    ArrayList<String> opciones;
    String seleccion, nombrfoto;
    ImageView preview;
    TextView  Ingrediente;
    EditText Nombre, Precio;
    Button enviar, cancelar;
    Spinner select;
    String URIfoto;
    private StorageReference filePath;
    private StorageReference myFirebaseStorage;

    private Button btnChoose, btnUpload;

    private Uri imageUri;

    private final int PICK_IMAGE_REQUEST = 71;
    private static final int GALLERY_INTENT = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plato);
        select = findViewById(R.id.spinner);


        //EditView
        Nombre = findViewById(R.id.anadir_entrante_nombre);
        Ingrediente = findViewById(R.id.anadir_entrante_ingrediente);
        Precio = findViewById(R.id.anadir_entrante_precio);
        cancelar = findViewById(R.id.cancelar);
        preview = findViewById(R.id.preview);
        //TextView
        enviar = findViewById(R.id.enviar_plato);
        preview.setImageResource(R.drawable.logo);

        //Firebase
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        opciones = new ArrayList<String>();

        opciones.add("Entrante");
        opciones.add("Postres");
        opciones.add("Carnes");
        opciones.add("Pescado");
        opciones.add("Bebidas");

        ArrayAdapter adp = new ArrayAdapter(AddPlatoActivity.this, R.layout.support_simple_spinner_dropdown_item, opciones);
        select.setAdapter(adp);

        select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String elemento = (String) select.getAdapter().getItem(position);
                seleccion = elemento;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
       /** preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cogerFoto();
            }
        });
**/
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearPlato();
                enlacePantallaGenerico(MainActivity.class);
            }
        });

    }

    public void comprobarDatos() {

        if (Nombre == null || Precio == null || Ingrediente == null ) {
            Toast.makeText(AddPlatoActivity.this, R.string.error_anadir, Toast.LENGTH_LONG).show();
        } else {
            db.collection(seleccion)
                    .add(plato);
        }
    }

    public void crearPlato() {

        plato = new Plato(Nombre.getText().toString().trim(),
                Precio.getText().toString().trim(),
                Ingrediente.getText().toString().trim(),
                ""
        );

        comprobarDatos();

    }

    private void enlacePantallaGenerico(Class destino) {
        Intent intent = new Intent(AddPlatoActivity.this, destino);
        startActivity(intent);
        finish();
    }

   /** private void cogerFoto() {
        intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,GALLERY_INTENT);
    }
    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(AddPlatoActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            crearPlato();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddPlatoActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {

            imageUri = data.getData();
            preview.setImageURI(imageUri);

            // Creamos una carpeta en el storage (hijo) y guardamos el uri EL CUAL ES LA FOTO
            filePath = myFirebaseStorage.child("FotosDeProductos").child(Nombre + "_FOTO");

            //Subimos la foto


            filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Toast.makeText(AddPlatoActivity.this, "Se subió exitosamente la fotografía.", Toast.LENGTH_SHORT).show();
                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            nombrfoto = uri.toString();
                        }
                    });
                }
            });
            filePath = myFirebaseStorage.child("images");
            filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(AddPlatoActivity.this, "Se subió exitosamente la fotografía.", Toast.LENGTH_SHORT).show();
                }
            });


            InputStream imageStream = null;
            try {
                imageStream = getContentResolver().openInputStream(imageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            URIfoto = encodeImageBase64(selectedImage);
        }
    }
    private String encodeImageBase64(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.NO_WRAP);

        return encImage;
    }**/
}
