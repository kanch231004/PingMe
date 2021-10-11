package com.cnx.pingme

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.cnx.pingme.api.MessageModel
import com.cnx.pingme.chat.ChatAdapter
import com.cnx.pingme.chat.ChatViewModel
import com.cnx.pingme.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_nav_header.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private var userSession: String = SESSION_BOB
    private lateinit var chatRVAdapter: ChatAdapter
    private lateinit var chatViewModel: ChatViewModel

    private var messages = ArrayList<MessageModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        chatViewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
        chatViewModel.userSessionLd.postValue(userSession)
        getChats()
        setSupportActionBar(toolbar)
        toolbar.title = userSession

        setUpNavigation()

        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        rvMsg.layoutManager = layoutManager

        chatRVAdapter = ChatAdapter()
        rvMsg.adapter = chatRVAdapter

        fabSend.setOnClickListener {

            if (!TextUtils.isEmpty(etMessage.editableText)) {


                val message = MessageModel(
                    UUID.randomUUID().toString(), userSession,
                    CHATBOT_ID, USER_NAME, "",
                    etMessage.editableText.toString().trim(), true
                )

                etMessage.setText("")
                chatViewModel.sendAndReceiveChat(message)
            }
        }
    }


    private fun setUpNavigation() {

        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        var actionBarToggle = object : ActionBarDrawerToggle(
            this
            , drawerLayout, toolbar, R.string.opened, R.string.closed
        ) {

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                hideKeyboard()
                tvSelectedName.text = userSession
            }

        }

        drawerLayout.addDrawerListener(actionBarToggle)
        actionBarToggle.syncState()

        nav_view.setNavigationItemSelectedListener {

            drawerLayout.closeDrawers()

            when (it.itemId) {

                R.id.tom -> {
                    userSession = SESSION_TOM; ivProfile.setImageResource(R.drawable.pic_tom)
                }

                R.id.bob -> {
                    userSession = SESSION_BOB; ivProfile.setImageResource(R.drawable.pic_bob)
                }

                R.id.jennifer -> {
                    userSession = SESSION_JENNI; ivProfile.setImageResource(R.drawable.pic_jennifer)
                }

                R.id.mark -> {
                    userSession = SESSION_MARK; ivProfile.setImageResource(R.drawable.pic_mark)
                }

            }

            chatViewModel.userSessionLd.postValue(userSession)
            toolbar.title = userSession
            tvSelectedName.text = userSession

            return@setNavigationItemSelectedListener true
        }

    }

    private fun getChats() {

        chatViewModel.chatList.observe(this, Observer {

            chatRVAdapter.submitList(it)

            if (it.size > 0)
                rvMsg.smoothScrollToPosition(it.size - 1)

        })

        chatViewModel.userSessionLd.observe(this, Observer {
            userSession = it
        })
    }

    private fun hideKeyboard() {

        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
