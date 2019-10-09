package com.cnx.pingme.chat

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cnx.pingme.api.Message
import kotlinx.android.synthetic.main.rv_received_message.view.*
import kotlinx.android.synthetic.main.rv_sent_message.view.*

/** Could be inside adapter class if it is not gonna be used elsewhere */

class ChatVH(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(message: Message) {
        with(itemView) {
            if(message.isSent) {
                tvMessage.text = message.message
                tvName.text = message.chatBotName
            } else {
                tvSenderMessage.text = message.message
                tvSenderName.text = message.chatBotName
            }

        }
    }
}