package com.cnx.pingme.di

import com.cnx.pingme.worker.SendMsgWorker
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@EntryPoint
interface WorkerEntryPoint {
    fun injectIntoWorker(sendMsgWorker: SendMsgWorker)
}