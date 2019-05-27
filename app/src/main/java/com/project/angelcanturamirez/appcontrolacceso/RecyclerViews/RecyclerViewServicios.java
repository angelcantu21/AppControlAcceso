package com.project.angelcanturamirez.appcontrolacceso.RecyclerViews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.project.angelcanturamirez.appcontrolacceso.R;
import com.project.angelcanturamirez.appcontrolacceso.entidades.ServicioModelo;
import java.util.List;

public class RecyclerViewServicios extends RecyclerView.Adapter<RecyclerViewServicios.ViewHolder> implements View.OnClickListener {

    public List<ServicioModelo> ListaServicio;
    private View.OnClickListener listener;

    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        //Variables
        private TextView nombre, telefono;

        public ViewHolder(View itemView) {
            super(itemView);
            nombre = (TextView)itemView.findViewById(R.id.TituloNombreServicio);
        }
    }

    public RecyclerViewServicios(List<ServicioModelo> listaServicio) {
        ListaServicio = listaServicio;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_servicios, null,false);
        RecyclerView.LayoutParams layParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layParams);
        view.setOnClickListener(this);
        //ViewHolder viewHolder = new ViewHolder(view);
        return new RecyclerViewServicios.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewServicios.ViewHolder holder, int position) {
        holder.nombre.setText(ListaServicio.get(position).getServicio());
    }

    @Override
    public int getItemCount() {return ListaServicio.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }



}
