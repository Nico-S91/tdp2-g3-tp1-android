package tp1.g3.tdp2.hoycomo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import tp1.g3.tdp2.hoycomo.R;

import java.util.ArrayList;
import java.util.List;

import tp1.g3.tdp2.hoycomo.Modelos.Comercio;

public class ComercioAdapter extends BaseAdapter implements Filterable {

    private List<Comercio>originalData = null;
    private List<Comercio>filteredData = null;
    private LayoutInflater mInflater;
    private ItemFilter mFilter = new ItemFilter();

    public ComercioAdapter(Context context, List<Comercio> comercios) {
        this.filteredData = comercios ;
        this.originalData = comercios ;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return filteredData.size();
    }

    public Object getItem(int position) {
        return filteredData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // A ViewHolder keeps references to children views to avoid unnecessary calls
        // to findViewById() on each row.
        ViewHolder holder;

        // When convertView is not null, we can reuse it directly, there is no need
        // to reinflate it. We only inflate a new View when the convertView supplied
        // by ListView is null.
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_comercios, null);

            // Creates a ViewHolder and store references to the two children views
            // we want to bind data to.
            holder = new ViewHolder();
            holder.text = convertView.findViewById(R.id.txtComercioNombre);

            // Bind the data efficiently with the holder.

            convertView.setTag(holder);
        } else {
            // Get the ViewHolder back to get fast access to the TextView
            // and the ImageView.
            holder = (ViewHolder) convertView.getTag();
        }

        // If weren't re-ordering this you could rely on what you set last time
        holder.text.setText(filteredData.get(position).getNombre());
        TextView txtGenres =  convertView.findViewById(R.id.txtDireccion);
        txtGenres.setText(filteredData.get(position).getDireccion());
/*        ImageView imgPhoto = (ImageView) convertView.findViewById(R.id.imgPhoto);
        imgPhoto.setImageBitmap(filteredData.get(position).getPhoto());*/

        return convertView;
    }

    static class ViewHolder {
        TextView text;
    }

    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<Comercio> list = originalData;

            int count = list.size();
            final ArrayList<Comercio> nlist = new ArrayList<>(count);

            Comercio filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i);
                if (filterableString.getNombre().toLowerCase().contains(filterString)) {
                    nlist.add(filterableString);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<Comercio>) results.values;
            notifyDataSetChanged();
        }

    }
}