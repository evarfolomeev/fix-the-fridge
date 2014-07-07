package dev.battle.swissquote.com.ideas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import dev.battle.swissquote.com.ideas.domain.User;
import dev.battle.swissquote.com.ideas.events.user.LoginSuccessfulEvent;
import dev.battle.swissquote.com.ideas.events.user.SignupSuccessfulEvent;
import dev.battle.swissquote.com.ideas.services.UserService;

/**
 * Created by dsaklenko on 7/3/14.
 */
public class RegistrationActivity extends AbstractActivity {

    @InjectView(R.id.nickNameEditText)
    public EditText nickName;

    @InjectView(R.id.emailEditText)
    public EditText emailEditText;

    @InjectView(R.id.passwordEditText)
    public EditText passwordEditText;

    @InjectView(R.id.confirmPasswordEditText)
    public EditText confirmPasswordEditText;

    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().setTitle(R.string.create_account);
        userService = container.getComponent(UserService.class);
        ButterKnife.inject(this);
    }

    public void createAccount(View view) {
        boolean isValid = true;
        String nickNameTxt = nickName.getText().toString();
        String emailTxt = emailEditText.getText().toString();
        String passwordTxt = passwordEditText.getText().toString();
        String confPasswordTxt = confirmPasswordEditText.getText().toString();

        if (nickNameTxt == null || nickNameTxt.length() == 0) {
            nickName.setError("Nickname is required");
            isValid = false;
        } else {
            nickName.setError(null);
        }
        if (emailTxt == null || emailTxt.length() == 0) {
            emailEditText.setError("Email is required");
            isValid = false;
        } else {
            emailEditText.setError(null);
        }

        if (passwordTxt == null || passwordTxt.length() == 0 || !passwordTxt.equals(confPasswordTxt)) {
            passwordEditText.setError("Password and confirm Password must match");
            isValid = false;
        } else {
            passwordEditText.setError(null);
        }
        if (isValid) {
            User user = new User();
            user.email = emailTxt;
            user.avatarName = nickNameTxt;

            userService.signUp(emailTxt, nickNameTxt, passwordTxt);
        }
    }

    public void onEventMainThread(SignupSuccessfulEvent event) {
        Toast.makeText(getApplicationContext(), "Sign up Successful", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
    }
}
