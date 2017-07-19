package com.example.ekgml.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;


/**
 * Created by ekgml on 2017-07-18.
 */

public class LoginActivity extends Activity {

    private SessionCallback callback;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d("test:","connect");
        setContentView(R.layout.activity_login);
        callback = new SessionCallback(); //중요 함수 1
        Session.getCurrentSession().addCallback(callback); //중요 함수 2
        Session.getCurrentSession().checkAndImplicitOpen();
        Log.d("test:","connect-----");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(Session.getCurrentSession().handleActivityResult(requestCode,resultCode,data)){
            Log.d("test:","connect1");
            return;
        }
        Log.d("test:","connect2");
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy(){
        Log.d("test:","connect3");
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }

    private class SessionCallback implements ISessionCallback{

        @Override
        public void  onSessionOpened(){
            Log.d("test:","connect4");
            redirectSignupActivity(); //세션 연결 성공시 redirecftSignupActivity() 호출
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception){
            Log.d("test:","connect5");
            if(exception!=null){
                Logger.e(exception);
            }
            setContentView(R.layout.activity_login); //세션 연결 실패시 로그인 화면 다시 불러옴
        }
    }

    protected void redirectSignupActivity(){ //세션 연결 성공시 signupActivity로 넘김
        final Intent intent = new Intent(this, KakaoSignupActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //화면 이동시 애니메이션 없이.
        Log.d("test:","connect6");
        startActivity(intent);
        finish();
    }
}


