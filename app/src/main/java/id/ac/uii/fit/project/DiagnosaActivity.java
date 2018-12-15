package id.ac.uii.fit.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.ac.uii.fit.project.models.Gejala;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class DiagnosaActivity extends BaseActivity {

    private DatabaseReference db;
    private List<Gejala> listGejala;
    private boolean isLoading;
    private int currentQuestion = 0;
    public static List<Gejala> answer = new ArrayList<>();
    private TextView questionText;
    private LinearLayout answerLinearLayout;

    private String SOLVE_FUNCTION_URL = "https://us-central1-sparkcare-5e2bb.cloudfunctions.net/solve";
    private static final MediaType JSON_CONTENT_TYPE = MediaType.parse("application/json; charset=utf-8");
;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnosa_activity);
        answer.clear();
        answerLinearLayout = findViewById(R.id.answerLinearLayout);
        questionText = (TextView) findViewById(R.id.question_text);
        listGejala = Gejala.getCollection();
        showLoading(false);
    }

    public void actionMyAnswer(View view) {
        Intent intent = new Intent(this, ViewMyAnswerActivity.class);
        startActivity(intent);
    }

    public void actionBack(View view) {
        if (currentQuestion > 0) {
            currentQuestion--;
            showLoading(false);
        } else {
            finish();
        }
    }

    public void actionYes(View view) {
        if (! isFinishedAction()) {
            showLoading(true);
            Gejala gejala = listGejala.get(currentQuestion - 1);
            if (! answer.contains(gejala)) {
                answer.add(gejala);
            }
            showLoading(false);
        }
    }

    public void actionNo(View view) {
        if (! isFinishedAction()) {
            showLoading(true);
            Gejala gejala = listGejala.get(currentQuestion - 1);
            if (answer.contains(gejala)) {
                answer.remove(gejala);
            }
            showLoading(false);
        }
    }

    private void showLoading(Boolean isLoading) {
        disableAction(isLoading, "Loading...");
    }

    private void disableAction(Boolean hideYesOrNo, String text) {
        if (hideYesOrNo) {
            questionText.setText(text);
            answerLinearLayout.setVisibility(View.INVISIBLE);
        } else {
            questionText.setText(listGejala.get(currentQuestion).nama);
            answerLinearLayout.setVisibility(View.VISIBLE);
        }
    }

    private boolean isFinishedAction() {
        if (listGejala.size() <= ++currentQuestion) {
            showLoading(true);
            getResult(answer);
            return true;
        } else {
            return false;
        }
    }

    private void getResult(List<Gejala> params) {
        Map<String, List<String>> data = new HashMap<>();
        List<String> listKodeGejala = new ArrayList<>();
        for (Gejala gejala : params) {
            listKodeGejala.add(gejala.kode);
        }
        Gson gson = new Gson();
        data.put("gejala", listKodeGejala);

        OkHttpClient httpClient = new OkHttpClient();
        HttpUrl.Builder httpBuilder = HttpUrl.parse(SOLVE_FUNCTION_URL).newBuilder();
        httpBuilder.addQueryParameter("gejala", "coba");

        String json = gson.toJson(data);
        RequestBody requestBody = RequestBody.create(JSON_CONTENT_TYPE, json);
        Request request = new Request.Builder().url(httpBuilder.build()).post(requestBody).build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("SOLVING FUNCTION", "error in getting response from firebase cloud function");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToast("Cound’t get response from cloud function");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody responseBody = response.body();
                String hasilnya = "";
                if (!response.isSuccessful()) {
                    showToast("Failed!");
                } else {
                    try {
                        hasilnya = responseBody.string();

                    } catch (IOException e) {
                        showToast("Error lagi");
                    }
                }

                runOnUiThread(responseRunnable(hasilnya));
            }
        });
    }

    private Runnable responseRunnable(final String result) {
        return new Runnable() {
            @Override
            public void run() {
                final Map<String, String> listPenyakit = new HashMap<>();
                DatabaseReference dbPenyakit = FirebaseDatabase.getInstance().getReference("penyakit");
                dbPenyakit.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        listPenyakit.clear();
                        String namaPenyakit = "";
                        for (DataSnapshot penyakitSnapshot: dataSnapshot.getChildren()) {
                            Map<String, String> penyakit = new HashMap<>();
                            listPenyakit.put(penyakitSnapshot.getKey(), penyakitSnapshot.child("nama").getValue().toString());
                        }
                        Gson gson = new Gson();
                        Map resultMap = new HashMap<>();
                        resultMap = gson.fromJson(result, resultMap.getClass());
                        namaPenyakit = listPenyakit.get(resultMap.get("penyakit").toString());
                        float nilaiPenyakit = Float.parseFloat(resultMap.get("result").toString());


                        disableAction(true,
                                "Penyakit anda adalah " + namaPenyakit +
                                        "\n dengan nilai kemiripan " + (nilaiPenyakit * 100) + " persen"
                        );
                        answerLinearLayout.setVisibility(View.GONE);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        };
    }
}
