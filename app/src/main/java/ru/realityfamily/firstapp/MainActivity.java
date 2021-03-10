package ru.realityfamily.firstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ru.realityfamily.firstapp.Models.Automat;
import ru.realityfamily.firstapp.Models.Client;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    TextView autoName1;
    TextView autoName2;
    TextView autoName3;
    TextView autoName4;

    TextView autoStatus1;
    TextView autoStatus2;
    TextView autoStatus3;
    TextView autoStatus4;

    TextView clientId1;
    TextView clientId2;
    TextView clientId3;
    TextView clientId4;

    TextView autoCart1;
    TextView autoCart2;
    TextView autoCart3;
    TextView autoCart4;

    TextView autoQueue11;
    TextView autoQueue12;

    TextView autoQueue21;
    TextView autoQueue22;

    TextView autoQueue31;
    TextView autoQueue32;

    TextView autoQueue41;
    TextView autoQueue42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autoCart1 = findViewById(R.id.autoCart1);
        autoCart2 = findViewById(R.id.autoCart2);
        autoCart3 = findViewById(R.id.autoCart3);
        autoCart4 = findViewById(R.id.autoCart4);

        autoName1 = findViewById(R.id.autoName1);
        autoName2 = findViewById(R.id.autoName2);
        autoName3 = findViewById(R.id.autoName3);
        autoName4 = findViewById(R.id.autoName4);

        autoStatus1 = findViewById(R.id.autoStatus1);
        autoStatus2 = findViewById(R.id.autoStatus2);
        autoStatus3 = findViewById(R.id.autoStatus3);
        autoStatus4 = findViewById(R.id.autoStatus4);

        clientId1 = findViewById(R.id.clientId1);
        clientId2 = findViewById(R.id.clientId2);
        clientId3 = findViewById(R.id.clientId3);
        clientId4 = findViewById(R.id.clientId4);

        autoQueue11 = findViewById(R.id.autoQueue11);
        autoQueue12 = findViewById(R.id.autoQueue12);

        autoQueue21 = findViewById(R.id.autoQueue21);
        autoQueue22 = findViewById(R.id.autoQueue22);

        autoQueue31 = findViewById(R.id.autoQueue31);
        autoQueue32 = findViewById(R.id.autoQueue32);

        autoQueue41 = findViewById(R.id.autoQueue41);
        autoQueue42 = findViewById(R.id.autoQueue42);

        List<Automat> automats = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            automats.add(new Automat(i));
        }

        for (int i = 1; i < 21; i++) {
            new ClientAsyncTask(
                    i,
                    new Random().nextInt(5) + 1,
                    automats.get(new Random().nextInt(automats.size()))
            ).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    class ClientAsyncTask extends Client {
        public ClientAsyncTask(int id, int productCount, Automat automat) {
            super(id, productCount, automat);
        }

        void changeStatus() {
            switch (getAutomat().getId()) {
                case 1:
                    autoStatus1.setText(getAutomat().getStatus());
                    break;
                case 2:
                    autoStatus2.setText(getAutomat().getStatus());
                    break;
                case 3:
                    autoStatus3.setText(getAutomat().getStatus());
                    break;
                case 4:
                    autoStatus4.setText(getAutomat().getStatus());
                    break;
            }
        }

        void changeBill() {
            switch (getAutomat().getId()) {
                case 1:
                    autoCart1.setText(getBill());
                    break;
                case 2:
                    autoCart2.setText(getBill());
                    break;
                case 3:
                    autoCart3.setText(getBill());
                    break;
                case 4:
                    autoCart4.setText(getBill());
                    break;
            }
        }

        void changeClient() {
            switch (getAutomat().getId()) {
                case 1:
                    clientId1.setText(getClientName());
                    break;
                case 2:
                    clientId2.setText(getClientName());
                    break;
                case 3:
                    clientId3.setText(getClientName());
                    break;
                case 4:
                    clientId4.setText(getClientName());
                    break;
            }
        }

        void endClient() {
            switch (getAutomat().getId()) {
                case 1:
                    clientId1.setText("");
                    autoCart1.setText("");
                    autoStatus1.setText("");
                    break;
                case 2:
                    clientId2.setText("");
                    autoCart2.setText("");
                    autoStatus2.setText("");
                    break;
                case 3:
                    clientId3.setText("");
                    autoCart3.setText("");
                    autoStatus3.setText("");
                    break;
                case 4:
                    clientId4.setText("");
                    autoCart4.setText("");
                    autoStatus4.setText("");
                    break;
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            synchronized (getAutomat()) {
                Log.d("MainActivity", "started " + getClientName());
                getAutomat().setStatus(Automat.AutomatStatusType.came);
                publishProgress();
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                getAutomat().setStatus(Automat.AutomatStatusType.choosing);
                while (!hasProductsChoosen()) {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    chooseProduct();
                    publishProgress();
                }

                getAutomat().setStatus(Automat.AutomatStatusType.paying);
                publishProgress();
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                getAutomat().setStatus(Automat.AutomatStatusType.leaving);
                publishProgress();
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Client... values) {
            changeClient();
            changeStatus();
            changeBill();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            endClient();
        }
    }
}