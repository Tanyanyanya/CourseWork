package com.coursewk.bookapp.filters;

import android.widget.Filter;

import com.coursewk.bookapp.adapters.AdapterCategory;
import com.coursewk.bookapp.adapters.AdapterPdfAdmin;
import com.coursewk.bookapp.models.ModelCategory;
import com.coursewk.bookapp.models.ModelPdf;

import java.util.ArrayList;

public class FilterPdfAdmin extends Filter {
    ArrayList<ModelPdf> filterList;
    AdapterPdfAdmin adapterPdfAdmin;

    public FilterPdfAdmin(ArrayList<ModelPdf> filterList, AdapterPdfAdmin adapterPdfAdmin) {
        this.filterList = filterList;
        this.adapterPdfAdmin = adapterPdfAdmin;
    }


    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results =new FilterResults();
        if (constraint != null && constraint.length() >0) {
            constraint = constraint.toString().toUpperCase();
            ArrayList<ModelPdf> filterModels = new ArrayList<>();
            for (int i = 0; i < filterList.size(); i++) {
                if (filterList.get(i).getTitle().toUpperCase().contains(constraint)) {
                    filterModels.add(filterList.get(i));
                }
            }
            results.count = filterModels.size();
            results.values = filterModels;
        }

        else{
            results.count = filterList.size();
            results.values=filterList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapterPdfAdmin.pdfArrayList = (ArrayList<ModelPdf>) results.values;
        adapterPdfAdmin.notifyDataSetChanged();
    }
}