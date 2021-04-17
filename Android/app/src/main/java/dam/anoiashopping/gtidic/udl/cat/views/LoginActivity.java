package dam.anoiashopping.gtidic.udl.cat.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import dam.anoiashopping.gtidic.udl.cat.R;
import dam.anoiashopping.gtidic.udl.cat.databinding.ActivityLoginBinding;
import dam.anoiashopping.gtidic.udl.cat.preferences.PreferencesProvider;
import dam.anoiashopping.gtidic.udl.cat.viewmodels.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = "LoginActivity";
    public LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        PreferencesProvider.init(this);

        if (!PreferencesProvider.providePreferences().getString("token", "").equals("")) {

            Log.d (TAG, "L'usuari ja té token: " + PreferencesProvider.providePreferences().getString("token", ""));
            startActivity (new Intent (LoginActivity.this, MainActivity.class));

        } else {
            Log.d (TAG, "L'usuari no té token.");
        }

        setTheme(R.style.Theme_Android);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView () {

        loginViewModel = new ViewModelProvider (this).get(LoginViewModel.class);
        ActivityLoginBinding activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        activityLoginBinding.setLifecycleOwner (this);
        activityLoginBinding.setViewModel (loginViewModel);

        loginViewModel.registerClick.observe(this, s -> {
            startActivity (new Intent (LoginActivity.this, RegisterActivity.class));
        });

        loginViewModel.getLoginResponse().observe(this, s -> {

            if (s.isValid()) {

                Log.d (TAG, "Login correcte");
                Toast.makeText(getApplicationContext(), R.string.OkLogIn, Toast.LENGTH_SHORT).show();
                startActivity (new Intent (LoginActivity.this, MainActivity.class));

            } else {

                Log.d (TAG, "Login incorrecte");
                Toast.makeText(getApplicationContext(), R.string.FailLogIn, Toast.LENGTH_SHORT).show();
            }
        });
    }
}