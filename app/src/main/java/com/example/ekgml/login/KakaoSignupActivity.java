package com.example.ekgml.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.kakao.auth.ErrorCode;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.helper.log.Logger;

/**
 * Created by ekgml on 2017-07-18.
 */

public class KakaoSignupActivity extends Activity {
    @Override
    protected void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d("here2","here2");
        requestMe();
    }

    //사용자의 상태 파악 위해 me API 호출

    protected void requestMe(){//유저의 정보를 받는 함수
        UserManagement.requestMe(new MeResponseCallback() {

            @Override
            public void onFailure(ErrorResult errorResult){
                String message = "failed to get user into. msg="+errorResult;
                Log.d("test:",message);
                Logger.d(message);

                ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                if(result==ErrorCode.CLIENT_ERROR_CODE){
                    finish();
                }else{
                    redirectLoginActivity();
                }
            }
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.d("test:","Session is closed");
                redirectLoginActivity();
            }

            @Override
            public void onNotSignedUp() {
                //카카오톡 회원 아닐 시 showSignup(); 호출 해야한다

            }

            @Override
            public void onSuccess(UserProfile userProfile) {
                Log.d("test","getting profile");
                Log.d("test","userProfile is "+userProfile);
                Logger.d("UserProfile: " + userProfile);
                redirectMainActivity();
            }
        });
    }

    private void redirectMainActivity(){
        Log.d("test:","redirectMainActivity");
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    protected void redirectLoginActivity(){
        Log.d("test:","redirectLoginActivity");
        final Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
}


