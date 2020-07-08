package com.example.pharmease;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
//
//public class CustomListAdapter extends ArrayAdapter<String> {
//
//    Context context;
//    int resource, textViewResourceId;
//    List<String> pharmacies, pharmacyItems, suggestions;
//
//    public CustomListAdapter(Context context, int resource, int textViewResourceId, List<String> items) {
//        super(context, resource, textViewResourceId, items);
//        this.context = context;
//        this.resource = resource;
//        this.textViewResourceId = textViewResourceId;
//        this.pharmacies = items;
//        pharmacyItems = new ArrayList<String>(items); // this makes the difference.
//        suggestions = new ArrayList<String>();
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View view = convertView;
//        if (convertView == null) {
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            view = inflater.inflate(R.layout.dropdown, parent, false);
//        }
//        String pharma = pharmacies.get(position);
//        if (pharma != null) {
//            TextView lblName = (TextView) view.findViewById(R.id.textAutoComplete);
//            if (lblName != null)
//                lblName.setText(pharma.);
//        }
//        return view;
//    }
//
//    @Override
//    public Filter getFilter() {
//        return nameFilter;
//    }
//
//    /**
//     * Custom Filter implementation for custom suggestions we provide.
//     */
//    Filter nameFilter = new Filter() {
//        @Override
//        public CharSequence convertResultToString(Object resultValue) {
//            String str = ((String) resultValue).getName();
//            return str;
//        }
//
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            if (constraint != null) {
//                suggestions.clear();
//                for (People people : pharmacyItems) {
//                    if (people.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
//                        suggestions.add(people);
//                    }
//                }
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = suggestions;
//                filterResults.count = suggestions.size();
//                return filterResults;
//            } else {
//                return new FilterResults();
//            }
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            List<People> filterList = (ArrayList<People>) results.values;
//            if (results != null && results.count > 0) {
//                clear();
//                for (People people : filterList) {
//                    add(people);
//                    notifyDataSetChanged();
//                }
//            }
//        }
//    };
//}}}