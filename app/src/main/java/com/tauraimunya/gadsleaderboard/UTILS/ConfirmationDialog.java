package com.tauraimunya.gadsleaderboard.UTILS;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.tauraimunya.gadsleaderboard.R;
import com.tauraimunya.gadsleaderboard.model.SubmitProjectModel;
import com.tauraimunya.gadsleaderboard.services.LeanerService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfirmationDialog extends DialogFragment {

    private static final String TAG = "ConfirmationDialog";
    private static final String BASE_URL = "https://docs.google.com/forms/d/e/";


    //widgets
    private Button mConfirm;
    private SubmitProjectModel mProject;
    private ImageView mClose;
    private FragmentManager mFragmentManager;

    public ConfirmationDialog(FragmentManager supportFragmentManager) {
        mFragmentManager = supportFragmentManager;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: started");
        mProject = getArguments().getParcelable(getString(R.string.field_project));
        if (mProject != null) {
            Log.d(TAG, "onCreate: submit bean: " + mProject);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_confirmation, container, false);
        mConfirm = view.findViewById(R.id.btn_confirm);
        mClose = view.findViewById(R.id.imageView2);

        mClose.setOnClickListener(view1 -> dismiss());

        mConfirm.setOnClickListener(v -> {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            LeanerService service = retrofit.create(LeanerService.class);
            Log.d(TAG, "onCreateView: " + mProject.toString());
            //Todo:: for test
            mProject = new SubmitProjectModel();
            Call<ResponseBody> call = service.submitProject(mProject.getFirstName(), mProject.getLastName(), mProject.getBusinessEmail(), mProject.getProjectLink());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.d(TAG, "onResponse: response: " + response.message());
                    SuccessDialog dialog = new SuccessDialog();
                    dialog.show(mFragmentManager, getString(R.string.dialog_success));
                    dismiss();
                }


                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d(TAG, "onFailure: error message: " + t.getMessage());
                    WarningDialog dialog = new WarningDialog();
                    try {
                        dialog.show(mFragmentManager, getString(R.string.dialog_warning_submit));
                        dismiss();
                    } catch (IllegalStateException e) {
                        Log.d(TAG, "onFailure: error ConfirmationDialog not attached to a context");
                    }


                }
            });
        });

        return view;
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    /**
     * Return true if the @param is null
     *
     * @param string
     * @return
     */
    private boolean isEmpty(String string) {
        return string.equals("");
    }
}

