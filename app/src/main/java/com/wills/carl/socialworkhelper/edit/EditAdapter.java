package com.wills.carl.socialworkhelper.edit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.wills.carl.socialworkhelper.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carl on 2/6/2018.
 */

public class EditAdapter extends ArrayAdapter<String> {
    public EditAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public EditAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public EditAdapter(@NonNull Context context, int resource, @NonNull String[] objects) {
        super(context, resource, objects);
    }

    public EditAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull String[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public EditAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
    }

    public EditAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<String> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public EditAdapter(Context context, ArrayList<String> obj){
        super(context, 0, obj);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        String selected = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.edit_item_title, parent, false);
        }

        final TextView tv_title = (TextView) convertView.findViewById(R.id.supervision_editable_expander);
        final EditText et_hours = (EditText) convertView.findViewById(R.id.et_hours);
        final EditText et_notes = (EditText) convertView.findViewById(R.id.et_notes);
        final AppCompatSpinner spin_type = (AppCompatSpinner) convertView.findViewById(R.id.spin_type);

        String category = tv_title.getText().toString();

        //ParentId indicates if we are in CEU or SUPERVISION.
        //Id of 0 = CEU
        //Id of 1 = Supervision
        int parentId = 0;
        if(category.contains("Supervision")){
            parentId = 1;
        }
        setupSpinner(spin_type, 1);

        tv_title.setText(selected);
        tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_hours.getVisibility() == View.GONE) {
                    tv_title.setCompoundDrawablesWithIntrinsicBounds(null, null, ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.ic_arrow_up, null), null);
                    et_hours.setVisibility(View.VISIBLE);
                    et_notes.setVisibility(View.VISIBLE);
                    spin_type.setVisibility(View.VISIBLE);
                } else{
                    tv_title.setCompoundDrawablesWithIntrinsicBounds(null, null, ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.ic_arrow_down, null), null);
                    et_hours.setVisibility(View.GONE);
                    et_notes.setVisibility(View.GONE);
                    spin_type.setVisibility(View.GONE);
                }
            }
        });

        return convertView;
    }


    //Sets up the spinner properties and data
    private void setupSpinner(AppCompatSpinner spinner, int parentId){
        final ArrayList<String> listOfSupTypes = new ArrayList<String>();
        listOfSupTypes.add("Online");
        listOfSupTypes.add("Face To Face");
        listOfSupTypes.add("Lucy");
        ArrayAdapter adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, listOfSupTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String companyId = listOfSupTypes.get(i);
                String companyName = listOfSupTypes.get(i);
                Log.d("SPINNER", "SPINNER CLICKED: " + companyId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

}
