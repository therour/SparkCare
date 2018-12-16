package id.ac.uii.fit.project;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.Constraints;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

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
import java.util.Random;

import id.ac.uii.fit.project.models.Gejala;
import id.ac.uii.fit.project.models.Penyakit;
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
    private TextSwitcher questionText;
    private LinearLayout answerLinearLayout;
    private ProgressBar questionProgressBar;
    private LinearLayout questionLayout;
    private TextView buttonSebelumnya;

    String bgArray[] = {"#B2D9C2", "#B9B2D9", "#CBD9B2", "#D9C7B2", "#D9B2C6", "#D9B2B3", "#B5D9B2"};

    private String SOLVE_FUNCTION_URL = "https://us-central1-sparkcare-5e2bb.cloudfunctions.net/solve";
    private static final MediaType JSON_CONTENT_TYPE = MediaType.parse("application/json; charset=utf-8");
;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnosa_activity);
        answer.clear();
        listGejala = Gejala.getCollection();
        answerLinearLayout = findViewById(R.id.answerLinearLayout);
        questionText = (TextSwitcher) findViewById(R.id.question_text);
        questionLayout = (LinearLayout) findViewById(R.id.questionLayout);
        buttonSebelumnya = (TextView) findViewById(R.id.buttonSebelumnya);
        final LinearLayout qLayout = (LinearLayout) findViewById(R.id.questionLinearLayout);
        questionText.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView t = new TextView(DiagnosaActivity.this);
                t.setGravity(Gravity.CENTER_VERTICAL);
                t.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                t.setTextSize(30);
                t.setMinHeight(250);
                return t;
            }
        });
        if (currentQuestion == 0) {
            buttonSebelumnya.setVisibility(View.GONE);
        }
        questionText.setCurrentText("Loading ....");
        questionText.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
        questionText.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
        questionProgressBar = (ProgressBar) findViewById(R.id.questionProgressBar);
        questionProgressBar.setMax(listGejala.size() -1);
        questionProgressBar.setProgress(currentQuestion + 1);
        showLoading(false);
    }

    public void actionMyAnswer(View view) {
        Intent intent = new Intent(this, ViewMyAnswerActivity.class);
        startActivity(intent);
    }

    public void actionBack(View view) {
        changeBackground();
        if (currentQuestion > 0) {
            currentQuestion--;
            questionText.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_right));
            questionText.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_out_left));
            questionProgressBar.setProgress(currentQuestion + 1);
            showLoading(false);
            questionText.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
            questionText.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
        } else {
            finish();
        }
    }

    public void actionYes(View view) {
        changeBackground();
        if (! isFinishedAction()) {
            Gejala gejala = listGejala.get(currentQuestion - 1);
            if (! answer.contains(gejala)) {
                answer.add(gejala);
            }
            showLoading(false);
        }
    }

    private void changeBackground() {
        Random i = new Random();
        int c = i.nextInt(7-1) + 1;

        questionLayout.setBackgroundColor(Color.parseColor(bgArray[c]));
    }

    public void actionNo(View view) {
        changeBackground();
        if (! isFinishedAction()) {
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
            questionProgressBar.setIndeterminate(true);
            questionText.setText(text);
            answerLinearLayout.setVisibility(View.INVISIBLE);
        } else {
            questionProgressBar.setIndeterminate(false);
            questionText.setText(listGejala.get(currentQuestion).kode + ". " + listGejala.get(currentQuestion).nama);
            answerLinearLayout.setVisibility(View.VISIBLE);
        }
    }

    private boolean isFinishedAction() {
        if (++currentQuestion >= listGejala.size()) {
            showLoading(true);
            getResult(answer);
            buttonSebelumnya.setVisibility(View.GONE);
            return true;
        } else {
            questionProgressBar.setProgress(currentQuestion);
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
        System.out.println(json);
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

    private void showResult(final Penyakit penyakit) {
        disableAction(true,
                "Anda terindikasi memiliki penyakit\n\n" + penyakit.getNama()
        );
        answerLinearLayout.setVisibility(View.GONE);
        questionProgressBar.setIndeterminate(false);
        final Button buttonDetail = (Button) findViewById(R.id.buttonDetail);
        buttonDetail.setVisibility(View.VISIBLE);
        buttonDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailPenyakitActivity.setPenyakit(penyakit);
                Intent intent = new Intent(DiagnosaActivity.this, DetailPenyakitActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void showForm() {
        disableAction(true,
                "Tidak ada yang sama"
        );
        answerLinearLayout.setVisibility(View.GONE);
        questionProgressBar.setIndeterminate(false);
    }

    private Runnable responseRunnable(final String result) {
        return new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                Map resultMap = gson.fromJson(result, HashMap.class);

                Penyakit penyakit = Penyakit.get(resultMap.get("penyakit").toString());
                float nilaiPenyakit = Float.parseFloat(resultMap.get("result").toString());

                if (nilaiPenyakit > Float.parseFloat(resultMap.get("threshold").toString())) {
                    System.out.println("Diatas Threshold");
                    System.out.println(penyakit.getNama());
                    showResult(penyakit);
                } else {
                    System.out.println("Tidak ada yang sama");
                    System.out.println(answer);
                    showForm();
                }
            }
        };
    }
}
