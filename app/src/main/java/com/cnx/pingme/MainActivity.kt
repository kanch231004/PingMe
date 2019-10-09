package com.cnx.pingme

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.cnx.pingme.api.ApiErrorResponse
import com.cnx.pingme.api.ApiSuccessResponse
import com.cnx.pingme.api.Message
import com.cnx.pingme.chat.ChatVH
import com.cnx.pingme.chat.ChatViewModel
import com.cnx.pingme.utils.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var chatViewModel: ChatViewModel

    private lateinit var chatRVAdapter : BaseRvAdapter<Message,ChatVH>

    private var messages = ArrayList<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chatViewModel = ViewModelProviders.of(this).get(ChatViewModel::class.java)

        fabSend.setOnClickListener {

            if(!TextUtils.isEmpty(etMessage.editableText)) {

                val message = Message(
                    CHATBOT_ID, CHATBOT_NAME,"",
                    etMessage.editableText.toString(),true)
                etMessage.setText("")
                chatRVAdapter.addItem(message)
                rvMsg.smoothScrollToPosition(chatRVAdapter.itemCount-1)
                sendAndReceiveMsg(message)
            }
        }
        initRvAdapter()


    }

    private fun initRvAdapter() {

        chatRVAdapter = BaseRvAdapter(this,{
            index ->
                if (messages[index].isSent)
                    SENT_MESSAGE
                else
                    RECEIVED_MESSAGE
        },{
            view -> ChatVH(view)
        },messages,{
            holder , message, position ->
                holder.bind(message)
        })

        rvMsg.layoutManager = LinearLayoutManager(this)
        rvMsg.adapter = chatRVAdapter

    }


    private fun sendAndReceiveMsg(message: Message) {

        chatViewModel.sendAndReceiveChat(message.chatBotName!!,message.message!!).observe(this, Observer {

            when (it) {

                is ApiSuccessResponse -> {

                    if (it.body.success == 1)
                        updateUI(it.body.message)

                    else Toast.makeText(this,it.body.errorMessage, Toast.LENGTH_SHORT).show()
                }

                is ApiErrorResponse-> {

                    Toast.makeText(this,it.errorMessage,Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun updateUI(message : Message?) {

        Log.d("MainActivity", "updated msg ${message?.message}")

        message?.let {

            chatRVAdapter.addItem(it)
            rvMsg.smoothScrollToPosition(chatRVAdapter.itemCount - 1)

        }


    }
}
