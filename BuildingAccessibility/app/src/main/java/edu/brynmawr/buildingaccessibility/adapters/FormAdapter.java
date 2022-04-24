package edu.brynmawr.buildingaccessibility.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.brynmawr.buildingaccessibility.R;
import edu.brynmawr.buildingaccessibility.models.Form;

public class FormAdapter extends RecyclerView.Adapter<FormAdapter.ViewHolder> {

    Context context;
    List<Form> forms;

    public FormAdapter(Context context, List<Form> forms){
        this.context = context;
        this.forms = forms;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View FormView = LayoutInflater.from(context).inflate(R.layout.item_form, parent, false);
        return new ViewHolder(FormView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Get form data at the passed in position
        Form form = forms.get(position);
        //Bind the form data
        holder.bind(form);
    }

    @Override
    public int getItemCount() {
        return forms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvBuildingName;
        TextView tvUserName;
        TextView tvDescription;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvBuildingName = itemView.findViewById(R.id.building_name_text);
            tvUserName = itemView.findViewById(R.id.username_text);
            tvDescription = itemView.findViewById(R.id.description_text);
        }


        public void bind(Form form) {
            tvBuildingName.setText(form.getBuildingName());
            tvUserName.setText(form.getUserName());
            tvDescription.setText(form.getDescription());
        }
    }
}
