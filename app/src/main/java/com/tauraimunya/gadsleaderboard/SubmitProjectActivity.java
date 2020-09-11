package com.tauraimunya.gadsleaderboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tauraimunya.gadsleaderboard.UTILS.ConfirmationDialog;
import com.tauraimunya.gadsleaderboard.UTILS.TextValidator;
import com.tauraimunya.gadsleaderboard.UTILS.WarningDialog;
import com.tauraimunya.gadsleaderboard.model.Learner;
import com.tauraimunya.gadsleaderboard.model.SubmitProjectModel;
import com.tauraimunya.gadsleaderboard.services.LeanerService;

import java.util.List;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class SubmitProjectActivity extends AppCompatActivity {

    private static final String TAG = SubmitProjectActivity.class.getSimpleName();


    private EditText mEdFirstName;
    private EditText mEdLastName;
    private EditText mEdEmail;
    private EditText mEdProjectLink;
    private String submissionUrl = "https://docs.google.com/forms/d/e/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_project);

        Button backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(view -> onBackPressed());

        Button submitButton = findViewById(R.id.btn_submit);
        submitButton.setOnClickListener(view -> actionSubmit());

        mEdFirstName = findViewById(R.id.editTextFirstName);
        mEdLastName = findViewById(R.id.editTextLastName);
        mEdEmail = findViewById(R.id.editTextEmail);
        mEdProjectLink = findViewById(R.id.editTextProjectLink);

        mEdLastName.addTextChangedListener(new TextValidator(mEdFirstName) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.isEmpty()) {
                    Toast.makeText(SubmitProjectActivity.this, "Last name required", Toast.LENGTH_LONG).show();
                }
            }
        });

        mEdEmail.addTextChangedListener(new TextValidator(mEdFirstName) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.isEmpty()) {
                    Toast.makeText(SubmitProjectActivity.this, "Email required", Toast.LENGTH_LONG).show();
                }
            }
        });

        mEdProjectLink.addTextChangedListener(new TextValidator(mEdFirstName) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.isEmpty()) {
                    Toast.makeText(SubmitProjectActivity.this, "Your project link in Github required", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void actionSubmit() {

        String lastName = mEdLastName.getText().toString();
        String firstName = mEdFirstName.getText().toString();
        String email = mEdEmail.getText().toString();
        String projectLink = mEdProjectLink.getText().toString();

        SubmitProjectModel projectModel = new SubmitProjectModel();
        projectModel.setLastName(lastName);
        projectModel.setFirstName(firstName);
        projectModel.setBusinessEmail(email);
        projectModel.setProjectLink(projectLink);

        if (projectModel.getFirstName().isEmpty()){
            Toast.makeText(SubmitProjectActivity.this, "First name required", Toast.LENGTH_LONG).show();
            return;
        }
        if (projectModel.getLastName().isEmpty()){
            Toast.makeText(SubmitProjectActivity.this, "Last name required", Toast.LENGTH_LONG).show();
            return;
        }
        if (projectModel.getBusinessEmail().isEmpty()){
            Toast.makeText(SubmitProjectActivity.this, "Email required", Toast.LENGTH_LONG).show();
            return;
        }else if (!isValidEmail(projectModel.getBusinessEmail())){
            Toast.makeText(SubmitProjectActivity.this, "Email address no valid", Toast.LENGTH_LONG).show();
            return;
        }
        if (projectModel.getProjectLink().isEmpty()){
            Toast.makeText(SubmitProjectActivity.this, "Project link required", Toast.LENGTH_LONG).show();
            return;
        }


        //Log.d(TAG, projectModel.toString());

        //THE SUBMIT PART STARTING ......
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(submissionUrl)
                .addConverterFactory(GsonConverterFactory.create())

                .client(client);
        Retrofit retrofit = builder.build();
        LeanerService learnerApiService = retrofit.create(LeanerService.class);



        //Call<projectModel> call = learnerApiService.submitProject(email,firstName, lastName, projectLink);
        Call<ResponseBody> call = learnerApiService.submitProject(projectModel.getFirstName(), projectModel.getLastName(),projectModel.getBusinessEmail(), projectModel.getProjectLink());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                //Submit Confirmation
                ConfirmationDialog dialog = new ConfirmationDialog(getSupportFragmentManager());
                Bundle args = new Bundle();
                args.putParcelable(getString(R.string.field_project), projectModel);
                dialog.setArguments(args);
                dialog.show(getSupportFragmentManager(), getString(R.string.dialog_confirm_submit));

                mEdLastName.setText("");
                mEdFirstName.setText("");
                mEdEmail.setText("");
                mEdProjectLink.setText("");
                //Toast.makeText(SubmitProjectActivity.this, "Submited successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                WarningDialog warningDialog = new WarningDialog();
                Bundle args = new Bundle();
                args.putParcelable(getString(R.string.field_project), projectModel);
                warningDialog.setArguments(args);
                warningDialog.show(getSupportFragmentManager(),getString(R.string.dialog_warning_submit));

               // Toast.makeText(SubmitProjectActivity.this, "Submission failed", Toast.LENGTH_SHORT).show();

            }
        });




    }

    private boolean isValidEmail(String email) {
        Pattern validator = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        return validator.matcher(email).find();
    }


}