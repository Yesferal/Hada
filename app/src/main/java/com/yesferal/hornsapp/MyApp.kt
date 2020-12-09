package com.yesferal.hornsapp

import android.app.Application
import com.yesferal.hornsapp.hada.container.Container
import com.yesferal.hornsapp.hada.container.Hada
import com.yesferal.hornsapp.hada.dependency.Factory
import com.yesferal.hornsapp.hada.dependency.Singleton

class MyApp: Application() {
    /**
     * Initialize the Container for the app
     * in the Application
     * so you can use it in any Activity
     */
    val container: Container = Hada()

    override fun onCreate() {
        super.onCreate()

        initBaseModule()
    }

    /**
     * Here we initialize the dependencies for the entire app
     * in the Base Module
     */
    private fun initBaseModule() {
        container register Factory<String>(tag = "Title") { "Title: Hada Container" }
        container register Factory<String>(tag = "Description") { "Description: This is a demo app, which implement Hada Container. This strings are injected by Hada using a Tag, in order to Hada know which one to use in each case." }

        container register Singleton<MainRepository> {
            MainRepository(
                message = container.resolve(tag = "Title"),
                description = container.resolve(tag = "Description")
            )
        }

        container register Factory<MainContract.ActionListener> {
            MainPresenter(mainRepository = container.resolve())
        }
    }
}