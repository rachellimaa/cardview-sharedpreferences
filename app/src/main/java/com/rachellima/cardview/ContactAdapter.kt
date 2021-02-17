package com.rachellima.cardview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(var listener: ClickItemContactListener) :
    RecyclerView.Adapter<ContactAdapter.ContactAdapterViewHolder>() {
    private val list: MutableList<Contact> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ContactAdapterViewHolder(view, list, listener)
    }

    // Popular cada item do array no RecyclerView
    override fun onBindViewHolder(holder: ContactAdapterViewHolder, position: Int) {
        holder.bind(list[position])
    }

    //Tamanho da lista
    override fun getItemCount(): Int = list.size

    fun updateListAdapter(lista: List<Contact>) {
        this.list.clear()
        this.list.addAll(lista)
        notifyDataSetChanged()
    }

    class ContactAdapterViewHolder(
        itemView: View,
        var list: List<Contact>,
        var listener: ClickItemContactListener
    ) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tv_name)
        private val tvPhone: TextView = itemView.findViewById(R.id.tv_phone)
        private val ivPhotograph: ImageView = itemView.findViewById(R.id.iv_contact)

        //chama um método da interface
        init {
            itemView.setOnClickListener {
                listener.clickItemContact(list[adapterPosition])
            }
        }

        fun bind(contact: Contact) {
            tvName.text = contact.name
            tvPhone.text = contact.phone
        }
    }

}