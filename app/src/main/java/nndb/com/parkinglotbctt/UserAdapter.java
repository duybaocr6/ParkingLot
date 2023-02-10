package nndb.com.parkinglotbctt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter implements Filterable {
    Context context;
    ArrayList<User> originalArray, tempArray;
    UserAdapter.CustomFilter cs;

    public UserAdapter(Context context, ArrayList<User> originalArray)
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
        View row = inflater.inflate(R.layout.listview_user,null);

        TextView Hoten = (TextView) row.findViewById(R.id.txtname);
        TextView Email = (TextView) row.findViewById(R.id.txtemail);
        TextView DiaChi = (TextView) row.findViewById(R.id.txtdiachi);
        TextView NgaySinh = (TextView) row.findViewById(R.id.txtngaysinh);
        TextView SoDT = (TextView) row.findViewById(R.id.txtsdt);
        TextView GioiTinh = (TextView) row.findViewById(R.id.txtgioitinh);

        Hoten.setText(originalArray.get(i).getHoTen());
        Email.setText(originalArray.get(i).getEmail());
        DiaChi.setText(originalArray.get(i).getDiaChi());
        NgaySinh.setText(originalArray.get(i).getNgaySinh());
        SoDT.setText(originalArray.get(i).getSoDT());
        GioiTinh.setText(originalArray.get(i).getGioiTinh());
        return row;
    }

    @Override
    public Filter getFilter() {
        if (cs == null)
        {
            cs = new UserAdapter.CustomFilter();
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
                ArrayList<User> filters = new ArrayList<>();
                for (int i = 0; i < tempArray.size(); i++) {
                    if (tempArray.get(i).getHoTen().toUpperCase().contains(charSequence) || tempArray.get(i).getEmail().toUpperCase().contains(charSequence)
                            || tempArray.get(i).getSoDT().toUpperCase().contains(charSequence)|| tempArray.get(i).getDiaChi().toUpperCase().contains(charSequence)
                            || tempArray.get(i).getNgaySinh().toUpperCase().contains(charSequence)|| tempArray.get(i).getGioiTinh().toUpperCase().contains(charSequence)) {
                        User user = new User(tempArray.get(i).getHoTen(), tempArray.get(i).getEmail(), tempArray.get(i).getSoDT(), tempArray.get(i).getDiaChi(), tempArray.get(i).getNgaySinh(), tempArray.get(i).getGioiTinh());
                        filters.add(user);
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
            originalArray = (ArrayList<User>)filterResults.values;
            notifyDataSetChanged();
        }
    }
}
