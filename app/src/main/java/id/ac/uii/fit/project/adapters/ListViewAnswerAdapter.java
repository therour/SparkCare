package id.ac.uii.fit.project.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import id.ac.uii.fit.project.R;
import id.ac.uii.fit.project.models.Gejala;

public class ListViewAnswerAdapter extends ArrayAdapter {

    private final Activity context;
    private final List<Gejala> listGejala;

    public ListViewAnswerAdapter(Activity context, List<Gejala> listGejala) {
        super(context, R.layout.listview_answer_row, listGejala);

        this.context = context;
        this.listGejala = listGejala;
    }

    @NonNull
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listview_answer_row, null, true);

        TextView nameView = (TextView) rowView.findViewById(R.id.nameView);
        nameView.setText(listGejala.get(position).nama);

        return rowView;

    }




}
