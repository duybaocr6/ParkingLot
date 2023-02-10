package nndb.com.parkinglotbctt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Locale;

public class LicenseAdapter extends BaseAdapter implements Filterable {
    Context context;
    ArrayList<License> originalArray, tempArray;
    CustomFilter cs;

    public LicenseAdapter(Context context, ArrayList<License> originalArray)
    {
        this.context= context;
        this.originalArray = originalArray;
        this.tempArray =  originalArray;
    }

    @Override
    public int getCount() {
        return originalArray.size();
    }

    @Override
    public Object getItem(int i) {
        return originalArray.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.listview_licenceplate,null);

        TextView number_plate = (TextView) row.findViewById(R.id.number_plate);
        TextView type_vehicle = (TextView) row.findViewById(R.id.type_vehicle);

        number_plate.setText(originalArray.get(i).getBSXe());
        type_vehicle.setText(originalArray.get(i).getLoaiXe());
        return row;
    }

    @Override
    public Filter getFilter() {
        if (cs == null)
        {
            cs = new CustomFilter();
        }
        return cs;
    }
    class CustomFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            if (charSequence!=null && charSequence.length()>0) {
                charSequence = charSequence.toString().toUpperCase();
                ArrayList<License> filters = new ArrayList<>();
                for (int i = 0; i < tempArray.size(); i++) {
                    if (tempArray.get(i).getMaX().toUpperCase().contains(charSequence) ||tempArray.get(i).getBSXe().toUpperCase().contains(charSequence) || tempArray.get(i).getLoaiXe().toUpperCase().contains(charSequence) || tempArray.get(i).getGioVao().toUpperCase().contains(charSequence)) {
                        License license = new License(tempArray.get(i).getMaX(),tempArray.get(i).getBSXe(), tempArray.get(i).getLoaiXe(), tempArray.get(i).getGioVao());
                        filters.add(license);
                    }
                }
                results.count = filters.size();
                results.values = filters;
            }
            else
            {
                results.count = tempArray.size();
                results.values = tempArray;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            originalArray = (ArrayList<License>)filterResults.values;
            notifyDataSetChanged();
        }
    }
}
