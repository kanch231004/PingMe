package com.cnx.pingme.chat

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cnx.pingme.api.MessageModel
import kotlinx.android.synthetic.main.rv_received_message.view.*
import kotlinx.android.synthetic.main.rv_sent_message.view.*

/** Could be inside adapter class if it is not gonna be used elsewhere */


class ChatVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(messageModel: MessageModel) {

        with(itemView) {

            if (messageModel.isSent) {
                tvMessage.text = messageModel.message
                tvName.text = messageModel.chatBotName
                tvFailed.visibility = if (messageModel.isSuccess) View.GONE else View.VISIBLE
            } else {

                tvSenderMessage.text = messageModel.message
                tvSenderName.text = messageModel.userSession
            }
        }
    }
}
