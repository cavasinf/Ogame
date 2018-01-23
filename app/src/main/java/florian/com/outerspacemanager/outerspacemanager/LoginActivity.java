package florian.com.outerspacemanager.outerspacemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPseudonyme;
    private EditText editTextPassword;
    private Button btnLogin;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmailID);
        editTextPseudonyme = findViewById(R.id.editTextPseudonymeID);
        editTextPassword = findViewById(R.id.editTextPasswordID);
        btnLogin = findViewById(R.id.buttonConnectID);
        btnRegister = findViewById(R.id.buttonRegisterID);

        //TEMPLATE CLICK BUTTON
        //

        /*btnLogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
        );*/

         btnLogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final User user = new User(editTextPseudonyme.getText().toString(), editTextPassword.getText().toString());

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("https://outer-space-manager.herokuapp.com/api/v1/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        ApiService service = retrofit.create(ApiService.class);

                        Call<LoginUserResponse> request = service.loginUser(user);

                        request.enqueue(new Callback<LoginUserResponse>() {
                            @Override
                            public void onResponse(Call<LoginUserResponse> call, Response<LoginUserResponse> response) {
                                if (response.code() > 199 && response.code() < 301) {
                                    SharedPreferences settings = getSharedPreferences(Constant.PREFS_USER, 0);
                                    SharedPreferences.Editor editor = settings.edit();
                                    editor.putString("userToken",response.body().getToken());
                                    editor.commit();
                                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                                    intent.putExtra(Constant.EXTRA_USER, user);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                } else if (response.code() > 499 && response.code() < 601) {
                                    Toast toast = Toast.makeText(getApplicationContext(), "Erreur Serveur, merci de reessayer plus tard !", Toast.LENGTH_LONG);
                                    toast.show();
                                }
                                  else {
                                    Toast toast = Toast.makeText(getApplicationContext(), "Erreur !", Toast.LENGTH_LONG);
                                    toast.show();
                                }
                            }

                            @Override
                            public void onFailure(Call<LoginUserResponse> call, Throwable t) {
                                Constant.ToastErrorConnection(getApplicationContext());
                            }
                        });
                    }
                }
        );

        btnRegister.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (editTextEmail.getText().toString() != "" &&  editTextPseudonyme.getText().toString() != "" && editTextPassword.getText().toString() != "") {
                        User user = new User(editTextEmail.getText().toString(), editTextPseudonyme.getText().toString(), editTextPassword.getText().toString());

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("https://outer-space-manager.herokuapp.com/api/v1/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        ApiService service = retrofit.create(ApiService.class);

                        Call<CreateUserResponse> request = service.createUser(user);

                        request.enqueue(new Callback<CreateUserResponse>() {
                            @Override
                            public void onResponse(Call<CreateUserResponse> call, Response<CreateUserResponse> response) {
                                if (response.code() > 199 && response.code() < 301) {
                                    Toast toast = Toast.makeText(getApplicationContext(), "Utilisateur enregistré", Toast.LENGTH_LONG);
                                    toast.show();
                                } else {
                                    Toast toast = Toast.makeText(getApplicationContext(), "Erreur !", Toast.LENGTH_LONG);
                                    toast.show();
                                }
                            }

                            @Override
                            public void onFailure(Call<CreateUserResponse> call, Throwable t) {

                            }
                        });

                        }
                        else {
                            if (editTextEmail.getText().toString() != "") {
                                Toast toast = Toast.makeText(getApplicationContext(), "Veuillez entrer un Email !", Toast.LENGTH_LONG);
                                toast.show();
                            }
                            if ( editTextPseudonyme.getText().toString() != "")
                            {
                                Toast toast = Toast.makeText(getApplicationContext(), "Veuillez entrer un Pseudonyme !", Toast.LENGTH_LONG);
                                toast.show();
                            }
                            if (editTextPassword.getText().toString() != "") {
                                Toast toast = Toast.makeText(getApplicationContext(), "Veuillez entrer un Mot de passe !", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }
                    }
                }
        );


    }
}
