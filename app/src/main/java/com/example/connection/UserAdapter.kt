package com.example.connection

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class UserAdapter(context: Context, resource: Int, users: List<User>) :
    ArrayAdapter<User>(context, resource, users) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        }

        val user = getItem(position)

        val imageViewThumbnail: ImageView = itemView!!.findViewById(R.id.imageViewThumbnail)
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewEmail: TextView = itemView.findViewById(R.id.textViewEmail)
        val textViewAddress: TextView = itemView.findViewById(R.id.textViewAddress)

        user?.let {
            textViewName.text = it.name
            textViewEmail.text = it.email
            textViewAddress.text = "${it.address.street}, ${it.address.city}"

            Picasso.get().load(it.thumbnail).placeholder(R.drawable.ic_launcher_foreground).into(imageViewThumbnail)
        }

        return itemView
    }
}
