package com.cnx.pingme.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.cnx.pingme.api.MessageModel
import com.cnx.pingme.utils.RECEIVED_MESSAGE
import com.cnx.pingme.utils.SENT_MESSAGE

class ChatAdapter : PagedListAdapter<MessageModel, ChatVH>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatVH {

        val view  = LayoutInflater.from(parent.context)
            .inflate( viewType,parent,false)

        return ChatVH(view)
    }

    override fun onBindViewHolder(holder: ChatVH, position: Int) {

        val chat = getItem(position)
        chat?.let {  holder.bind(messageModel = chat) }
    }

    override fun getItemViewType(position: Int): Int {
        return if(currentList!![position]?.isSent == true) SENT_MESSAGE else RECEIVED_MESSAGE
    }

    companion object {
        /**
         * This diff callback informs the PagedListAdapter how to compute list differences when new
         * PagedLists arrive.
         * <p>
         * When you add a Chat, the PagedListAdapter uses diffCallback to
         * detect there's only a single item difference from before, so it only needs to animate and
         * rebind a single view.
         *
         * @see android.support.v7.util.DiffUtil
         */
        private val diffCallback = object : DiffUtil.ItemCallback<MessageModel>() {
            override fun areItemsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean =
                oldItem == newItem
        }
    }
}

