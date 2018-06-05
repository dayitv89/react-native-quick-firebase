package com.gds.quickfirebase;

import android.support.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


@ReactModule(name = "RNQuickFirebase")
public class RNQuickFirebase extends ReactContextBaseJavaModule {


    private ReactApplicationContext reactContext;
    private FirebaseAuth mAuth;
    private String mVerificationId;

    private PhoneAuthProvider.ForceResendingToken mResendToken;


    public RNQuickFirebase(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public String getName() {
        return "RNQuickFirebase";
    }

    @ReactMethod
    public void sendOTP(String phone) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,        // Phone number to verify
                60,              // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                reactContext.getCurrentActivity(),       // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential credential) {
                        // This callback will be invoked in two situations:
                        // 1 - Instant verification. In some cases the phone number can be instantly
                        //     verified without needing to send or enter a verification code.
                        // 2 - Auto-retrieval. On some devices Google Play services can automatically
                        //     detect the incoming verification SMS and perform verificaiton without user action.
//                mTextViewProfile.setText("onVerificationCompleted: " + credential);
//                mVerificationInProgress = false;
//
//                updateUI(STATE_VERIFY_SUCCESS, credential);
//                signInWithPhoneAuthCredential(credential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
//                mTextViewProfile.setText("onVerificationFailed: " + e.getMessage());
//                mVerificationInProgress = false;
//
//                if (e instanceof FirebaseAuthInvalidCredentialsException) {
//                    mPhoneNumberField.setError("Invalid phone number.");
//                } else if (e instanceof FirebaseTooManyRequestsException) {
//                    Snackbar.make(findViewById(android.R.id.content), "The SMS quota for the project has been exceeded", Snackbar.LENGTH_SHORT).show();
//                }
//                updateUI(STATE_VERIFY_FAILED);
                    }

                    @Override
                    public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                        // The SMS verification code has been sent to the provided phone number, we
                        // now need to ask the user to enter the code and then construct a credential
                        // by combining the code with a verification ID.

//                mTextViewProfile.setText("onCodeSent: " + verificationId);
//
//                // Save verification ID and resending token so we can use them later
                        mVerificationId = verificationId;
                        mResendToken = token;
//
//                updateUI(STATE_CODE_SENT);
                    }
                });
    }

    @ReactMethod
    public void validateOTP(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        mAuth.signInWithCredential(credential).addOnCompleteListener(reactContext.getCurrentActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = task.getResult().getUser();
                } else {

                }
            }
        });
    }

    @ReactMethod
    public void signOut() {
        mAuth.signOut();
    }
}
