package dam.anoiashopping.gtidic.udl.cat.viewmodels;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.squareup.picasso.Picasso;

import java.io.File;

import dam.anoiashopping.gtidic.udl.cat.R;
import dam.anoiashopping.gtidic.udl.cat.models.Account;
import dam.anoiashopping.gtidic.udl.cat.preferences.PreferencesProvider;
import dam.anoiashopping.gtidic.udl.cat.repositories.AccountRepo;
import dam.anoiashopping.gtidic.udl.cat.utils.ResultImpl;

public class ConfigurationViewModel extends ViewModel {

    private AccountRepo accountRepo;
    private final String TAG = "ConfigurationVM";

    public MutableLiveData <String> username  = new MutableLiveData <> ();
    public MutableLiveData <String> firstName = new MutableLiveData <> ();
    public MutableLiveData <String> lastName  = new MutableLiveData <> ();
    public MutableLiveData <String> email     = new MutableLiveData <> ();
    public MutableLiveData <String> photoURL  = new MutableLiveData <> ();

    public MutableLiveData <ResultImpl> getAccountResponse () {
        return this.accountRepo.getmResponseGetAccount();
    }

    public ConfigurationViewModel () {
        this.accountRepo = new AccountRepo();
    }

    public void getAccount () {

        this.accountRepo.getAccount(PreferencesProvider.providePreferences().getString("token", ""));

    }

    public void setAccount () {

        Account account = this.accountRepo.getAccount();

        username.setValue  (account.getUsername());
        firstName.setValue (account.getFirstname());
        lastName.setValue  (account.getLastname());
        email.setValue     (account.getEmail());
        photoURL.setValue  (account.getPhotoURL());

        Log.d(TAG, photoURL.getValue());
    }

    public void uploadAccountImage(File imageFile){
        Log.d("VM", "uploading image... using repo");
        this.accountRepo.uploadImage(PreferencesProvider.providePreferences().getString("token", ""), imageFile);
    }
}
