package com.lian.myproject.adapters;

import android.os.Bundle;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.lian.myproject.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recycler_view_adapter);

        public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements Filterable {

            private List<String> fullList;
            private List<String> filteredList;

            public MyAdapter(List<String> list) {
                this.fullList = list;
                this.filteredList = new ArrayList<>(list);
            }

            @Override
            public int getItemCount() {
                return filteredList.size();
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                holder.textView.setText(filteredList.get(position));
            }

            @Override
            public Filter getFilter() {
                return myFilter;
            }

            private Filter myFilter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    List<String> resultList = new ArrayList<>();

                    if (constraint == null || constraint.length() == 0) {
                        resultList.addAll(fullList);
                    } else {
                        String filterPattern = constraint.toString().toLowerCase().trim();

                        for (String item : fullList) {
                            if (item.toLowerCase().contains(filterPattern)) {
                                resultList.add(item);
                            }
                        }
                    }

                    FilterResults results = new FilterResults();
                    results.values = resultList;
                    return results;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    filteredList.clear();
                    filteredList.addAll((List) results.values);
                    notifyDataSetChanged();
                }
            };

            class ViewHolder extends RecyclerView.ViewHolder {
                TextView textView;

                public ViewHolder(View itemView) {
                    super(itemView);
                    textView = itemView.findViewById(R.id.textView);
                }
            }
        }
    }


}