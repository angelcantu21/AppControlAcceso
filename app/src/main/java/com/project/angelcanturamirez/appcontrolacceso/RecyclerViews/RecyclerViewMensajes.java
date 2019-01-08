package com.project.angelcanturamirez.appcontrolacceso.RecyclerViews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.angelcanturamirez.appcontrolacceso.R;
import com.project.angelcanturamirez.appcontrolacceso.entidades.MensajeModelo;

import java.util.List;

public class RecyclerViewMensajes extends RecyclerView.Adapter<RecyclerViewMensajes.ViewHolder> implements View.OnClickListener{

    public List<MensajeModelo> ListasMensajes;
    private View.OnClickListener listener;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        //Variables
        private TextView asunto, mensaje;

        public ViewHolder(View itemView) {
            super(itemView);
            asunto = (TextView)itemView.findViewById(R.id.txtAsunto);
            mensaje = (TextView)itemView.findViewById(R.id.txtMensaje);
        }
    }

    public RecyclerViewMensajes(List<MensajeModelo> listaMensaje) {
        ListasMensajes = listaMensaje;
    }

    @Override
    public RecyclerViewMensajes.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mensajes, null,false);
        RecyclerView.LayoutParams layParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layParams);
        view.setOnClickListener(this);
        //ViewHolder viewHolder = new ViewHolder(view);
        return new RecyclerViewMensajes.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewMensajes.ViewHolder holder, int position) {
        holder.asunto.setText(ListasMensajes.get(position).getAsunto());
        holder.mensaje.setText(ListasMensajes.get(position).getMensaje());
    }

    @Override
    public int getItemCount() {return ListasMensajes.size();
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
