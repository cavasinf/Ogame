package florian.com.outerspacemanager.outerspacemanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

        btnRegister.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        User user = new User(editTextEmail.getText().toString(),editTextPseudonyme.getText().toString(),editTextPassword.getText().toString());

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("https://outer-space-manager.herokuapp.com/api/v1/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        ApiService service = retrofit.create(ApiService.class);

                        Call<CreateUserResponse> request = service.createUser(user);

                        request.enqueue(new Callback<CreateUserResponse>() {
                            @Override
                            public void onResponse(Call<CreateUserResponse> call, Response<CreateUserResponse> response) {
                                if (response.body() != Null) {
                                    Toast toast = Toast.makeText(getApplicationContext(), "Utilisateur enregistré", Toast.LENGTH_LONG);
                                    toast.show();
                                }
                                else {
                                    Toast toast = Toast.makeText(getApplicationContext(), "Erreur !", Toast.LENGTH_LONG);
                                    toast.show();
                                }
                            }

                            @Override
                            public void onFailure(Call<CreateUserResponse> call, Throwable t) {

                            }
                        });

                    }
                }
        );


    }
}
