package edu.tcu.ciprudhomme.tiredb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class TireAdapter extends ArrayAdapter<Tire> {
    public TireAdapter(Context context, List<Tire> tires) {
        super(context, 0, tires);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Tire tire = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.note_cell, parent, false);
        }

        TextView tireSize = convertView.findViewById(R.id.cellTireSize);
        TextView dot = convertView.findViewById(R.id.cellDot);
        TextView treadLife = convertView.findViewById(R.id.cellTreadLife);
        TextView brand = convertView.findViewById(R.id.cellBrand);
        TextView quantity = convertView.findViewById(R.id.cellQuantity);
        TextView patches = convertView.findViewById(R.id.cellPatches);
        TextView location = convertView.findViewById(R.id.cellLocation);

        tireSize.setText(tire.getTireSize());
        dot.setText(tire.getDot());
        treadLife.setText(String.valueOf(tire.getTreadLife()) + "%");
        brand.setText(tire.getBrand());
        quantity.setText(String.valueOf(tire.getQuantity()));
        patches.setText(tire.hasPatches() ? "Yes" : "No");
        location.setText(tire.getLocation());

        return convertView;
    }
}
