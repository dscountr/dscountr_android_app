package dscountr.app.co.ke.dscountr_android_app.view.registration.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import dscountr.app.co.ke.dscountr_android_app.R;
import dscountr.app.co.ke.dscountr_android_app.view.utils.CodeEntryEditText;

public class VerifyEmailFragment extends Fragment implements Toolbar.OnMenuItemClickListener, Button.OnClickListener{

    public static String TAG = VerifyEmailFragment.class.getSimpleName();
    String phone_number = null, phone_number_verification_code = null, email = null;
    CodeEntryEditText enterEmailVerification;
    //firebase auth object
    private FirebaseAuth mAuth;
    private ActionCodeSettings actionCodeSettings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View verify_email = inflater.inflate(R.layout.fragment_verify_email, container, false);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(this);
        Button btnEmailVerificaton = verify_email.findViewById(R.id.btnEmailVerificaton);
        btnEmailVerificaton.setOnClickListener(this);

        LinearLayout llVerifyBack = verify_email.findViewById(R.id.llVerifyBack);
        TextView tvResendVerificationCode = verify_email.findViewById(R.id.tvResendVerificationCode);
        tvResendVerificationCode.setOnClickListener(this);
        llVerifyBack.setOnClickListener(this);

        //initializing objects
        mAuth = FirebaseAuth.getInstance();
        buildActionCodeSettings();

        Bundle args = this.getArguments();
        if(args != null){
            phone_number = args.getString("phone_number");
            phone_number_verification_code = args.getString("phone_number_verification_code");
            email = args.getString("email");
            sendVerificationCode(email);
        }

        enterEmailVerification = verify_email.findViewById(R.id.enterEmailVerification);

        return verify_email;
    }

    private void loadFragment(Fragment fragment) {
        // create a FragmentManager
        FragmentManager fragmentManager = getFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit(); // save the changes
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miRegistrationHelp:
                Toast.makeText(getActivity(), "Email verification code help.",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEmailVerificaton:
                verifyEmailCode();
                break;
            case R.id.llVerifyBack:
                Toast.makeText(getActivity(), "You clicked the back button.",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tvResendVerificationCode:
                Toast.makeText(getActivity(), "Resend the email verification code.",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;

        }
    }

    private void verifyEmailCode(){
        String email_verification_code = enterEmailVerification.getText().toString();
        if (TextUtils.isEmpty(email_verification_code)){
            Toast.makeText(getActivity(), "Please enter email verification code.",Toast.LENGTH_SHORT).show();
        }else if (email_verification_code.length() < 6){
            Toast.makeText(getActivity(), "Email verification code isn't complete.",Toast.LENGTH_SHORT).show();
        }else{
            Bundle bundle = new Bundle();
            bundle.putString("phone_number", phone_number);
            bundle.putString("phone_number_verification_code", phone_number_verification_code);
            bundle.putString("email", email);
            bundle.putString("email_verification_code", enterEmailVerification.getText().toString());
            Fragment verifyEmail = new DateOfBirthFragment();
            verifyEmail.setArguments(bundle);
            loadFragment(verifyEmail);
            // verifySignInLink();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void buildActionCodeSettings() {
        // [START auth_build_action_code_settings]
        actionCodeSettings = ActionCodeSettings.newBuilder()
                        // URL you want to redirect back to. The domain (www.example.com) for this
                        // URL must be whitelisted in the Firebase Console.
                        .setUrl("https://dscountr-app.co.ke/finishSignUp?cartId=1234")
                        // This must be true
                        .setHandleCodeInApp(true)
                        .setIOSBundleId("dscountr.app.co.ke.ios")
                        .setAndroidPackageName(
                                "dscountr.app.co.ke.dscountr_android_app",
                                true, /* installIfNotAvailable */
                                "16"    /* minimumVersion */)
                        .build();
        // [END auth_build_action_code_settings]
    }

    private void sendVerificationCode(String email) {
        mAuth.sendSignInLinkToEmail(email, actionCodeSettings)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            enterEmailVerification.setText(phone_number_verification_code);
                            Toast.makeText(getActivity(), "Email sent successful.",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getActivity(), "Failed to sent email link.",Toast.LENGTH_SHORT).show();
                        }
                        enterEmailVerification.setText(phone_number_verification_code);
                    }
                });
    }

    private void verifySignInLink() {
        // [START auth_verify_sign_in_link]
        FirebaseAuth auth = FirebaseAuth.getInstance();
        Intent intent = getActivity().getIntent();
        String emailLink = intent.getData().toString();

        // Confirm the link is a sign-in with email link.
        if (auth.isSignInWithEmailLink(emailLink)) {
            // Retrieve this from wherever you stored it

            // The client SDK will parse the code from the link for you.
            auth.signInWithEmailLink(email, emailLink)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.e(TAG, "Successfully signed in with email link!");
                                AuthResult result = task.getResult();
                                // You can access the new user via result.getUser()
                                // Additional user info profile *not* available via:
                                // result.getAdditionalUserInfo().getProfile() == null
                                // You can check if the user is new or existing:
                                // result.getAdditionalUserInfo().isNewUser()

                                Bundle bundle = new Bundle();
                                bundle.putString("phone_number", phone_number);
                                bundle.putString("phone_number_verification_code", phone_number_verification_code);
                                bundle.putString("email", email);
                                bundle.putString("email_verification_code", enterEmailVerification.getText().toString());
                                Fragment verifyEmail = new DateOfBirthFragment();
                                verifyEmail.setArguments(bundle);
                                loadFragment(verifyEmail);

                            } else {
                                Log.e(TAG, "Error signing in with email link", task.getException());
                            }
                        }
                    });
        }
        // [END auth_verify_sign_in_link]
    }


}