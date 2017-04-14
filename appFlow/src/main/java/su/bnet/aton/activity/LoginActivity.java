package su.bnet.aton.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import su.bnet.aton.MainActivity;
import su.bnet.aton.R;
import su.bnet.aton.view.PinBoardView;
import su.bnet.flowcontrol.Command;
import su.bnet.flowcontrol.Navigator;
import su.bnet.flowcontrol.Router;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity {
    private TextView textViewLogin;
    private TextView textViewForget;

    private FrameLayout frameLogin;
    private FrameLayout framePin;
    private FrameLayout framePin2;
    private PinBoardView viewPinBoard;
    private PinBoardView viewPinBoard2;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        frameLogin = (FrameLayout) findViewById(R.id.frameLogin);
        frameLogin.setVisibility(View.GONE);
        framePin = (FrameLayout) findViewById(R.id.framePin);
        framePin.setVisibility(View.GONE);
        framePin2 = (FrameLayout) findViewById(R.id.framePin2);
        framePin2.setVisibility(View.GONE);
        textViewLogin = (TextView) findViewById(R.id.textViewLogin);
        textViewForget = (TextView) findViewById(R.id.textViewForget);
        viewPinBoard = (PinBoardView) findViewById(R.id.viewPinBoard);
        viewPinBoard2 = (PinBoardView) findViewById(R.id.viewPinBoard2);

        initRoutes();
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigator.forwardTo(State.CREATE_PIN);
            }
        });

        textViewForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigator.forwardTo(State.PIN);
            }
        });

        viewPinBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigator.forwardTo(State.MAIN);
            }
        });

        viewPinBoard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigator.back();
            }
        });

        navigator.forwardTo(State.LOGIN);

        //TODO remove
//        {
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            startActivity(intent);
//        }
    }

    @Override
    public void onBackPressed() {
        if(!navigator.back()){
            super.onBackPressed();
        }
    }

    private enum State{
        LOGIN, PIN, CREATE_PIN, RECOVER, MAIN
    }

    private Router<State, Command> router;
    private Navigator<State, Command> navigator;

    private void initRoutes(){
        router = new Router<>();
        navigator = new Navigator<>(router);

        router.add(State.LOGIN, new Command(){
            @Override
            public void forward(Bundle b) {
                frameLogin.setVisibility(View.VISIBLE);
                framePin.setVisibility(View.GONE);
                framePin2.setVisibility(View.GONE);
            }

            @Override
            public void rollback() {
                frameLogin.setVisibility(View.GONE);
            }
        });

        router.add(State.PIN, new Command() {
            @Override
            public void forward(Bundle b) {
                frameLogin.setVisibility(View.GONE);
                framePin.setVisibility(View.GONE);
                framePin2.setVisibility(View.VISIBLE);
            }

            @Override
            public void rollback() {
                framePin2.setVisibility(View.GONE);
            }
        });

        router.add(State.CREATE_PIN, new Command() {
            @Override
            public void forward(Bundle b) {
                frameLogin.setVisibility(View.GONE);
                framePin.setVisibility(View.VISIBLE);
                framePin2.setVisibility(View.GONE);
            }

            @Override
            public void rollback() {
                framePin.setVisibility(View.GONE);
            }
        });

        router.add(State.MAIN, new Command() {
            @Override
            public void forward(Bundle b) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void rollback() {

            }
        });
    }
}
