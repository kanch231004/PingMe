package com.cnx.pingme.room

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cnx.pingme.api.MessageModel

@Dao
interface ChatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChat(chat : MessageModel)

    @Query("SELECT * From MessageModel Where userSession = :userSess")
    fun getMessageForId(userSess : String) : DataSource.Factory<Int,MessageModel>

    @Query("Update MessageModel SET isSuccess = :isSuccess Where id = :msgId")
    fun updateChat(isSuccess : Boolean, msgId: Int)
}
