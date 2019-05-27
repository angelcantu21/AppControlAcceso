package com.project.angelcanturamirez.appcontrolacceso.RecyclerViews;

/**
 * Created by ANGELCANTU on 26/01/2018.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.angelcanturamirez.appcontrolacceso.R;
import com.project.angelcanturamirez.appcontrolacceso.entidades.InvitadoModelo;
import java.util.List;

public class RecyclerViewInvitados extends RecyclerView.Adapter<RecyclerViewInvitados.ViewHolder> implements View.OnClickListener{

    public List<InvitadoModelo> ListasInvitado;
    private View.OnClickListener listener;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        //Variables
        private TextView nombre, fecha, caducidad;

        public ViewHolder(View itemView) {
            super(itemView);
            nombre = (TextView)itemView.findViewById(R.id.txtTitulo);
            fecha = (TextView)itemView.findViewById(R.id.txtDescripcion);
            caducidad = (TextView) itemView.findViewById(R.id.txtCarrera);
        }
    }

    public RecyclerViewInvitados(List<InvitadoModelo> listaEdificio) {
        ListasInvitado = listaEdificio;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contenedor, null,false);
        RecyclerView.LayoutParams layParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layParams);
        view.setOnClickListener(this);
        //ViewHolder viewHolder = new ViewHolder(view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nombre.setText(ListasInvitado.get(position).getNombre());
        holder.fecha.setText("Fecha:\n"+ListasInvitado.get(position).getFecha());
        holder.caducidad.setText("Caducidad:\n"+ListasInvitado.get(position).getCaducidad());
    }

    @Override
    public int getItemCount() {return ListasInvitado.size();
    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }
    }
}

