package dev.battle.swissquote.com.ideas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import dev.battle.swissquote.com.ideas.domain.Idea;
import dev.battle.swissquote.com.ideas.events.user.SignupSuccessfulEvent;
import dev.battle.swissquote.com.ideas.exceptions.NotLoggedInException;
import dev.battle.swissquote.com.ideas.services.IdeaService;

/**
 * Created by dsaklenko on 7/3/14.
 */
public class PostIdeaActivity extends AbstractActivity implements View.OnClickListener {

    @InjectView(R.id.ideaTitleContent)
    public EditText title;

    @InjectView(R.id.ideaDescriptionContent)
    public EditText description;

    @InjectView(R.id.postIdeaButton)
    public Button postButton;

    @InjectView(R.id.attachImageView)
    public ImageView attachImageView;

    private IdeaService ideaService;

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_idea);
        getSupportActionBar().setTitle(R.string.post_idea);
        ideaService = container.getComponent(IdeaService.class);
        ButterKnife.inject(this);

        postButton.setOnClickListener(this);
    }


    public void onEventMainThread(SignupSuccessfulEvent event) {
        Toast.makeText(getApplicationContext(), "Sign up Successful", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
        super.onBackPressed();
    }

    public void attachPicture(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            attachImageView.setImageBitmap(imageBitmap);
        }
    }


    public void onClick(View view) {
        String titleTxt = title.getText().toString();
        String descriptionTxt = description.getText().toString();
        //TODO for Attachement
        Idea idea = new Idea();
        idea.title = titleTxt;
        idea.description = descriptionTxt;

        if (titleTxt == null || titleTxt.length() == 0) {
            Toast.makeText(getApplicationContext(), "A title is mandatory", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            ideaService.sendIdea(idea);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        } catch (NotLoggedInException ex) {
            Toast.makeText(getApplicationContext(), "You need to be logged in to post", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
    }
}
