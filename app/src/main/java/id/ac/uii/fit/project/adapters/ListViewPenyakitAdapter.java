package id.ac.uii.fit.project.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.ac.uii.fit.project.R;
import id.ac.uii.fit.project.models.Penyakit;

public class ListViewPenyakitAdapter extends ArrayAdapter implements Filterable {

    private final Activity context;
    private List<Penyakit> listPenyakit;

    public ListViewPenyakitAdapter(Activity context, List<Penyakit> listPenyakit) {
        super(context, R.layout.listview_penyakit_row, listPenyakit);

        this.context = context;
        this.listPenyakit = listPenyakit;
    }

    public View getView(int position, View view, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listview_penyakit_row, null, true);

        TextView nameView = (TextView) rowView.findViewById(R.id.nameView);
        if (listPenyakit.size() > 0 && position < listPenyakit.size()) {
            nameView.setText(listPenyakit.get(position).getNama());
        } else {
            rowView.setVisibility(View.GONE);
        }

        return rowView;
    }

    private final Filter filter = new Filter() {
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            listPenyakit = (List<Penyakit>) results.values;
            notifyDataSetChanged();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();
            ArrayList<Penyakit> FilteredArrayNames = new ArrayList<Penyakit>();

            if (constraint == null || constraint.toString().trim().length() == 0) {
                FilteredArrayNames = (ArrayList<Penyakit>) Penyakit.getAll();
            } else {
                constraint = constraint.toString().trim().toLowerCase();

                List<Penyakit> allPenyakit = Penyakit.getAll();
                for (Penyakit penyakit : allPenyakit) {
                    if (penyakit.getNama().toLowerCase().contains(constraint)) {
                        FilteredArrayNames.add(penyakit);
                    }
                }
            }

            results.count = FilteredArrayNames.size();
            results.values = FilteredArrayNames;

            return results;
        }
    };

    @Override
    public Filter getFilter() {
        return this.filter;
    }
}
