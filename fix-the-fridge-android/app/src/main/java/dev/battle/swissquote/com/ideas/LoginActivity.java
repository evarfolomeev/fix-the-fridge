package dev.battle.swissquote.com.ideas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import butterknife.ButterKnife;
import butterknife.InjectView;
import dev.battle.swissquote.com.ideas.events.user.LoginSuccessfulEvent;
import dev.battle.swissquote.com.ideas.services.PersistanceService;
import dev.battle.swissquote.com.ideas.services.UserService;

/**
 * Created by ikuznietsov on 7/3/14.
 */
public class LoginActivity extends AbstractActivity implements View.OnClickListener {

    @InjectView(R.id.buttonOk)
	Button btnOk;

    @InjectView(R.id.buttonOpenAnAccount)
	Button btnOpenAnAccount;

    @InjectView(R.id.textPassw)
    TextView password;

    @InjectView(R.id.textLogin)
    TextView login;

    private UserService userService;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		getSupportActionBar().setTitle(R.string.login_title);

        ButterKnife.inject(this);

		btnOk.setOnClickListener(this);
		btnOpenAnAccount.setOnClickListener(this);

        userService = container.getComponent(UserService.class);
    }


	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.buttonOk:
                String loginTxt = login.getText().toString();
                String passwordTxt = password.getText().toString();
                if(loginTxt == null || passwordTxt == null || loginTxt.length() == 0 || passwordTxt.length() == 0){
                    Toast.makeText(getApplicationContext(), "Username and password are mandatory", Toast.LENGTH_LONG).show();
                    return;
                }
				userService.login(loginTxt, passwordTxt);
				break;
            case R.id.buttonOpenAnAccount:
                Intent intent = new Intent(this, RegistrationActivity.class);
                startActivity(intent);
		}
	}
    public void onEventMainThread(LoginSuccessfulEvent event) {
        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
        super.onBackPressed();
    }

}
